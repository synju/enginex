package testing3d.engineTester;

import org.lwjgl.BufferUtils;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import testing3d.entities.*;
import testing3d.guis.GuiRenderer;
import testing3d.guis.GuiTexture;
import testing3d.models.TexturedModel;
import testing3d.renderEngine.DisplayManager;
import testing3d.renderEngine.Loader;
import testing3d.renderEngine.MasterRenderer;
import testing3d.renderEngine.OBJLoader;
import testing3d.terrains.Terrain;
import testing3d.textures.ModelTexture;
import testing3d.textures.TerrainTexture;
import testing3d.textures.TerrainTexturePack;
import testing3d.toolbox.MousePicker;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Random;

public class Game {
	// Fog and Density Properties
	float density = 0.0026f;
	float gradient = 1.9420f;

	// Window Size...
	int resolutionMultiplier = 16;
	int baseWidth = 16;
	int baseHeight = 9;

	// Model Loader.. Camera.. Renderer..
	Loader loader;
	Camera camera;
	MasterRenderer renderer;

	// GUI Stuff..
	GuiRenderer guiRenderer;
	ArrayList<GuiTexture> guis;
	GuiTexture gui;

	// Mouse Picker
	MousePicker picker;

	// Terrain
	public Terrain terrain;
	TerrainTexture stoneTexture;
	TerrainTexture rTexture;
	TerrainTexture gTexture;
	TerrainTexture bTexture;
	TerrainTexturePack texturePack;
	TerrainTexture blendMap;

	// 3D Models..
	Entity bulbasaur;
	ArrayList<Lamp> lamps;
	ArrayList<Entity> grassEntities;
	ArrayList<Entity> fernEntities;

	// Player..
	public Player player;

	// Lights..
	ArrayList<Light> lights;

	public boolean placingLamp = false;

	// Main Function (Where it all begins...)
	public static void main(String[] args) {
		new Game().run();
	}

	// Game Loop
	void run() {
		create();

		while(!Display.isCloseRequested()) {
			update();
			render();
		}

		exit();
	}

	// Create.. Update.. Render.. Exit..
	void create() {
		DisplayManager.createDisplay();
		loader = new Loader();
		renderer = new MasterRenderer(loader);

		customMouse();
		generateGui();
		generateTerrain();
		generateLamps();
		generateBulbasaur();
		generateFerns();
		generateGrass();
		generateLights();
		player = new Player(new TexturedModel(OBJLoader.loadObjModel("bulbasaur", loader), new ModelTexture(loader.loadTexture("bbs"))), new Vector3f(5, terrain.getHeightOfTerrain(5, 5), 5), 0, 0, 0, 1, this);

		camera = new Camera(this);
		picker = new MousePicker(camera, renderer.getProjectionMatrix(), terrain);
	}

	void update() {
		checkInput();
		bulbasaur.increaseRotation(0.0f, 0.25f, 0.0f);
		player.update();
		lamps.get(0).update();
		camera.update();

		picker.update();
	}

	void render() {
		renderer.density = density;
		renderer.gradient = gradient;

		renderer.processTerrain(terrain);

		for(Lamp l : lamps) {
			renderer.processEntity(l.getEntity());
		}
//		renderer.processEntity(lamps.get(0).getEntity());

		renderer.processEntity(bulbasaur);
		renderer.processEntity(player);
		for(Entity e : fernEntities) renderer.processEntity(e);
		for(Entity g : grassEntities) renderer.processEntity(g);

		renderer.render(lights, camera);

		// GUI Rendering
//		guiRenderer.render(guis);

		// Update the Display
		DisplayManager.updateDisplay();
	}

	void exit() {
		guiRenderer.cleanUp();
		renderer.cleanUp();
		loader.cleanUP();
		DisplayManager.closeDisplay();
		System.exit(0);
	}

