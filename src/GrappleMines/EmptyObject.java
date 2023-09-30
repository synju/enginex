package GrappleMines;

public class EmptyObject {
	Game game;
	public int offsetX, offsetY, x, y, w, h;

	EmptyObject(Game game, int offsetX, int offsetY, int width, int height) {
		this.game = game;

		this.offsetX = offsetX;
		this.offsetY = offsetY;
		x = game.ps.worldX + offsetX;
		y = game.ps.worldY + offsetY;
		this.w = width;
		this.h = height;
	}

	void update() {
		x = game.ps.worldX + offsetX;
		y = game.ps.worldY + offsetY;
	}

	int getWidth() {
		return w;
	}

	int getHeight() {
		return h;
	}
}
