package Spiral_Galaxy;

import java.awt.*;
import java.util.ArrayList;

public class Star {
	Game game;
	int  x, y, offsetX, offsetY;
	public ArrayList<Star> neighbors = new ArrayList<>();
	int width  = 4;
	int height = 4;

	Star(Game game, int offsetX, int offsetY) {
		this.game = game;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.x = game.ps.worldX + offsetX;
		this.y = game.ps.worldY + offsetY;
	}

	public void addNeighbor(Star neighbor) {
		neighbors.add(neighbor);
	}

	public void update() {
		this.x = game.ps.worldX + offsetX;
		this.y = game.ps.worldY + offsetY;
	}

	public void render(Graphics2D g) {
		g.fillOval(x - width / 2, y - height / 2, width, height);
	}
}
