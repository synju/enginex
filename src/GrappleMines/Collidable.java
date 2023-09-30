package GrappleMines;

import java.awt.*;

public class Collidable {
	Game game;
	boolean initialized = false;
	int offsetX, offsetY, x, y, w, h;

	Collidable(Game game, int offsetX, int offsetY, int width, int height) {
		this.game = game;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.x = game.ps.worldX + offsetX;
		this.y = game.ps.worldY + offsetY;
		this.w = width;
		this.h = height;
	}

	void postInit() {
		// Check if initialized
		if(initialized)
			return;

		// Initialization...

		// Complete initialization
		initialized = true;
	}

	public void update() {
		// This is Run Only Once...
		postInit();

		x = game.ps.worldX + offsetX;
		y = game.ps.worldY + offsetY;
	}

	public void render(Graphics2D g) {
		g.setColor(Color.gray);
		g.fillRect(x,y,w,h);
	}
}
