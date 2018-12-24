package testing3d.entities;

import com.badlogic.gdx.math.Vector3;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;
import testing3d.engineTester.Game;
import testing3d.models.TexturedModel;
import testing3d.renderEngine.Loader;
import testing3d.renderEngine.OBJLoader;
import testing3d.textures.ModelTexture;

public class Lamp {
	Game game;

	public static final Vector3f RED = new Vector3f(1f,0f,0f);
	public static final Vector3f GREEN = new Vector3f(0f,1f,0f);
	public static final Vector3f BLUE = new Vector3f(0f,0f,1f);
	public static final Vector3f WHITE = new Vector3f(1f,1f,1f);
	public static final Vector3f ORANGE = new Vector3f(0.25f,0.25f,0.25f);
	private Vector3f position;
	private Entity entity;
	private Light light;
//	private float attenuation = 0.002f;
	private float attenuation = 0.00025f;

	public Lamp(Vector3f position, Loader loader, Vector3f color, Game game) {
		this.game = game;
		this.position = position;
		entity = new Entity(new TexturedModel(OBJLoader.loadObjModel("lamp", loader),new ModelTexture(loader.loadTexture("lamp"))), position,0,0,0,1);
		light = new Light(new Vector3f(position.x,position.y+40,position.z), color, new Vector3f(attenuation,attenuation,attenuation));
		entity.getModel().getTexture().setUseFakeLighting(true);
		game.lights.add(this.light);
	}

	public void update() {
		float amount = 0.01f;

		if(Keyboard.isKeyDown(Keyboard.KEY_Q)) {
			light.getPosition().y += amount;
			System.out.println(light.getPosition().y);
		}

		if(Keyboard.isKeyDown(Keyboard.KEY_Z)) {
			light.getPosition().y -= amount;
			System.out.println(light.getPosition().y);
		}

		entity.setPosition(position);
		light.setPosition(new Vector3f(position.x,position.y+40,position.z));
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public Entity getEntity() {
		return entity;
	}

	public Light getLight() {
		return light;
	}
}
