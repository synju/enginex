package Project_Platformer;

public class EmptyObject {
	Game game;
	int offsetX, offsetY, x, y, w, h;

	EmptyObject(Game game, int offsetX, int offsetY, int width, int height) {
		this.game = game;

		this.offsetX = offsetX;
		this.offsetY = offsetY;
		x = game.ps.worldX + offsetX;
		y = game.ps.worldY + offsetY;
		this.w = width;
		this.h = height;
	}

	public void update() {
		x = game.ps.worldX + offsetX;
		y = game.ps.worldY + offsetY;
	}

	public int getWidth() {
		return w;
	}

	public int getHeight() {
		return h;
	}
}
