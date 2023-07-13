package Project_Platformer;

import EngineX.GameObject;

import java.awt.*;

@SuppressWarnings("serial")
public class Collidable extends GameObject {
	Game game;

	int offsetX;
	int offsetY;

	public Collidable(Game game, int offsetX, int offsetY, int width, int height) {
		super(game);
		this.game = game;

		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.x = game.ps.levelHandler.worldX + offsetX;
		this.y = game.ps.levelHandler.worldY + offsetY;
		this.w = width;
		this.h = height;
	}

	public void update() {
		x = game.ps.levelHandler.worldX + offsetX;
		y = game.ps.levelHandler.worldY + offsetY;
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