	// Input
	private void checkInput() {
		checkMouseInput();

		placingLamp = false;
		if(Keyboard.isKeyDown(Keyboard.KEY_F1)) placingLamp = true;

		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) || Keyboard.isKeyDown(Keyboard.KEY_X))
			exit();

		if(Keyboard.isKeyDown(Keyboard.KEY_MINUS)) {
			resolutionMultiplier /= 1.2;
			if(resolutionMultiplier < 16) {
				resolutionMultiplier = 16;
				return;
			}

			try {
				int width = resolutionMultiplier * baseWidth;
				int height = resolutionMultiplier * baseHeight;
				Display.setDisplayMode(new DisplayMode(width, height));
				GL11.glViewport(0, 0, width, height);
			}
			catch(Exception e) {
				// Do nothing...
			}
		}

		if(Keyboard.isKeyDown(Keyboard.KEY_EQUALS)) {
			resolutionMultiplier *= 1.2;

			try {
				int width = resolutionMultiplier * baseWidth;
				int height = resolutionMultiplier * baseHeight;
				Display.setDisplayMode(new DisplayMode(width, height));
				GL11.glViewport(0, 0, width, height);
			}
			catch(Exception e) {
				// Do nothing...
			}
		}

		if(Keyboard.isKeyDown(Keyboard.KEY_2)) {
			try {
				int width = 640;
				int height = 480;
				Display.setDisplayMode(new DisplayMode(width, height));
				GL11.glViewport(0, 0, width, height);
			}
			catch(Exception e) {
				// Do nothing...
			}
		}

		if(Keyboard.isKeyDown(Keyboard.KEY_F)) {
			density += 0.0001f;
			System.out.println("density:" + density);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_V)) {
			density -= 0.0001f;
			System.out.println("density:" + density);
		}

		float gradientModifier = 0.001f;
