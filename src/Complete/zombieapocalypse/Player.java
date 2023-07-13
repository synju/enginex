package Complete.zombieapocalypse;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;

import EngineX.EngineX;
import EngineX.GameObject;
import Project_Platformer.Config;
import com.studiohartman.jamepad.ControllerState;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Util;

@SuppressWarnings("serial")
public class Player extends GameObject {
	ArrayList<Bullet> bullets = new ArrayList<>();
	ArrayList<Enemy> enemies;

	double x;
	double y;
	int w = 20;
	int h = 30;
	Color color = Color.WHITE;

	int health = 50;
	int hx;
	int hy;
	float hw;
	float hh;

	double fullSpeed = 1.6;
	double halfSpeed = fullSpeed / 100 * 70;
	double speed = fullSpeed;
	boolean moveLeft = false;
	boolean moveRight = false;
	boolean moveUp = false;
	boolean moveDown = false;
	int moveLeftCount = 0;
	int moveRightCount = 0;
	int moveUpCount = 0;
	int moveDownCount = 0;

	boolean alive = true;

	float alpha = 1.0f;

	boolean mouseDown = false;
	double mx = 0;
	double my = 0;

	boolean multishot = false;
	boolean shotFired = false;
	int shoot_cooldown = 0;
	int shootCooldowntime = 1;
	double angle = 0;

	int timeAlive = 0;

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

	boolean initialized = false;

	// Playstate
	PlayState ps;
	Game game;

	Player(Game game) {
		super(game);
		this.game = game;

	}

	void postInit() {
		if(!initialized) {
			ps = this.game.playState;
			x = game.getWidth() / 2 - w / 2;
			y = game.getHeight() / 2 - h / 2;

			hx = 0;
			hy = game.getHeight() - 50;
			hh = 20;
			hw = game.getWidth() / 100 * health;

			initialized = true;
		}
	}

	public void update(Boolean paused) {
		postInit();

		if(!paused) {
			if(health == 0) {
				alive = false;
				timeAlive = ((PlayState)getCurrentState()).getElapsedSeconds();
			}
			if(alive) {
				updateBounds();
				move();
				mouseInfo();
				enemyCollision();
				shoot();
				healthpackUpdate();

				for(Iterator<Bullet> it = bullets.iterator(); it.hasNext(); ) {
					GameObject b = it.next();
					if(b.isDisposable()) {
						// Dispose of Unwanted Game Objects
						it.remove();
					}
					else {
						if(b.updateEnabled) {
							// Update Game Object
							b.update();
						}
					}
				}
			}
		}

		if(back_btn) {
			((PlayState)getCurrentState()).resetGame();
		}

		if(controller.startJustPressed) {
			((PlayState)getCurrentState()).pauseGame();
		}

		if(controller.lbJustPressed) {
			System.exit(0);
		}
	}

	void healthpackUpdate() {
		for(Healthpack h : ((PlayState)getCurrentState()).healthpacks) {
			if(bounds.intersects(h.bounds)) {
				if(health < 100 && health > 0 && h.used == false) {
					health += 25;
					if(health > 100) {
						health = 100;
					}
					h.setDisposable(true);
					h.used = true;
				}
				((PlayState)getCurrentState()).healthpackCounter = ((PlayState)getCurrentState()).healthpackCount;
			}
		}
	}

	protected void updateBounds() {
		bounds.setLocation(new Point((int)x, (int)y));
		bounds.setSize(new Dimension(w, h));
	}

