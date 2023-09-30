package GrappleMines;

import EngineX.GameObject;
import HEC_Industries.Config;
import HEC_Industries.Phys;
import com.studiohartman.jamepad.ControllerState;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Player extends GameObject {
	Game game;
	boolean initialized = false;
	int offsetX, offsetY, x, y, w, h;

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

	// Controller
	ControllerState controller;
	boolean         start_btn         = false;
	boolean         back_btn          = false;
	boolean         dpadUp_btn        = false;
	boolean         dpadDown_btn      = false;
	boolean         dpadLeft_btn      = false;
	boolean         dpadRight_btn     = false;
	boolean         a_btn             = false;
	boolean         b_btn             = false;
	boolean         x_btn             = false;
	boolean         y_btn             = false;
	boolean         lb_btn            = false;
	boolean         leftTrigger_btn   = false;
	boolean         rb_btn            = false;
	boolean         rightTrigger_btn  = false;
	boolean         leftStick_btn     = false; // left stick
	boolean         leftStickY_up     = false;
	boolean         leftStickY_down   = false;
	boolean         leftStickX_left   = false;
	boolean         leftStickX_right  = false;
	boolean         rightStick_btn    = false; // right stick
	boolean         rightStickY_up    = false;
	boolean         rightStickY_down  = false;
	boolean         rightStickX_left  = false;
	boolean         rightStickX_right = false;

	// Controller Just Pressed
	boolean start_btn_just_pressed      = false;
	boolean back_btn_just_pressed       = false;
	boolean dpadUp_btn_just_pressed     = false;
	boolean dpadDown_btn_just_pressed   = false;
	boolean dpadLeft_btn_just_pressed   = false;
	boolean dpadRight_btn_just_pressed  = false;
	boolean a_btn_just_pressed          = false;
	boolean b_btn_just_pressed          = false;
	boolean x_btn_just_pressed          = false;
	boolean y_btn_just_pressed          = false;
	boolean lb_btn_just_pressed         = false;
	boolean rb_btn_just_pressed         = false;
	boolean leftStick_btn_just_pressed  = false; // left stick
	boolean rightStick_btn_just_pressed = false; // right stick

	// Custom Stuff...
	float velocityX = 0;
	float velocityY = 0;
	float maxXVelocity = 8f;
	float friction  = 0.0015f;
	float groundFriction  = 0.5f;

	float jumpSpeed = 2.5f;

	float gravity         = 0.30f;
	float maxGravity      = 12f;

	boolean gravityEnabled = true;
	boolean infiniteJumping = true;

	boolean mineIsClicked = false;
	Mine currentClickedMine = null;

	int highestHeight = 0;
	int heightDeath = 30;
	boolean beyondReturn = false;

	Player(Game game, int offsetX, int offsetY, int width, int height) {
		super(game);
		this.game = game;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.x = game.ps.worldX + offsetX;
		this.y = game.ps.worldY + offsetY;
		this.w = width;
		this.h = height;
	}

	void postInit() {
		// Check if initialized
		if(initialized)
			return;

		// Initialization...

		// Complete initialization
		initialized = true;
	}

	public void update() {
		// This is Run Once Only..
		postInit();

		// Check Inputs..
		if(Config.controllerEnabled)
			checkInputState();

		// Move Towards Clicked Mine
		moveTowardsClickedMine();

		// Gravity..
		gravity();

		// Velocity..
		updateVY();
		updateVX();

		// Capture Highest Height
		int currentHeight = (Math.abs(this.y-game.ps.worldY+20))/10;
		game.ps.currentHeight = currentHeight;
		if(currentHeight > heightDeath) {
			beyondReturn = true;
		}
		if(currentHeight > highestHeight) {
			highestHeight = currentHeight;
			if(game.ps.highestHeight < highestHeight) game.ps.highestHeight = highestHeight;
		}

		if(beyondReturn) {
			if(currentHeight == 0) {
				game.ps.gameOverDisplay.visible = true;
			}
		}
	}

	void moveTowardsClickedMine() {
		if(currentClickedMine != null) {
			// Desired velocity per cycle
			float velocity = 0.6f; // You can adjust this to the desired speed
			float maxMineVelocity = 1f; // You can adjust this to the desired speed

			// Calculate the direction vector from player to mine
			float deltaX = currentClickedMine.x - x;
			float deltaY = currentClickedMine.y - y;

			// Calculate the magnitude (distance) of the direction vector
			float distance = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);

			// Check Distance
			if(distance > 300) {
				// Action if distance is too far..
				System.out.println("Too Far!");
				currentClickedMine.containsMouse = false;
				currentClickedMine.isLive = false;
				currentClickedMine = null;
				mouseIsClicked = false;
				return;
			}
			else if(distance < 20) {
				// Action if too close ... i.e Explosion Time
				game.ps.gameOverDisplay.visible = true;
				System.out.println("BOOM!");
				currentClickedMine.containsMouse = false;
				currentClickedMine.isLive = false;
				currentClickedMine = null;
				mouseIsClicked = false;
				return;
			}

			// Normalize the direction vector (convert it to a unit vector)
			if (distance != 0) {
				deltaX /= distance;
				deltaY /= distance;
			}

			// Scale the normalized vector by the desired velocity
			deltaX *= velocity;
			deltaY *= velocity;
			if(deltaX >= maxMineVelocity) deltaX = maxMineVelocity;
			if(deltaY >= maxMineVelocity) deltaY = maxMineVelocity;

			// Add Velocity...
			velocityX += deltaX;
			velocityY += deltaY;
		}
	}

	void gravity() {
		boolean collision = false;
		for(Collidable block:game.ps.blocks) {
			if(Phys.collision(new Rectangle(this.x, this.y + this.h, this.w, 1), new Rectangle(block.x, block.y, block.w, block.h))) {
				collision = true;
				break;
			}
		}

		if(!collision)
			velocityY += gravity;
		if(velocityY > maxGravity)
			velocityY = maxGravity;
		if(!onGround())
			this.y += velocityY;
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

	boolean onGround() {
		for(Collidable block:game.ps.blocks) {
			Rectangle a = new Rectangle(this.x, this.y + this.h, this.w, 1);
			Rectangle b = new Rectangle(block.x, block.y, block.w, block.h);
			if(Phys.collision(a, b)) {
				return true;
			}
		}

		return false;
	}

	void updateVX() {
		int minX = 0;
		int maxX = 240;
		float nx = this.x + velocityX;
		if(nx <= minX || nx >= maxX) {
			if(velocityX < 0) velocityX = Math.abs(velocityX)/2;
			else if(velocityX > 0) velocityX = -velocityX/2;
		}

		// Limits velocityX
		if(velocityX > maxXVelocity) velocityX = maxXVelocity;
		if(velocityX < -maxXVelocity) velocityX = -maxXVelocity;

		if(((this.x + velocityX) > minX) && ((this.x + velocityX) < maxX)) {
			this.x += velocityX;
		}

		if(velocityX < 0) {
			if(onGround()) {velocityX += groundFriction;}
			if(!onGround()) {velocityX += friction;}

			if(velocityX > 0)
				velocityX = 0;
		}
		else if(velocityX > 0) {
			if(onGround()) {velocityX -= groundFriction;}
			if(!onGround()) {velocityX -= friction;}

			if(velocityX < 0)
				velocityX = 0;
		}
	}

	void updateVY() {
		float   difference = 0;
		boolean collision  = false;

		for(Collidable block:game.ps.blocks) {
			Rectangle a = new Rectangle(this.x, (int)(this.y + velocityY), this.w, this.h);
			Rectangle b = new Rectangle(block.x, block.y, block.w, block.h);
			if(Phys.collision(a, b)) {
				collision = true;
				if(velocityY < 0)
					difference = (float)(block.y + block.h) - (this.y + velocityY);
				if(velocityY > 0)
					difference = (this.y + this.h + velocityY) - (float)block.y;
				break;
			}
		}
		if(collision) {
			if(velocityY < 0)
				this.y += (velocityY + difference);
			if(velocityY > 0)
				this.y += (velocityY - difference);
			velocityY = 0;
		}
		else {
			this.y += velocityY;
		}
	}

	public void render(Graphics2D g) {
		if(currentClickedMine != null) {
			g.setColor(Color.white);
			g.drawLine(x+w/2,y+h/2,currentClickedMine.x+currentClickedMine.w/2,currentClickedMine.y+ currentClickedMine.h/2);
		}

		g.setColor(Color.white);
		g.fillRect(x,y,w,h);
	}

	void resetJustPressedKeys() {
		space_key_just_pressed = false;
		r_key_just_pressed = false;
		f1_key_just_pressed = false;
		f2_key_just_pressed = false;
		f3_key_just_pressed = false;
	}

	void checkInputState() {
		if(a_btn_just_pressed || space_key_just_pressed)
			jump();

		if(f1_key_just_pressed) {
			gravityEnabled = !gravityEnabled;

			if(!gravityEnabled) {
				velocityX = 0;
				velocityY = 0;
			}
		}
		if(f2_key_just_pressed)
			infiniteJumping = !infiniteJumping;

		// Reset Keys Just Pressed
		resetJustPressedKeys();
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			space_key = true;
			space_key_just_pressed = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_F1) {
			f1_key = true;
			f1_key_just_pressed = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_F2) {
			f2_key = true;
			f2_key_just_pressed = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_F3) {
			f3_key = true;
			f3_key_just_pressed = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {space_key = false;}
	}

	public void mousePressed(MouseEvent e) {
		this.mouseIsClicked = true;

		if(currentClickedMine == null) {
			for(Mine m : game.ps.mines) {
				if(m.containsMouse) {
					currentClickedMine = m;
					m.isLive = true;
					break;
				}
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		if(currentClickedMine != null) {
			currentClickedMine.containsMouse = false;
			currentClickedMine.isLive = false;
			currentClickedMine = null;
		}
		this.mouseIsClicked = false;
	}
}