//		float gradientModifier = 0.1f;
		if(Keyboard.isKeyDown(Keyboard.KEY_G)) {
			gradient += gradientModifier;
			System.out.println("gradient:" + gradient);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_B)) {
			gradient -= gradientModifier;
			System.out.println("gradient:" + gradient);
		}
	}

	private void checkMouseInput() {
		while(Mouse.next() && placingLamp) {
			if(Mouse.getEventButtonState()) {
				if(Mouse.getEventButton() == 0) {
//					System.out.println("Left button pressed");
				}
			}
			else {
				if(Mouse.getEventButton() == 0) {
					System.out.println("Left button released");
					addLamp();
					System.out.println("Lamp Added");
				}
			}
		}
	}

	// Custom Mouse Functions
	public void customMouse() {
		try {
			String filePath = "src/testing3d/res/customCursor.png";
			BufferedImage img = ImageIO.read(new File(filePath));
			final int w = img.getWidth();
			final int h = img.getHeight();

			int rgbData[] = new int[w * h];

			for(int i = 0; i < rgbData.length; i++) {
				int x = i % w;
				int y = h - 1 - i / w; // this will also flip the image vertically

				rgbData[i] = img.getRGB(x, y);
			}

			IntBuffer buffer = BufferUtils.createIntBuffer(w * h);
			buffer.put(rgbData);
			buffer.rewind();

			Cursor cursor = new Cursor(w, h, 2, h - 2, 1, buffer, null);

			Mouse.setNativeCursor(cursor);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void hideMouse() {
		try {
			Cursor invisibleCursor = new Cursor(1, 1, 0, 0, 1, BufferUtils.createIntBuffer(1), null);
			Mouse.setNativeCursor(invisibleCursor);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void addLamp() {
		if(picker.getCurrentTerrainPoint() != null) {
			Lamp lamp = new Lamp(picker.getCurrentTerrainPoint(), loader, Lamp.ORANGE);
			lamps.add(new Lamp(picker.getCurrentTerrainPoint(), loader, Lamp.ORANGE));
			lights.add(lamps.get(lamps.size() - 1).getLight());
		}
	}

	// Generator Functions
	private void generateGui() {
		guiRenderer = new GuiRenderer(loader);
		guis = new ArrayList<>();
		gui = new GuiTexture(loader.loadTexture("bulbasaur"), new Vector2f(0.5f, 0.5f), new Vector2f(0.15f, 0.25f));
		guis.add(gui);
		guis.add(new GuiTexture(loader.loadTexture("bulbasaur"), new Vector2f(0.6f, 0.4f), new Vector2f(0.15f, 0.25f)));
	}

	private void generateLamps() {
		lamps = new ArrayList<>();
		lamps.add(new Lamp(new Vector3f(0, 0, 0), loader, Lamp.ORANGE));
	}

	private void generateBulbasaur() {
		bulbasaur = new Entity(new TexturedModel(OBJLoader.loadObjModel("bulbasaur", loader), new ModelTexture(loader.loadTexture("bbs"))), new Vector3f(0, 0, -15), 0, 0, 0, 1);
		bulbasaur.getModel().getTexture().setShineDamper(10);
		bulbasaur.getModel().getTexture().setReflectivity(1);
	}

	private void generateFerns() {
		fernEntities = new ArrayList<>();
		ModelTexture fernTextureAtlas = new ModelTexture(loader.loadTexture("ferns"));
		fernTextureAtlas.setNumberOfRows(2);
		TexturedModel fernModel = new TexturedModel(OBJLoader.loadObjModel("fern", loader), fernTextureAtlas);
		for(int i = 0; i < 1000; i++) {
			Random random = new Random();
			int min = 0;
			int max = 800;
			int x = random.nextInt((max - min) + 1) + min;
			int z = random.nextInt((max - min) + 1) + min;
			float y = (terrain.getHeightOfTerrain(x, z) - 0.1f);

			Entity e = new Entity(fernModel, random.nextInt(4), new Vector3f(x, y, z), 0, random.nextInt(360), 0, 1);
			e.getModel().getTexture().setHasTransparency(true);
			e.getModel().getTexture().setUseFakeLighting(true);
			fernEntities.add(e);
		}
	}

	private void generateGrass() {
		grassEntities = new ArrayList<>();
		TexturedModel grassModel = new TexturedModel(OBJLoader.loadObjModel("grassModel", loader), new ModelTexture(loader.loadTexture("bush")));
		for(int i = 0; i < 1000; i++) {
			Random random = new Random();
			int min = 0;
			int max = 800;
			int x = random.nextInt((max - min) + 1) + min;
			int z = random.nextInt((max - min) + 1) + min;
			float y = (terrain.getHeightOfTerrain(x, z) - 0.1f);

			int maxRot = 360;
			int rotY = random.nextInt(maxRot);

			Entity g = new Entity(grassModel, new Vector3f(x, y, z), 0, rotY, 0, 1);
			g.getModel().getTexture().setHasTransparency(true);
			g.getModel().getTexture().setUseFakeLighting(true);
			grassEntities.add(g);
		}
	}

	private void generateTerrain() {
		stoneTexture = new TerrainTexture(loader.loadTexture("stone"));
		rTexture = new TerrainTexture(loader.loadTexture("tx1"));
		gTexture = new TerrainTexture(loader.loadTexture("tx2"));
		bTexture = new TerrainTexture(loader.loadTexture("tx3"));
		texturePack = new TerrainTexturePack(stoneTexture, rTexture, gTexture, bTexture);
		blendMap = new TerrainTexture(loader.loadTexture("blendmap"));
		terrain = new Terrain(0, 0, loader, texturePack, blendMap, "heightmap");
	}

	private void generateLights() {
		lights = new ArrayList<>();
		lights.add(new Light(new Vector3f(0, 0, -7000), new Vector3f(0.4f, 0.4f, 0.4f)));
//		lights.add(new Light(new Vector3f(0,100,0), new Vector3f(1f,1f,1f), new Vector3f(0.00001f,0.00001f,0.00001f)));
		lights.add(lamps.get(0).getLight());
	}
}
