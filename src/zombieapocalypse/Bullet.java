package zombieapocalypse;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import enginex.EngineX;
import enginex.GameObject;

@SuppressWarnings("serial")
public class Bullet extends GameObject {
	Color	color	= Color.BLACK;
	double	x;
	double	y;
	int		w		= 4;
	int		h		= w;
	double	angle;
	int		speed	= 9;
	boolean	used	= false;

	ArrayList<Enemy> enemies;

	Bullet(EngineX game, double angle, double x, double y) {
		super(game);

		this.x = x - w / 2;
		this.y = y - h / 2;
		this.angle = angle;
	}

	public void update() {
		updateBounds();
		outOfScreen();
		enemyCollision();

		if(!isDisposable()) {
			move();
		}
	}

	void outOfScreen() {
		if(x - w < 0 || y - h < 0 || x > game.window.getWidth() + w || y > game.window.getHeight() + w) {
			setDisposable(true);
		}
	}

	protected void updateBounds() {
		bounds.setLocation(new Point((int)x, (int)y));
		bounds.setSize(new Dimension(w, h));
	}

	void enemyCollision() {
		// Enemy Collision
		enemies = ((PlayState)getCurrentState()).enemies;

		for(Enemy e:enemies) {
			if(isColliding(e.bounds)) {
				if(!used) {
					setDisposable(true);
					e.setDisposable(true);
					used = true;
				}
			}
		}
	}

	void move() {
		double dx = (double)(Math.cos(angle) * speed);
		double dy = (double)(Math.sin(angle) * speed);

		x -= dx;
		y -= dy;
	}

	public void render(Graphics2D g) {
		if(!isDisposable()) {
			g.setColor(color);
			g.fillOval((int)x, (int)y, w, h);
		}
	}
}
