package The_Shroud;

import EngineX.GameObject;
import GrappleMines.Mine;
import HEC_Industries.Config;
import com.studiohartman.jamepad.ControllerState;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Player extends GameObject {
	Game game;
	boolean initialized = false;
	int offsetX, offsetY, x, y, w, h;
	float speed = 1f;
	float velocityX = 0;
	float velocityY = 0;
	float friction  = 0.5f;
	String direction = "right";

	// Movement
	boolean moveUp    = false;
	boolean moveDown  = false;
	boolean moveLeft  = false;
	boolean moveRight = false;

	// Rectangle
	Collidable collisionBox;

	// Mouse
	boolean mouseIsClicked = false;
	boolean mouseClicked = false;
	boolean mouseJustClicked = false;

	// Keyboard
	boolean space_key = false, space_key_just_pressed = false;
	boolean r_key = false, r_key_just_pressed = false;
	boolean f1_key = false, f1_key_just_pressed = false;
	boolean f2_key = false, f2_key_just_pressed = false;
	boolean f3_key = false, f3_key_just_pressed = false;
	boolean w_key = false, w_key_just_pressed = false;
	boolean a_key = false, a_key_just_pressed = false;
	boolean s_key = false, s_key_just_pressed = false;
	boolean d_key = false, d_key_just_pressed = false;

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

	// Controller Just Pressed
	boolean start_btn_just_pressed = false;
	boolean back_btn_just_pressed = false;
	boolean dpadUp_btn_just_pressed = false;
	boolean dpadDown_btn_just_pressed = false;
	boolean dpadLeft_btn_just_pressed = false;
	boolean dpadRight_btn_just_pressed = false;
	boolean a_btn_just_pressed = false;
	boolean b_btn_just_pressed = false;
	boolean x_btn_just_pressed = false;
	boolean y_btn_just_pressed = false;
	boolean lb_btn_just_pressed = false;
	boolean rb_btn_just_pressed = false;
	boolean leftStick_btn_just_pressed = false; // left stick
	boolean rightStick_btn_just_pressed = false; // right stick

	public Player(Game game, int offsetX, int offsetY, int width, int height) {
		super(game);
		this.game = game;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.x = game.ps.worldX + offsetX;
		this.y = game.ps.worldY + offsetY;
		this.w = width;
		this.h = height;
	}

	public void postInit() {
		// Check if initialized
		if (initialized)
			return;

		// Collision Box
		collisionBox = new Collidable(game,x,y,w,h);

		// Complete initialization
		initialized = true;
	}

	public void update() {
		// This is Run Once Only
		postInit();

		// Check Inputs
		if (Config.controllerEnabled)
			checkInputState();
		resetJustPressedKeys();

		// Movement
		move();

		// Velocity..
		updateVX();
		updateVY();
	}

	public void render(Graphics2D g) {
		// Player
		g.setColor(Color.white);
		g.fillRect(x, y, w, h);

		// CollisionBox
		g.setColor(Color.red);
		g.fillRect((int) collisionBox.x, (int) collisionBox.y, (int) collisionBox.w, (int) collisionBox.h);

		// Direction
		g.setColor(Color.white);
		if (direction == "up") g.drawLine(x+w/2, y-1, x+w/2, y-1-15);
		if (direction == "down") g.drawLine(x+w/2, y+h, x+w/2, y+h+15);
		if (direction == "left") g.drawLine(x-1, y+h/2, x-1-15, y+h/2);
		if (direction == "right") g.drawLine(x+w, y+h/2, x+w+15, y+h/2);
	}

	public void move() {
		if (moveUp && !moveDown && !moveLeft && !moveRight) {
			direction = "up";

			boolean canMove = false;
			for(Collidable c:game.ps.collidables) {
				Collidable a = new Collidable(game, (int) (collisionBox.x), (int) (collisionBox.y+10), (int) collisionBox.w, (int) collisionBox.h);
			}

			velocityY = Math.max(velocityY - speed, -speed);
		}

		if (moveDown && !moveUp && !moveLeft && !moveRight) {
			direction = "down";
			velocityY = Math.min(velocityY + speed, speed);
		}

		if (moveLeft && !moveUp && !moveDown && !moveRight) {
			direction = "left";
			velocityX = Math.max(velocityX - speed, -speed);
		}

		if (moveRight && !moveUp && !moveDown && !moveLeft) {
			direction = "right";
			velocityX = Math.min(velocityX + speed, speed);
		}

		if(moveLeft && moveRight)
			velocityX = 0;

		if(moveUp && moveDown)
			velocityY = 0;
	}

	public void updateVX() {
		this.x += velocityX;
		updateCollisionBox();

		if(!moveLeft && !moveRight) {
			if(velocityX < 0) {
				velocityX += friction;
				if(velocityX > 0)
					velocityX = 0;
			}
			if(velocityX > 0) {
				velocityX -= friction;
				if(velocityX < 0)
					velocityX = 0;
			}
		}
	}

	public void updateVY() {
		this.y += velocityY;
		updateCollisionBox();

		if(!moveUp && !moveDown) {
			if(velocityY < 0) {
				velocityY += friction;
				if(velocityY > 0)
					velocityY = 0;
			}
			if(velocityY > 0) {
				velocityY -= friction;
				if(velocityY < 0)
					velocityY = 0;
			}
		}
	}

	public void updateCollisionBox() {
		collisionBox.x = x;
		collisionBox.y = y;
	}

	public boolean noCollisions() {
		return true;
	}

	void checkInputState() {
		// Movement
		moveUp = (dpadUp_btn || leftStickY_up || w_key);
		moveDown = (dpadDown_btn || leftStickY_down || s_key);
		moveLeft = (dpadLeft_btn || leftStickX_left || a_key);
		moveRight = (dpadRight_btn || leftStickX_right || d_key);

		// Reset Keys Just Pressed
		resetJustPressedKeys();
	}

	void resetJustPressedKeys() {
		space_key_just_pressed = false;
		r_key_just_pressed = false;
		f1_key_just_pressed = false;
		f2_key_just_pressed = false;
		f3_key_just_pressed = false;
		w_key_just_pressed = false;
		a_key_just_pressed = false;
		s_key_just_pressed = false;
		d_key_just_pressed = false;
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_W) {
			w_key = true;
			w_key_just_pressed = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_A) {
			a_key = true;
			a_key_just_pressed = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_S) {
			s_key = true;
			s_key_just_pressed = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_D) {
			d_key = true;
			d_key_just_pressed = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_W) {
			w_key = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_A) {
			a_key = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_S) {
			s_key = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_D) {
			d_key = false;
		}
	}

	public void mousePressed(MouseEvent e) {
		this.mouseIsClicked = true;
	}

	public void mouseReleased(MouseEvent e) {
		this.mouseIsClicked = false;
	}

	public void controllerUpdate(ControllerState controller) {
		// Update Controller State
		this.controller = controller;

		// Start, Back
		this.start_btn = (controller.start);
		this.back_btn = (controller.back);
		this.start_btn_just_pressed = (controller.startJustPressed);
		this.back_btn_just_pressed = (controller.backJustPressed);

		// DPad
		this.dpadUp_btn = (controller.dpadUp);
		this.dpadDown_btn = (controller.dpadDown);
		this.dpadLeft_btn = (controller.dpadLeft);
		this.dpadRight_btn = (controller.dpadRight);
		this.dpadUp_btn_just_pressed = (controller.dpadUpJustPressed);
		this.dpadDown_btn_just_pressed = (controller.dpadDownJustPressed);
		this.dpadLeft_btn_just_pressed = (controller.dpadLeftJustPressed);
		this.dpadRight_btn_just_pressed = (controller.dpadRightJustPressed);

		// A, B, X, Y
		this.a_btn = (controller.a);
		this.b_btn = (controller.b);
		this.x_btn = (controller.x);
		this.y_btn = (controller.y);
		this.a_btn_just_pressed = controller.aJustPressed;
		this.b_btn_just_pressed = controller.bJustPressed;
		this.x_btn_just_pressed = controller.xJustPressed;
		this.y_btn_just_pressed = controller.yJustPressed;

		// Left Button, Left Trigger
		this.lb_btn = (controller.lb);
		this.leftTrigger_btn = (controller.leftTrigger > 0.5);
		this.lb_btn_just_pressed = (controller.lbJustPressed);

		// Right Button, Right Trigger
		this.rb_btn = (controller.rb);
		this.rightTrigger_btn = (controller.rightTrigger > 0.5);
		this.rb_btn_just_pressed = (controller.rbJustPressed);

		// Left Stick
		this.leftStick_btn = (controller.leftStickClick);
		this.leftStick_btn_just_pressed = (controller.leftStickJustClicked);
		this.leftStickY_up = (controller.leftStickY > 0.5);
		this.leftStickY_down = (controller.leftStickY < -0.5);
		this.leftStickX_left = (controller.leftStickX < -0.1);
		this.leftStickX_right = (controller.leftStickX > 0.1);

		// Right Stick
		this.rightStick_btn = (controller.rightStickClick);
		this.rightStick_btn_just_pressed = (controller.rightStickJustClicked);
		this.rightStickY_up = (controller.rightStickY > 0.5);
		this.rightStickY_down = (controller.rightStickY < -0.5);
		this.rightStickX_left = (controller.rightStickX < -0.5);
		this.rightStickX_right = (controller.rightStickX > 0.5);
	}
}

