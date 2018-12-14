package towerdefense;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import enginex.GameObject;

@SuppressWarnings("serial")
public class Tower extends GameObject {
	TowerDefense	game;

	double				x, y;
	int						w, h;

	Rectangle			bounds;

	boolean				hover				= false;
	boolean				moveable		= false;
	boolean				pointerDown	= false;
	
	boolean captured = false;

	Point					m;

	public Tower(TowerDefense game, int x, int y, int w, int h, boolean moveable) {
		super(game);
		this.game = game;

		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.moveable = moveable;

		bounds = new Rectangle(x, y, w, h);
	}

	public void update() {
		m = game.getMousePosition();
		updateBounds();
		hover = contains(m);
		
		checkCaptured();
		
		if(pointerDown) {
			if(hover)
				captured = true;
		}
		else {
			captured = false;
		}
	}
	
	void checkCaptured() {
		if(captured) {
			this.x = m.x - this.w / 2;
			this.y = m.y - this.h / 2;
		}
	}

	public void render(Graphics2D g) {
		g.setColor(Color.white);
		g.drawRect((int)x, (int)y, (int)w, (int)h);
	}
	
	public void updateBounds() {
		bounds.x = (int)this.x;
		bounds.y = (int)this.y;
	}

	public boolean contains(Point m) {
		try {
			return bounds.contains(m);
		}
		catch(Exception e) {}
		
		return false;
	}

	@SuppressWarnings("unused")
	public void mousePressed(MouseEvent e) {
		Point m = game.getMousePosition();
		pointerDown = true;
	}

	@SuppressWarnings("unused")
	public void mouseReleased(MouseEvent e) {
		Point m = game.getMousePosition();
		pointerDown = false;
	}
}
