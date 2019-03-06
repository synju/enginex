package testing3d.entities;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import testing3d.engineTester.Game;

public class Camera {
	Game game;
	private float distanceFromPlayer = 70;
	private float angleAroundPlayer = 0;

	private Vector3f position = new Vector3f(0,0,0);
	private float pitch = 20f;
	private float yaw = 0f;
	private float roll;

	private static final int MOUSE_LEFT_BUTTON = 0;
	private static final int MOUSE_RIGHT_BUTTON = 1;

	private float cameraHeightOffset = 3;

	private Player player;

	public Camera(Game game) {
		this.game = game;
		this.player = game.player;
	}

	public void update() {
		move();
	}

	public void move() {
		calculateZoom();
		calculatePitchAndAngleAroundPlayer();
		float horizontalDistance = calculateHorizontalDistance();
		float verticalDistance = calculateVerticalDistance();
		calculateCameraPosition(horizontalDistance,verticalDistance);
		cameraHeightCheck();
//		this.yaw = 180 - (player.getRotY() + angleAroundPlayer);
		this.yaw = 180 - (angleAroundPlayer);
	}

	private void cameraHeightCheck() {
		if(cameraIsUnderGround())
			position.y += ((game.terrain.getHeightOfTerrain(position.x,position.z) - position.y)+3);
	}

	private boolean cameraIsUnderGround() {
			return (position.y <= game.terrain.getHeightOfTerrain(position.x,position.z));
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}

	private void calculateCameraPosition(float horizontalDistance, float verticalDistance) {
//		float theta = player.getRotY() + angleAroundPlayer;
		float theta = angleAroundPlayer;
		float offsetX = (float)(horizontalDistance * Math.sin(Math.toRadians(theta)));
		float offsetZ = (float)(horizontalDistance * Math.cos(Math.toRadians(theta)));
		position.x = player.getPosition().x - offsetX;
		position.z = player.getPosition().z - offsetZ;
		position.y = player.getPosition().y + verticalDistance + cameraHeightOffset;
	}

	private float calculateHorizontalDistance() {
		return (float)(distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
	}

	private float calculateVerticalDistance() {
		return (float)(distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
	}

	private void calculateZoom() {
		float zoomLevel = Mouse.getDWheel() * 0.05f;
		distanceFromPlayer -= zoomLevel;

		// Zoom in correction
		if(distanceFromPlayer <= 10)
			distanceFromPlayer = 10;

		// Zoom out correction
		if(distanceFromPlayer >= 226)
			distanceFromPlayer = 226;
	}

	private void calculatePitchAndAngleAroundPlayer() {
		checkMouseState();

		float mouseYSensitivity = 0.15f;
		float mouseXSensitivity = 0.30f;

		if(Mouse.isButtonDown(MOUSE_LEFT_BUTTON)) {
			// Pitch
			float pitchChange = Mouse.getDY() * mouseYSensitivity;
			pitch -= pitchChange;

			// Angle Around Player
			float angleChange = Mouse.getDX() * mouseXSensitivity;
			angleAroundPlayer -= angleChange;
		}

		if(Mouse.isButtonDown(MOUSE_RIGHT_BUTTON)) {
			// Update Player
			player.turning = false;

			// Pitch (New Technique) (Allows infinite turning!)
			if(Mouse.getY() < Display.getHeight()/2)
				pitch -= -(mouseYSensitivity * (float)((Display.getHeight()/2) - Mouse.getY())); // UP
			else
				pitch -= mouseYSensitivity * (float)(Mouse.getY() - (Display.getHeight()/2)); // DOWN

			// Angle (New Technique) (Allows infinite turning!)
			if(Mouse.getX() < Display.getWidth()/2)
				angleAroundPlayer -= -(mouseXSensitivity * (float)((Display.getWidth()/2) - Mouse.getX())); // LEFT
			else
				angleAroundPlayer -= mouseXSensitivity * (float)(Mouse.getX() - (Display.getWidth()/2)); // RIGHT

			// Rotate Player
			player.setRotY(angleAroundPlayer);

			// Reset Mouse to Center, X and Y
			Mouse.setCursorPosition(Display.getWidth()/2, Display.getHeight()/2);
		}
		else {
			// Update Player
			player.turning = true;
		}

		// Pitch up correction
		if(pitch >= 90)
			pitch = 90;

		// Pitch down correction
		if(pitch <= 0)
			pitch = 0;
	}

	private void checkMouseState() {
		while(Mouse.next()){
			if(Mouse.getEventButtonState()) {
				if(Mouse.getEventButton() == 1) {
					// Right Click Pressed
					Mouse.setCursorPosition(Display.getWidth()/2,Display.getHeight()/2);
				}
			}
			else {
				if(Mouse.getEventButton() == 1) {
					// Right Click Released
					Mouse.setCursorPosition(Display.getWidth()/2,Display.getHeight()/2);
				}
			}
		}
	}
}