	void shoot() {
		if(mouseDown) {
			if(multishot)
				multiShot();
			else
				singleShot();
		}

		if(controller.rightStickMagnitude > 0.75) {
			double rawAngle = controller.rightStickAngle;

			if(rawAngle >= 0 && rawAngle < 90) {
				double percentage = (rawAngle / 90) * 100;
				double corrected_percentage = Math.abs(percentage - 100);

				double base_percentage = 1.65 / 100;
				angle = ((base_percentage * corrected_percentage) + 1.65) - 0.20;
			}

			if(rawAngle >= 90 && rawAngle < 180) {
				rawAngle = Math.abs(rawAngle - 180);
				double percentage = (rawAngle / 90) * 100;

				double base_percentage = 1.65 / 100;
				angle = (base_percentage * percentage);
			}

			if(rawAngle >= -90 && rawAngle < 0) {
				rawAngle += 90;
				double percentage = (rawAngle / 90) * 100;

				double base_percentage = 1.65 / 100;
				angle = -1.5 - (base_percentage * percentage);
			}

			//			System.out.println(rawAngle);
			if(rawAngle >= -180 && rawAngle < -90) {
				rawAngle += 180;
				double percentage = (rawAngle / 90) * 100;

				double base_percentage = 1.65 / 100;
				angle = 0 - (base_percentage * percentage);
			}
		}

		if(x_btn) {
			angle = 0;
			singleShot(angle);
		}

		if(y_btn) {
			angle = 1.5;
			singleShot(angle);
		}

		if(b_btn) {
			angle = 3.15;
			singleShot(angle);
		}

		if(a_btn) {
			angle = -1.5;
			singleShot(angle);
		}

		if(rb_btn) {
			singleShot(angle);
		}

		if(shoot_cooldown > 0) {
			shoot_cooldown--;
			if(shoot_cooldown <= 0) {
				shotFired = false;
			}
		}
	}

	void multiShot() {
		double angle = Math.atan2((y + h / 2) - my, (x + w / 2) - mx);
		if(bullets.size() < 50 && shoot_cooldown == 0) {
			bullets.add(new Bullet(game, angle, x + w / 2, y + h / 2));
			shoot_cooldown = shootCooldowntime;
		}
	}

	void singleShot() {
		if(!shotFired) {
			double angle = Math.atan2((y + h / 2) - my, (x + w / 2) - mx);
			//			System.out.println(angle);
			if(bullets.size() < 50 && shoot_cooldown == 0) {
				bullets.add(new Bullet(game, angle, x + w / 2, y + h / 2));
				shoot_cooldown = 20;
			}
			shotFired = true;
		}
	}

	void singleShot(double angle) {
		if(!shotFired) {
			//			double angle = Math.atan2((y + h / 2) - my, (x + w / 2) - mx);
			//			System.out.println(angle);
			if(bullets.size() < 50 && shoot_cooldown == 0) {
				bullets.add(new Bullet(game, angle, x + w / 2, y + h / 2));
				shoot_cooldown = 20;
			}
			shotFired = true;
		}
	}

	boolean noCollision(double x, double y) {
		Rectangle tbounds = new Rectangle((int)x, (int)y, w, h);

		for(Enemy e : getEnemies())
			if(e.bounds.intersects(tbounds))
				return false;

		return true;
	}

	ArrayList<Enemy> getEnemies() {
		return ((PlayState)game.stateMachine.getCurrentState()).enemies;
	}

	void move() {
		if(game.controllerEnabled)
			checkControllerState();

		if((moveUpCount + moveDownCount + moveLeftCount + moveRightCount) > 1)
			speed = halfSpeed;
		else
			speed = fullSpeed;

		if(moveUp)
			if(y > 0)
				if(noCollision(x, y - speed * 2))
					y -= speed;

		if(moveDown)
			if(y + h < game.getHeight() - h)
				if(noCollision(x, y + speed * 2))
					y += speed;

		if(moveLeft)
			if(x > 0)
				if(noCollision(x - speed * 2, y))
					x -= speed;

		if(moveRight)
			if(x + w < game.getWidth())
				if(noCollision(x + speed * 2, y))
					x += speed;

	}

	public void checkControllerState() {
		moveLeft = (dpadLeft_btn || leftStickX_left);
		moveRight = (dpadRight_btn || leftStickX_right);
		moveUp = (dpadUp_btn || leftStickY_up);
		moveDown = (dpadDown_btn || leftStickY_down);
	}

