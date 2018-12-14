package platformer;

import java.awt.Color;
import java.awt.Graphics2D;

import enginex.GameObject;

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
	
	public Collidable(Platformer game, int x, int y) {
		super(game);
		this.game = game;
		
		this.x = x;
		this.y = y;
		this.w = 50;
		this.h = 50;
	}
	
	public void render(Graphics2D g) {
		if((this.x > (0 - this.w)) && ((this.x + this.w) < (game.width + this.w))) {
			if((this.y > (0 - this.h)) && ((this.y + this.h) < (game.height + this.h))) {
		g.setColor(Color.WHITE);
		g.fillRect((int)x, (int)y, (int)w, (int)h);
			}
		}
	}
}
