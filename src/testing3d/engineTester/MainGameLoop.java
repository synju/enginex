package testing3d.engineTester;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import testing3d.entities.Camera;
import testing3d.entities.Entity;
import testing3d.entities.Light;
import testing3d.models.RawModel;
import testing3d.models.TexturedModel;
import testing3d.renderEngine.DisplayManager;
import testing3d.renderEngine.Loader;
import testing3d.renderEngine.MasterRenderer;
import testing3d.renderEngine.OBJLoader;
import testing3d.terrains.Terrain;
import testing3d.textures.ModelTexture;

import java.util.ArrayList;
import java.util.Random;

public class MainGameLoop {
	public static float density = 0.020f;
	public static float gradient = 0.5f;

	public static void main(String[] args) {
		DisplayManager.createDisplay();
		Loader loader = new Loader();

		// <editor-fold desc="// Cube Vertices, Texture Coords & Indices... ">
//		float[] vertices = {
//				-0.5f,0.5f,-0.5f,
//				-0.5f,-0.5f,-0.5f,
//				0.5f,-0.5f,-0.5f,
//				0.5f,0.5f,-0.5f,
//
//				-0.5f,0.5f,0.5f,
//				-0.5f,-0.5f,0.5f,
//				0.5f,-0.5f,0.5f,
//				0.5f,0.5f,0.5f,
//
//				0.5f,0.5f,-0.5f,
//				0.5f,-0.5f,-0.5f,
//				0.5f,-0.5f,0.5f,
//				0.5f,0.5f,0.5f,
//
//				-0.5f,0.5f,-0.5f,
//				-0.5f,-0.5f,-0.5f,
//				-0.5f,-0.5f,0.5f,
//				-0.5f,0.5f,0.5f,
//
//				-0.5f,0.5f,0.5f,
//				-0.5f,0.5f,-0.5f,
//				0.5f,0.5f,-0.5f,
//				0.5f,0.5f,0.5f,
//
//				-0.5f,-0.5f,0.5f,
//				-0.5f,-0.5f,-0.5f,
//				0.5f,-0.5f,-0.5f,
//				0.5f,-0.5f,0.5f
//		};
//
//		float[] textureCoordinates = {
//				0,0,
//				0,1,
//				1,1,
//				1,0,
//				0,0,
//				0,1,
//				1,1,
//				1,0,
//				0,0,
//				0,1,
//				1,1,
//				1,0,
//				0,0,
//				0,1,
//				1,1,
//				1,0,
//				0,0,
//				0,1,
//				1,1,
//				1,0,
//				0,0,
//				0,1,
//				1,1,
//				1,0
//		};
//
//		int[] indices = {
//				0,1,3,
//				3,1,2,
//				4,5,7,
//				7,5,6,
//				8,9,11,
//				11,9,10,
//				12,13,15,
//				15,13,14,
//				16,17,19,
//				19,17,18,
//				20,21,23,
//				23,21,22
//		};
		// </editor-fold>

//		RawModel model = loader.loadToVAO(vertices, textureCoordinates, indices);
// 		TexturedModel staticModel = new TexturedModel(model,new ModelTexture(loader.loadTexture("wood")));

		Entity bulbasaur = new Entity(new TexturedModel(OBJLoader.loadObjModel("bulbasaur", loader),new ModelTexture(loader.loadTexture("bbs"))), new Vector3f(0,0,-15),0,0,0,1);
		bulbasaur.getModel().getTexture().setShineDamper(10);
		bulbasaur.getModel().getTexture().setReflectivity(1);

		Entity grass = new Entity(new TexturedModel(OBJLoader.loadObjModel("grassModel", loader),new ModelTexture(loader.loadTexture("bush"))), new Vector3f(0,0,0),0,0,0,1);
		grass.getModel().getTexture().setHasTransparency(true);
		grass.getModel().getTexture().setUseFakeLighting(true);

		ArrayList<Entity> grassEntities = new ArrayList<>();
		TexturedModel grassModel = new TexturedModel(OBJLoader.loadObjModel("grassModel", loader),new ModelTexture(loader.loadTexture("bush")));
		for(int i = 0; i < 100; i++) {
			Random random = new Random();
			int min = -100;
			int max = 100;
			int x = random.nextInt(max +1 -min) + min;
			int z = random.nextInt(max +1 -min) + min;

			int minRot = 0;
			int maxRot = 360;
			int rotY = random.nextInt(maxRot);

			Entity g = new Entity(grassModel, new Vector3f(x,0,z),0,rotY,0,1);
			g.getModel().getTexture().setHasTransparency(true);
			g.getModel().getTexture().setUseFakeLighting(true);
			grassEntities.add(g);
		}

		Light light = new Light(new Vector3f(2000,2000,2000), new Vector3f(1,1,1));

//		Terrain terrain0 = new Terrain(-1,-1,loader,new ModelTexture(loader.loadTexture("grass")));
//		Terrain terrain1 = new Terrain(0,-1,loader,new ModelTexture(loader.loadTexture("grass")));
//		Terrain terrain2 = new Terrain(-1,0,loader,new ModelTexture(loader.loadTexture("grass")));
//		Terrain terrain3 = new Terrain(0,0,loader,new ModelTexture(loader.loadTexture("grass")));

		Camera camera = new Camera(null);
		MasterRenderer renderer = null;

		while(!Display.isCloseRequested()) {
			checkInput();

			renderer.density = density;
			renderer.gradient = gradient;

			bulbasaur.increaseRotation(0.0f,0.25f,0.0f);
			camera.move();

//			renderer.processTerrain(terrain0);
//			renderer.processTerrain(terrain1);
//			renderer.processTerrain(terrain2);
//			renderer.processTerrain(terrain3);
			renderer.processEntity(bulbasaur);
			for(Entity g:grassEntities) {
				renderer.processEntity(g);
			}
//			renderer.processEntity(grass);

//			renderer.render(light, camera);
			DisplayManager.updateDisplay();
		}

		renderer.cleanUp();
		loader.cleanUP();
		DisplayManager.closeDisplay();
	}

	private static void checkInput() {
		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
			exit();

		if(Keyboard.isKeyDown(Keyboard.KEY_F)) {
			density += 0.001f;
			System.out.println(density);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_V)) {
			density -= 0.001f;
			System.out.println(density);
		}

		if(Keyboard.isKeyDown(Keyboard.KEY_G)) {
			gradient += 0.01f;
			System.out.println(gradient);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_B)) {
			gradient -= 0.01f;
			System.out.println(gradient);
		}
	}

	private static void exit() {
		System.exit(0);
	}
}
