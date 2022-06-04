package Project_Station_8;

import com.studiohartman.jamepad.ControllerState;
import enginex.Animation;
import enginex.Sprite;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Player {
	private Game game;

	// Images for each animation
	private BufferedImage[] stdImages;

	// Animation States
	private Animation std;

	// Current Animation
	private String currentAnimation = "std";
	private Animation animation;

	// Movement Booleans
	private boolean up = false;
	private boolean down = false;
	private boolean left = false;
	private boolean right = false;

	// Controller
	ControllerState controller;
	boolean start_btn = false;
	boolean back_btn = false;
	boolean dpadUp_btn = false;
	boolean dpadDown_btn = false;
	boolean dpadLeft_btn = false;
	boolean dpadRight_btn = false;
	boolean a_btn = false;
	boolean b_btn = false;
	boolean x_btn = false;
	boolean y_btn = false;
	boolean lb_btn = false;
	boolean leftTrigger_btn = false;
	boolean rb_btn = false;
	boolean rightTrigger_btn = false;
	boolean leftStick_btn = false; // left stick
	boolean leftStickY_up = false;
	boolean leftStickY_down = false;
	boolean leftStickX_left = false;
	boolean leftStickX_right = false;
	boolean rightStick_btn = false; // right stick
	boolean rightStickY_up = false;
	boolean rightStickY_down = false;
	boolean rightStickX_left = false;
	boolean rightStickX_right = false;

	// Player Properties
	private float x = 250;
	private float y = 150;
	private int speed = 2;

	// Speed Properties
	float maxSpeed = 2;
	float currentXSpeed = 0;
	float currentYSpeed = 0;
	float currentMaxSpeed = 0;
	float acceleration = 0.2f;
	float currentXAccel = 0f;
	float friction = 0.2f;
	float diagonalLimit = 66f;

	Player(Game game) {
		this.game = game;
		this.x = (float)((game.width/2)-(32/2));
		this.y = (float)((game.height/2)-(32/2));

		initializeResources();
	}

	public void initializeResources() {
		stdImages = new BufferedImage[]{
				Sprite.getSprite(0, 0, 32, 32, "src/"+this.game.gameName+"/resources/characters/player.png"),
				Sprite.getSprite(0, 0, 32, 32, "src/"+this.game.gameName+"/resources/characters/player.png")
		};

		std = new Animation(stdImages, 1);

		animation = std;
	}

	public void update() {
		move();
		updateAnimation();
		updateBounds();
	}

	public void checkControllerState() {
		up = (dpadUp_btn || leftStickY_up);
		down = (dpadDown_btn || leftStickY_down);
		left = (dpadLeft_btn || leftStickX_left);
		right = (dpadRight_btn || leftStickX_right);
	}

	private void move() {
		checkControllerState();
		currentMaxSpeed = ((up && left) || (up&&right) || (down&&left) || (down&&right)) ? ((maxSpeed/4)*3) : maxSpeed;
		updateVerticalVelocity();
		updateHorizontalVelocity();
	}

	private void updateVerticalVelocity() {
		if(up && down) {
			return;
		}
		else if(up) {
			float leftStickY = (controller.leftStickY > 1) ? 1 : controller.leftStickY;
			float percentage = 100 * leftStickY;
			currentYSpeed = -((currentMaxSpeed / 100) * percentage);
		}
		else if(down) {
			float leftStickY = (controller.leftStickY > -1) ? -1 : controller.leftStickY;
			float percentage = 100 * leftStickY;
			currentYSpeed = -((currentMaxSpeed / 100) * percentage);
		}
		else if(!up && !down) { // Friction, to slow down movement...
			if(currentYSpeed > 0) {
				currentYSpeed -= friction;
				if(currentYSpeed<0) currentYSpeed = 0;
			}
			else if(currentYSpeed < 0) {
				currentYSpeed += friction;
				if(currentYSpeed>0) currentYSpeed = 0;
			}
		}

		y+=currentYSpeed;
	}

	private void updateHorizontalVelocity() {
		if(left && right) {
			return;
		}
		else if(left) {
			float leftStickX = (controller.leftStickX < -1) ? -1 : controller.leftStickX;
			float percentage = 100 * leftStickX;
			currentXSpeed = (currentMaxSpeed / 100) * percentage;
		}
		else if(right) {
			float leftStickX = (controller.leftStickX > 1) ? 1 : controller.leftStickX;
			float percentage = 100 * leftStickX;
			currentXSpeed = (currentMaxSpeed / 100) * percentage;
		}
		else if(!left && !right) { // Friction, to slow down movement...
			if(currentXSpeed > 0) {
				currentXSpeed -= friction;
				if(currentXSpeed<0) currentXSpeed = 0;
			}
			else if(currentXSpeed < 0) {
				currentXSpeed += friction;
				if(currentXSpeed>0) currentXSpeed = 0;
			}
		}

		x+=currentXSpeed;
	}

	private void updateAnimation() {
		animation = std;
		currentAnimation = "std";

		animation.start();
		animation.update();
	}

	private void updateBounds() {
		//...
	}

	public void render(Graphics2D g) {
		g.drawImage(animation.getSprite(), (int)x, (int)y, null);
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_W) up = true;
		if(e.getKeyCode() == KeyEvent.VK_S) down = true;
		if(e.getKeyCode() == KeyEvent.VK_A) left = true;
		if(e.getKeyCode() == KeyEvent.VK_D) right = true;
	}

	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_W) up = false;
		if(e.getKeyCode() == KeyEvent.VK_S) down = false;
		if(e.getKeyCode() == KeyEvent.VK_A) left = false;
		if(e.getKeyCode() == KeyEvent.VK_D) right = false;
	}

	public void controllerUpdate(ControllerState controller) {
		// Update Controller State
		this.controller = controller;

		// Polling
		this.start_btn = (controller.start) ? true:false;
		this.back_btn = (controller.back) ? true:false;

		// DPad
		this.dpadUp_btn = (controller.dpadUp) ? true:false;
		this.dpadDown_btn = (controller.dpadDown) ? true:false;
		this.dpadLeft_btn = (controller.dpadLeft) ? true:false;
		this.dpadRight_btn = (controller.dpadRight) ? true:false;

		// A, B, X, Y
		this.a_btn = (controller.a) ? true:false;
		this.b_btn = (controller.b) ? true:false;
		this.x_btn = (controller.x) ? true:false;
		this.y_btn = (controller.y) ? true:false;

		// Left Button, Left Trigger
		this.lb_btn = (controller.lb) ? true:false;
		this.leftTrigger_btn = (controller.leftTrigger>0.5)?true:false;

		// Right Button, Right Trigger
		this.rb_btn = (controller.rb) ? true:false;
		this.rightTrigger_btn = (controller.rightTrigger>0.5)?true:false;

		// Left Stick
		this.leftStick_btn = (controller.leftStickClick)?true:false;
		this.leftStickY_up = (controller.leftStickY > 0.5)?true:false;
		this.leftStickY_down = (controller.leftStickY < -0.5)?true:false;
		this.leftStickX_left = (controller.leftStickX < -0.1)?true:false;
		this.leftStickX_right = (controller.leftStickX > 0.1)?true:false;

		// Right Stick
		this.rightStick_btn = (controller.rightStickClick)?true:false;
		this.rightStickY_up = (controller.rightStickY > 0.5)?true:false;
		this.rightStickY_down = (controller.rightStickY < -0.5)?true:false;
		this.rightStickX_left = (controller.rightStickX < -0.5)?true:false;
		this.rightStickX_right = (controller.rightStickX > 0.5)?true:false;
	}
}
