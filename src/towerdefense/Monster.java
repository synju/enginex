package towerdefense;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import enginex.GameObject;

@SuppressWarnings("serial")
public class Monster extends GameObject {
	TowerDefense	game;

	double				x, y;
	int						w, h;

	Rectangle			bounds;
	
	float speed = 0.2f;
	
	public Monster(TowerDefense game, int x, int y, int w, int h) {
		super(game);
		this.game = game;

		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		
		bounds = new Rectangle(x, y, w, h);
	}
	
	public void update() {
		updateBounds();
		move();
	}
	
	public void move() {
		this.x -= speed;
	}
	
	public void render(Graphics2D g) {
		g.setColor(Color.white);
		g.drawRect((int)x, (int)y, (int)w, (int)h);
	}
	
	public void updateBounds() {
		bounds.x = (int)this.x;
		bounds.y = (int)this.y;
	}
}
