package platformer;

import java.awt.Color;
import java.awt.Graphics2D;

import enginex.GameObject;

@SuppressWarnings("serial")
public class MovementContainer extends GameObject {
	Platformer game;
	
	public MovementContainer(Platformer game, int x, int y, int w, int h) {
		super(game);
		this.game = game;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public void render(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.drawRect((int)x, (int)y, (int)w, (int)h);
	}
	
}
