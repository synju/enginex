package moo;

import enginex.GameObject;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Player extends GameObject {
	Game game;
	float x = 10*32;
	float y = 14*32;
	int w = 32;
	int h = 64;
//	int w = 16*3;
//	int h = 64;

	int speed = 5;
//	float jumpSpeed = 6.67f;
	float jumpSpeed = 6.7f;

	float velocityX = 0;
	float velocityY = 0;
	float friction = 0.5f;

	float gravity = 0.35f;
	float maxGravity = 12f;
	boolean gravityEnabled = true;

	ArrayList<Collidable> clist = new ArrayList<>();

//	int x1 = 200;
//	int y1 = 500;
//	int x2 = 600;
//	int y2 = 500;

	Boolean moveUp = false;
	Boolean moveDown = false;
	Boolean moveLeft = false;
	Boolean moveRight = false;

	public Player(Game game) {
		super(game);
		this.game = game;
	}

	public void update() {
		clist = ((PlayState)game.stateMachine.getCurrentState()).clist;

		gravity();
		move();

		updateVY();
		updateVX();

		// Collision Detection
//		Area area = new Area(new Rectangle((int)(this.x + velocityX), (int)(this.y), this.w, this.h));
//		Line2D line = new Line2D.Double(x1, y1, x2, y2);
//		if(Phys.lineCollision(area, line, 10))
//			System.out.println("Collision");
	}

	void gravity() {
		if(gravityEnabled) {
			boolean collision = false;
			for(Collidable c:clist) {
				if(Phys.collision(
						new Rectangle((int)this.x, (int)(this.y + this.h), this.w, 1),
						new Rectangle((int)c.x, (int)c.y, (int)c.w, (int)c.h))
				) {
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
		if(moveLeft) {
			if(velocityX > -speed) {
				velocityX -= speed;
				if(velocityX < -speed) {
					velocityX = -speed;
				}
			}
		}

		if(moveRight) {
			if(velocityX < speed) {
				velocityX += speed;
				if(velocityX > speed) {
					velocityX = speed;
				}
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
		if(onGround())
			velocityY = -jumpSpeed;
	}

	void resetPlayer() {
		this.x = 10*32;
		this.y = 14*32;
		velocityY = 0;
		velocityX = 0;
	}

	public void render(Graphics2D g) {
		g.setColor(Color.red);
		g.fillRect((int)x, (int)y, w, h);
//		g.drawLine(x1, y1, x2, y2);
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

		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			jump();
		}

		if(e.getKeyCode() == KeyEvent.VK_R) {
			resetPlayer();
		}
	}

	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_W)
			moveUp = false;
		if(e.getKeyCode() == KeyEvent.VK_A) {
			if(onGround()) velocityX = 0;
			moveLeft = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_S)
			moveDown = false;
		if(e.getKeyCode() == KeyEvent.VK_D) {
			if(onGround()) velocityX = 0;
			moveRight = false;
		}
	}

	public void mousePressed(MouseEvent e) {
		//		whatever.mousePressed(e);
	}

	public void mouseReleased(MouseEvent e) {
		//		whatever.mousePressed(e);
	}
}