	void mouseInfo() {
		try {
			Point p = game.getMousePosition();
			mx = p.getX();
			my = p.getY();
		}
		catch(Exception e) {
		}
	}

	void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	void enemyCollision() {
		for(Enemy e : ((PlayState)getCurrentState()).enemies) {
			if(isColliding(e.bounds)) {
				e.canMove = false;
			}
			else {
				if(!e.canMove) {
					e.canMove = true;
				}
			}
		}
	}

	public void render(Graphics2D g) {
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		for(Bullet b : bullets) {
			b.render(g);
		}

		//		g.setColor(color);
		if(!alive) {
			//			g.setColor(Color.BLUE);
			g.drawImage(((PlayState)getCurrentState()).deadImage, (int)x, (int)y, null);
		}
		else {
			g.drawImage(((PlayState)getCurrentState()).playerImage, (int)x, (int)y, null);
		}
		//		g.fillRect((int)x, (int)y, w, h);
	}

	public void controllerUpdate(ControllerState controller) {
		// Update Controller State
		this.controller = controller;

		// Start and Back/Select Button
		this.start_btn = (controller.start);
		this.back_btn = (controller.back);

		// DPad
		this.dpadUp_btn = (controller.dpadUp);
		this.dpadDown_btn = (controller.dpadDown);
		this.dpadLeft_btn = (controller.dpadLeft);
		this.dpadRight_btn = (controller.dpadRight);

		// A, B, X, Y
		this.a_btn = (controller.a);
		this.b_btn = (controller.b);
		this.x_btn = (controller.x);
		this.y_btn = (controller.y);

		// Left Button, Left Trigger
		this.lb_btn = (controller.lb);
		this.leftTrigger_btn = (controller.leftTrigger > 0.5);

		// Right Button, Right Trigger
		this.rb_btn = (controller.rb);
		this.rightTrigger_btn = (controller.rightTrigger > 0.5);

		// Left Stick
		this.leftStick_btn = (controller.leftStickClick);
		this.leftStickY_up = (controller.leftStickY > 0.5);
		this.leftStickY_down = (controller.leftStickY < -0.5);
		this.leftStickX_left = (controller.leftStickX < -0.1);
		this.leftStickX_right = (controller.leftStickX > 0.1);

		// Right Stick
		this.rightStick_btn = (controller.rightStickClick);
		this.rightStickY_up = (controller.rightStickY > 0.5);
		this.rightStickY_down = (controller.rightStickY < -0.5);
		this.rightStickX_left = (controller.rightStickX < -0.5);
		this.rightStickX_right = (controller.rightStickX > 0.5);
	}

	void renderHealth(Graphics2D g) {
		hw = game.getWidth() / 100 * health;
		g.setColor(Color.GREEN);
		g.fillRect(0, (int)(game.getHeight() - hh), (int)hw, (int)hh);
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_W) {
			moveUp = true;
			moveUpCount = 1;
		}
		if(e.getKeyCode() == KeyEvent.VK_S) {
			moveDown = true;
			moveDownCount = 1;
		}
		if(e.getKeyCode() == KeyEvent.VK_A) {
			moveLeft = true;
			moveLeftCount = 1;
		}
		if(e.getKeyCode() == KeyEvent.VK_D) {
			moveRight = true;
			moveRightCount = 1;
		}
	}

	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_W) {
			moveUp = false;
			moveUpCount = 0;
		}
		if(e.getKeyCode() == KeyEvent.VK_S) {
			moveDown = false;
			moveDownCount = 0;
		}
		if(e.getKeyCode() == KeyEvent.VK_A) {
			moveLeft = false;
			moveLeftCount = 0;
		}
		if(e.getKeyCode() == KeyEvent.VK_D) {
			moveRight = false;
			moveRightCount = 0;
		}
	}

	public void mousePressed(MouseEvent e) {
		mouseDown = true;
	}

	public void mouseReleased(MouseEvent e) {
		mouseDown = false;
		shotFired = false;
	}
}
