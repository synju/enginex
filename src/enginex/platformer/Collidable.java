package enginex.platformer;

import java.awt.Color;
import java.awt.Graphics2D;

import enginex.core.GameObject;

@SuppressWarnings("serial")
public class Collidable extends GameObject {
	Platformer game;

	public Collidable(Platformer game, int x, int y, int w, int h) {
		super(game);
		this.game = game;
		
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public void render(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fillRect((int)x, (int)y, (int)w, (int)h);
	}
}
