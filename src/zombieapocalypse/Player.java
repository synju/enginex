package zombieapocalypse;

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

import enginex.EngineX;
import enginex.GameObject;

@SuppressWarnings("serial")
public class Player extends GameObject {
	ArrayList<Bullet>	bullets	= new ArrayList<>();
	ArrayList<Enemy>	enemies;

	double	x;
	double	y;
	int		w		= 20;
	int		h		= 30;
	Color	color	= Color.WHITE;

	int		health	= 100;
	int		hx;
	int		hy;
	float	hw;
	float	hh;

	double	fullSpeed		= 1.6;
	double	halfSpeed		= fullSpeed / 100 * 70;
	double	speed			= fullSpeed;
	boolean	moveLeft		= false;
	boolean	moveRight		= false;
	boolean	moveUp			= false;
	boolean	moveDown		= false;
	int		moveLeftCount	= 0;
	int		moveRightCount	= 0;
	int		moveUpCount		= 0;
	int		moveDownCount	= 0;

	boolean alive = true;

	float alpha = 1.0f;

	boolean	mouseDown	= false;
	double	mx			= 0;
	double	my			= 0;

	boolean	multishot			= false;
	boolean	shotFired			= false;
	int		shoot_cooldown		= 0;
	int		shootCooldowntime	= 1;

	int timeAlive = 0;

	Player(EngineX game) {
		super(game);
		x = game.getWidth() / 2 - w / 2;
		y = game.getHeight() / 2 - h / 2;

		hx = 0;
		hy = game.getHeight() - 50;
		hh = 20;
		hw = game.getWidth() / 100 * health;
	}

	public void update() {
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

			for(Iterator<Bullet> it = bullets.iterator(); it.hasNext();) {
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

	void healthpackUpdate() {
		for(Healthpack h:((PlayState)getCurrentState()).healthpacks) {
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

		if(shoot_cooldown > 0) {
			shoot_cooldown--;
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
			if(bullets.size() < 50 && shoot_cooldown == 0) {
				bullets.add(new Bullet(game, angle, x + w / 2, y + h / 2));
				shoot_cooldown = 20;
			}
			shotFired = true;
		}
	}
	
	boolean noCollision(double x, double y) {
		Rectangle tbounds = new Rectangle((int)x,(int)y,w,h);
		
		for(Enemy e:getEnemies())
			if(e.bounds.intersects(tbounds))
				return false;
		
		return true;
	}
	
	ArrayList<Enemy> getEnemies() {
		return ((PlayState)game.stateMachine.getCurrentState()).enemies;
	}

	void move() {
		if((moveUpCount + moveDownCount + moveLeftCount + moveRightCount) > 1)
			speed = halfSpeed;
		else
			speed = fullSpeed;

		if(moveUp)
			if(y > 0)
				if(noCollision(x,y-speed*2))
					y -= speed;
			
		if(moveDown)
			if(y + h < game.getHeight() - h)
				if(noCollision(x,y+speed*2))
					y += speed;
		
		if(moveLeft)
			if(x > 0)
				if(noCollision(x-speed*2,y))
					x -= speed;

		if(moveRight)
			if(x + w < game.getWidth())
				if(noCollision(x+speed*2,y))
					x += speed;

	}

	void mouseInfo() {
		try {
			Point p = game.getMousePosition();
			mx = p.getX();
			my = p.getY();
		}
		catch(Exception e) {}
	}

	void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}

	void enemyCollision() {
		for(Enemy e:((PlayState)getCurrentState()).enemies) {
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
		for(Bullet b:bullets) {
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
