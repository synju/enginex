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
	float densityModifier = 0.0001f;
	float gradient = 1.9420f;
	float gradientModifier = 0.001f;

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
	boolean guiRenderingEnabled = false;

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
	public ArrayList<Light> lights;
	public boolean placingLamp = false;

	// Main Function
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

	// Create
	void create() {
		DisplayManager.createDisplay();
		loader = new Loader();
		renderer = new MasterRenderer(loader);

		generateLights();
		customMouse();
		generateGui();
		generateTerrain();
		generateLamps();
		generateBulbasaur();
		generateFerns();
		generateGrass();
		player = new Player(new TexturedModel(OBJLoader.loadObjModel("bulbasaur", loader), new ModelTexture(loader.loadTexture("bbs"))), new Vector3f(5, terrain.getHeightOfTerrain(5, 5), 5), 0, 0, 0, 1, this);

		camera = new Camera(this);
		picker = new MousePicker(camera, renderer.getProjectionMatrix(), terrain);
	}

	// Update
	void update() {
		// Check Input
		checkInput();

		// Other
		bulbasaur.increaseRotation(0.0f, 0.25f, 0.0f);

		// Update All 3D Things
		player.update();
		lamps.get(0).update();

		// Update Camera
		camera.update();

		// Update Picker
		picker.update();
	}

	// Render
	void render() {
		// Check for adjustments to density or gradient
		if(renderer.density != density || renderer.gradient != gradient) {
			renderer.density = density;
			renderer.gradient = gradient;
		}

		// Process Everything
		renderer.processTerrain(terrain);
		renderer.processEntity(bulbasaur);
		renderer.processEntity(player);
		for(Lamp l : lamps) renderer.processEntity(l.getEntity());
		for(Entity e : fernEntities) renderer.processEntity(e);
		for(Entity g : grassEntities) renderer.processEntity(g);

		// Render 3D things...
		renderer.render(lights, camera);

		// GUI Rendering
		if(guiRenderingEnabled) guiRenderer.render(guis);

		// Update the Display
		DisplayManager.updateDisplay();
	}

	// Exit
	public void exit() {
		guiRenderer.cleanUp();
		renderer.cleanUp();
		loader.cleanUP();
		DisplayManager.closeDisplay();
		System.exit(0);
	}

	// Window Size Adjustments
	private void adjustWindowSize(String adjustment) {
		// Return if incorrect argument is given...
		if(adjustment != "increase" && adjustment != "decrease")
			return;

		try {
			// Increase window size
			if(adjustment == "increase") resolutionMultiplier *= 1.2;

			// Decrease window size
			if(adjustment == "decrease") resolutionMultiplier /= 1.2;

			// Make sure it doesn't get smaller
			if(resolutionMultiplier < 16) resolutionMultiplier = 16;

			// Set window size.
			int width = resolutionMultiplier * baseWidth;
			int height = resolutionMultiplier * baseHeight;
			Display.setDisplayMode(new DisplayMode(width, height));
			GL11.glViewport(0, 0, width, height);
		}
		catch(Exception e) {
			// Do nothing...
		}
	}

	private void setWindowSize(int w, int h) {
		try {
			Display.setDisplayMode(new DisplayMode(w, h));
			GL11.glViewport(0, 0, w, h);
		}
		catch(Exception e) {
			// Do nothing...
		}
	}

	// Density and Gradient Adjustment
	private void adjustDensity(String adjustment) {
		// Return if incorrect argument is given...
		if(adjustment != "increase" && adjustment != "decrease")
			return;

		// Increase window size
		if(adjustment == "increase") density += densityModifier;

		// Decrease window size
		if(adjustment == "decrease") density -= densityModifier;

		System.out.println("density:" + density);
	}

	private void adjustGradient(String adjustment) {
		// Return if incorrect argument is given...
		if(adjustment != "increase" && adjustment != "decrease")
			return;

		// Increase window size
		if(adjustment == "increase") gradient += gradientModifier;

		// Decrease window size
		if(adjustment == "decrease") gradient -= gradientModifier;

		System.out.println("gradient:" + gradient);
	}

	// Keyboard and Mouse Input Input
	private void checkInput() {
		// Check Mouse Input
		checkMouseInput();

		// Enable placing Lamps
		placingLamp = false;
		if(Keyboard.isKeyDown(Keyboard.KEY_F1)) placingLamp = true;

		// Exit
		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) || Keyboard.isKeyDown(Keyboard.KEY_X)) exit();

		// Window Size Adjustment
		if(Keyboard.isKeyDown(Keyboard.KEY_EQUALS)) adjustWindowSize("increase");
		if(Keyboard.isKeyDown(Keyboard.KEY_MINUS)) adjustWindowSize("decrease");

		// Set 640 x 480 Window Size
		if(Keyboard.isKeyDown(Keyboard.KEY_2)) setWindowSize(640, 480);

		// Increase and Decrease Density..
		if(Keyboard.isKeyDown(Keyboard.KEY_F)) adjustDensity("increase");
		if(Keyboard.isKeyDown(Keyboard.KEY_V)) adjustDensity("decrease");

		// Increase and Decrease Gradient..
		if(Keyboard.isKeyDown(Keyboard.KEY_G)) adjustGradient("increase");
		if(Keyboard.isKeyDown(Keyboard.KEY_B)) adjustGradient("decrease");
	}

	private void checkMouseInput() {
		while(Mouse.next()) {
			if(Mouse.getEventButtonState()) {
				// Left Mouse Button
				if(Mouse.getEventButton() == 0) {
					// Left Button Pressed
				}

				// Right Mouse Button
				if(Mouse.getEventButton() == 1) {
					// Right Button Pressed
					this.hideMouse();
				}
			} else {
				// Left Mouse Button
				if(Mouse.getEventButton() == 0) {
					// Left Button Released
					if(placingLamp) {
						addLamp();
						System.out.println("Lamp Added");
					}
				}

				// Right Mouse Button
				if(Mouse.getEventButton() == 1) {
					// Released Button Released
					this.showMouse();
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

	public void showMouse() {
		// Just do Custom Mouse Again
		customMouse();
	}

	public void addLamp() {
		if(picker.getCurrentTerrainPoint() != null) {
			lamps.add(new Lamp(picker.getCurrentTerrainPoint(), loader, Lamp.ORANGE, this));
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
		lamps.add(new Lamp(new Vector3f(0, 0, 0), loader, Lamp.ORANGE, this));
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
//		lights.add(new Light(new Vector3f(400,1600,400), 		new Vector3f(1f,1f,1f), 					new Vector3f(0.00000075f,0.00000075f,0.00000075f)));
//		lights.add(lamps.get(0).getLight());
	}
}
