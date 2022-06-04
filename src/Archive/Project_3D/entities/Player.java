package Archive.Project_3D.entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;
import Archive.Project_3D.engineTester.Game;
import Archive.Project_3D.models.TexturedModel;
import Archive.Project_3D.renderEngine.DisplayManager;

public class Player extends Entity {
	private static final float RUN_SPEED = 20;
	private static final float TURN_SPEED = 160;
	private static final float GRAVITY = -50;
	private static final float JUMP_POWER = 30;

	private boolean up = false;
	private boolean down = false;
	private boolean left = false;
	private boolean right = false;
	public boolean turning = false;
	public boolean boost = false;

	private float currentSpeed = 0;
	private float currentTurnSpeed = 0;
	private float upwardsSpeed = 0;

	private boolean isInAir = false;

	Game game;

	public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, Game game) {
		super(model, position, rotX, rotY, rotZ, scale);
		this.game = game;
	}

	public void update() {
		move();
//		System.out.println("x:" + getPosition().x + " .. y:" + getPosition().y  + " .. z:" + getPosition().z);
	}

	public void move() {
		checkInputs();

		// Rotation
		super.increaseRotation(0,currentTurnSpeed * DisplayManager.getFrameTimeSeconds(), 0);
		this.currentTurnSpeed = 0;

		// Move Forward
		if(up) {
			if(boost) {
				this.currentSpeed = RUN_SPEED * 10;
			}
			else {
				this.currentSpeed = RUN_SPEED;
			}
			float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
			float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
			float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
			super.increasePosition(dx, 0, dz);
		}

		if(down) {
			this.currentSpeed = -RUN_SPEED;
			float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
			float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
			float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
			super.increasePosition(dx, 0, dz);
		}

		if(left) {
			if(!turning) {
				this.currentSpeed = RUN_SPEED;
				float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
				float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY() + 90)));
				float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY() + 90)));
				super.increasePosition(dx, 0, dz);
			}
			else {
				this.currentTurnSpeed = TURN_SPEED;
			}
		}

		if(right) {
			if(!turning) {
				this.currentSpeed = RUN_SPEED;
				float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
				float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY() - 90)));
				float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY() - 90)));
				super.increasePosition(dx, 0, dz);
			}
			else {
				this.currentTurnSpeed = -TURN_SPEED;
			}
		}

		// Gravity
		upwardsSpeed += GRAVITY * DisplayManager.getFrameTimeSeconds();
		super.increasePosition(0,upwardsSpeed*DisplayManager.getFrameTimeSeconds(), 0);
		float terrainHeight = game.terrain.getHeightOfTerrain(super.getPosition().x,super.getPosition().z);
		if(super.getPosition().y < terrainHeight) {
			upwardsSpeed = 0;
			super.getPosition().y = terrainHeight;
			isInAir = false;
		}
	}

	private void jump() {
		if(!isInAir) {
			this.upwardsSpeed = JUMP_POWER;
			isInAir = true;
		}
	}

	private void checkInputs() {
		up = Keyboard.isKeyDown(Keyboard.KEY_W);
		down = Keyboard.isKeyDown(Keyboard.KEY_S);
		left = Keyboard.isKeyDown(Keyboard.KEY_A);
		right = Keyboard.isKeyDown(Keyboard.KEY_D);
		boost = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);

		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE))
			jump();
	}
}
