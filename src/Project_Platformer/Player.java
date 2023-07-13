package Project_Platformer;

import com.studiohartman.jamepad.ControllerState;
import EngineX.GameObject;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Player extends GameObject {
	Game game;
	float offsetX;
	float offsetY;
	float x;
	float y;
	int w = 64 - 10;
	int h = 64 - 10;

	float walkSpeed = 4;
//	float runSpeed = 6.4f;
	float runSpeed = 7f;
	float speed = walkSpeed;
	boolean running = false;

	//	float jumpSpeed = 6.67f;
	//	float jumpSpeed = 6.7f;
	float jumpSpeed = 7f;

	float velocityX = 0;
	float velocityY = 0;
	float friction = 0.5f;

	float gravity = 0.35f;
	float maxGravity = 12f;
	boolean gravityEnabled = true;
	boolean infiniteJumping = false;

	ArrayList<Chunk> levelChunks;
	ArrayList<Collidable> clist = new ArrayList<>();

	Boolean moveUp = false;
	Boolean moveDown = false;
	Boolean moveLeft = false;
	Boolean moveRight = false;

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
	boolean rightStick_btn_just_pressed = false; // left stick

	public Player(Game game, int offsetX, int offsetY) {
		super(game);
		this.game = game;

		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.x = (int)game.ps.levelHandler.startLocation.x;
		this.y = (int)game.ps.levelHandler.startLocation.y;

		this.levelChunks = game.ps.levelHandler.levelChunks;

		// Add Level Collision objects to list of Collision Objects...
		for(Chunk chunk:levelChunks) {
			for(Collidable c:chunk.cList) {
				clist.add(c);
			}
		}
	}

	void resetPlayer() {
		game.ps.initialized = false;
//		this.x = (int)game.ps.startLocation.x;
//		this.y = (int)game.ps.startLocation.y;
//		velocityY = 0;
//		velocityX = 0;
	}

	public void update() {
		if(Config.controllerEnabled)
			checkControllerState();

		speed = (running) ? runSpeed : walkSpeed;

		gravity();
		move();

		updateVY();
		updateVX();
	}

	void gravity() {
		if(gravityEnabled) {
			boolean collision = false;
			for(Collidable c : clist) {
				if(Phys.collision(new Rectangle((int)this.x, (int)(this.y + this.h), this.w, 1), new Rectangle((int)c.x, (int)c.y, (int)c.w, (int)c.h))) {
					collision = true;
				}
			}
			if(!collision) {
				velocityY += gravity;
			}

			if(velocityY > maxGravity)
				velocityY = maxGravity;

			if(!onGround()) {
				this.y += velocityY;
			}
		}
	}

	void move() {
		if(!gravityEnabled) {
			if(moveUp)
				this.y -= 5;
			if(moveDown)
				this.y += 5;
		}

		if(moveLeft) {
			if(velocityX > -speed) {
				velocityX -= speed;
			}
			if(velocityX < -speed) {
				velocityX = -speed;
			}
		}

		if(moveRight) {
			if(velocityX < speed) {
				velocityX += speed;
			}
			if(velocityX > speed) {
				velocityX = speed;
			}
		}

		if(moveLeft && moveRight)
			velocityX = 0;
	}

	void updateVX() {
		float difference = 0;
		boolean collision = false;
		for(Collidable c : clist) {
			Rectangle a = new Rectangle((int)(this.x + velocityX), (int)this.y, this.w, this.h);
			Rectangle b = new Rectangle((int)c.x, (int)c.y, (int)c.w, (int)c.h);
			if(Phys.collision(a, b)) {
				collision = true;

				if(velocityX > 0) {
					difference = (this.x + this.w + velocityX) - (float)c.x;
				}
				else if(velocityX < 0) {
					difference = (float)(c.x + c.w) - (this.x + velocityX);
				}

				break;
			}
		}

		if(!collision) {
			this.x += velocityX;
		}
		else {
			if(velocityX > 0) {
				this.x += (velocityX - difference);
			}
			else if(velocityX < 0) {
				this.x += velocityX + difference;
			}
			velocityX = 0;
		}

		if(!moveLeft && !moveRight) {
			if(velocityX > 0) {
				velocityX -= friction;
				if(velocityX < 0) {
					velocityX = 0;
				}
			}
			if(velocityX < 0) {
				velocityX += friction;
				if(velocityX > 0) {
					velocityX = 0;
				}
			}
		}
	}

	void updateVY() {
		float difference = 0;
		boolean collision = false;
		for(Collidable c : clist) {
			Rectangle a = new Rectangle((int)this.x, (int)(this.y + velocityY), this.w, this.h);
			Rectangle b = new Rectangle((int)c.x, (int)c.y, (int)c.w, (int)c.h);
			if(Phys.collision(a, b)) {
				collision = true;

				if(velocityY < 0) {
					difference = (float)(c.y + c.h) - (this.y + velocityY);
				}
				else if(velocityY > 0) {
					difference = (this.y + this.h + velocityY) - (float)c.y;
				}

				break;
			}
		}

		if(!collision)
			this.y += velocityY;
		else {
			if(velocityY < 0) {
				this.y += (velocityY + difference);
			}
			else if(velocityY > 0) {
				this.y += velocityY - difference;
			}
			velocityY = 0;
		}
	}

	boolean onGround() {
		for(Collidable c : clist) {
			Rectangle a = new Rectangle((int)this.x, (int)(this.y + this.h), this.w, 1);
			Rectangle b = new Rectangle((int)c.x, (int)c.y, (int)c.w, (int)c.h);
			if(Phys.collision(a, b))
				return true;
		}

		return false;
	}

	void jump() {
		if(!infiniteJumping) {
			if(onGround())
				velocityY = -jumpSpeed;
		}
		else {
			velocityY = -jumpSpeed;
		}
	}

	void playSound() {
		System.out.println("Ping!");
	}

	public void render(Graphics2D g) {
		g.setColor(Color.red);
		g.fillRect((int)x, (int)y, w, h);
	}

	public void checkControllerState() {
		moveLeft = (dpadLeft_btn || leftStickX_left);
		moveRight = (dpadRight_btn || leftStickX_right);
		moveUp = (dpadUp_btn || leftStickY_up);
		moveDown = (dpadDown_btn || leftStickY_down);

		if(this.a_btn_just_pressed)
			jump();

		if(this.b_btn_just_pressed)
			game.exit();

		if(this.x_btn_just_pressed)
			playSound();

		if(this.y_btn_just_pressed)
			resetPlayer();

		if(this.lb_btn_just_pressed) {
			gravityEnabled = !gravityEnabled;
		}

		if(this.rb_btn_just_pressed) {
			infiniteJumping = !infiniteJumping;
		}

		running = this.rightTrigger_btn;
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

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_W)
			moveUp = true;
		if(e.getKeyCode() == KeyEvent.VK_A)
			moveLeft = true;
		if(e.getKeyCode() == KeyEvent.VK_S)
			moveDown = true;
		if(e.getKeyCode() == KeyEvent.VK_D)
			moveRight = true;

		if(e.getKeyCode() == KeyEvent.VK_NUMPAD2)
			jump();

		if(e.getKeyCode() == KeyEvent.VK_NUMPAD5)
			game.exit();

		if((e.getKeyCode() == KeyEvent.VK_R) || (e.getKeyCode() == KeyEvent.VK_NUMPAD3))
			resetPlayer();

		if(e.getKeyCode() == KeyEvent.VK_NUMPAD6) {
			running = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_W)
			moveUp = false;
		if(e.getKeyCode() == KeyEvent.VK_A) {
			if(onGround())
				velocityX = 0;
			moveLeft = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_S)
			moveDown = false;
		if(e.getKeyCode() == KeyEvent.VK_D) {
			if(onGround())
				velocityX = 0;
			moveRight = false;
		}

		if(e.getKeyCode() == KeyEvent.VK_NUMPAD6) {
			running = false;
		}
	}

	public void mousePressed(MouseEvent e) {
		//		whatever.mousePressed(e);
	}

	public void mouseReleased(MouseEvent e) {
		//		whatever.mousePressed(e);
	}
}
