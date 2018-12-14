package towerdefense;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import enginex.GameObject;

@SuppressWarnings("serial")
public class Little_GUI extends GameObject {
	TowerDefense game;

	double	x, y;
	int			w	= 800;
	int			h	= 150;

	Rectangle bounds;

	boolean	hover		= false;
	boolean	pointerDown	= false;

	boolean captured = false;

	Point m;

	boolean	initialized	= false;
	boolean	visible		= false;
	boolean	enabled		= false;

	int	gameHeight;
	int	hiddenHeight	= 30;

	public Little_GUI(TowerDefense game) {
	super(game);
	this.game = game;
	gameHeight = game.height - 29;
	}

	public void create() {
	if(!initialized) {
		this.x = game.width / 2 - this.w / 2;
		this.y = gameHeight - hiddenHeight;

		bounds = new Rectangle((int)this.x, (int)this.y, this.w, this.h);
	}
	initialized = true;
	}

	public void toggleMenu() {
	if(!visible) {
		this.y = gameHeight - this.h;
		visible = true;
		enabled = true;
	}
	else {
		this.y = gameHeight - hiddenHeight;
		visible = false;
		enabled = false;
	}
	}

	public void update() {
	create();

	m = game.getMousePosition();
	updateBounds();
	hover = contains(m);
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

	if(hover)
		toggleMenu();
	}

	@SuppressWarnings("unused")
	public void mouseReleased(MouseEvent e) {
	Point m = game.getMousePosition();
	pointerDown = false;
	}
}
