package zombieapocalypse;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import enginex.EngineX;
import enginex.GameObject;

@SuppressWarnings("serial")
public class Enemy extends GameObject {
	ArrayList<Enemy> enemies;

	double		x		= 500;
	double		y		= 500;
	static int	WIDTH	= 20;
	static int	HEIGHT	= 30;
	int			w		= WIDTH;
	int			h		= HEIGHT;
	Color		color	= Color.GREEN;
	float		alpha	= 1.0f;
	boolean		canMove	= true;

	double	slowestSpeed	= 1;
	double	maxSpeed		= 0.70;
	double	speed;

	int		speedIncreaseCount	= 0;
	int		sic					= 100;
	double	speedIncreaseAmount	= 0.01;

	int biteDamage = 3;

	Player player;

	int biteCooldown = 0;

	double dx, dy;

	double id = (double)(Math.random() * 9999999);

	Enemy(EngineX game, double x, double y) {
		super(game);
		this.maxSpeed = ((PlayState)game.stateMachine.getCurrentState()).player.fullSpeed;
		this.x = x;
		this.y = y;
		speed = slowestSpeed + (double)(Math.random() * maxSpeed);
	}

	public void update() {
		if(player == null) {
			player = ((PlayState)getCurrentState()).player;
		}
		updateBounds();
		enemyCollision();
		playerCollision();
		move();
		increaseSpeed();
	}

	void increaseSpeed() {
		if(speedIncreaseCount < sic) {
			speedIncreaseCount++;
		}
		else {
			speed += speedIncreaseAmount;
			speedIncreaseCount = 0;
		}
	}

	protected void updateBounds() {
		bounds.setLocation(new Point((int)x, (int)y));
		bounds.setSize(new Dimension(w, h));
	}

	void enemyCollision() {
		this.enemies = ((PlayState)getCurrentState()).enemies;
		for(Enemy e:enemies) {
			if(this.id != e.id) {
				if(this.bounds.intersects(e.bounds)) {
					//					System.out.println("Enemy Collision Detected!");

					// Not working as expected...
					//					canMove = false;
				}
			}
		}
	}

	void playerCollision() {
		if(bounds.intersects(player.bounds)) {
			if(biteCooldown == 0) {
				bite();
				biteCooldown = 100;
			}
		}
		if(biteCooldown > 0) {
			biteCooldown--;
		}
	}

	void bite() {
		if(player.health > 0) {
			player.health -= biteDamage;
			if(player.health < 0) {
				player.health = 0;
			}
			//			System.out.println("Ouch!!! Player Health: " + player.health + "%");
		}
	}

	void move() {
		if(canMove) {
			double angle = Math.atan2(y - ((PlayState)getCurrentState()).player.y, x - ((PlayState)getCurrentState()).player.x);

			dx = (double)(Math.cos(angle) * speed);
			dy = (double)(Math.sin(angle) * speed);

			x -= dx;
			y -= dy;
		}
	}

	public void render(Graphics2D g) {
		//		g.setColor(color);
		//		g.fillRect((int)x, (int)y, w, h);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		g.drawImage(((PlayState)getCurrentState()).enemyImage, (int)x - 2, (int)y - 2, null);
	}
}
