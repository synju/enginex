package HEC_Industries;

public class EmptyObject {
	Game game;
	public int                     offsetX, offsetY, x, y, w, h;

	EmptyObject(Game game, int offsetX, int offsetY, int width, int height) {
		this.game = game;

		this.offsetX = offsetX;
		this.offsetY = offsetY;
		x = game.ps.levelHandler.worldX + offsetX;
		y = game.ps.levelHandler.worldY + offsetY;
		this.w = width;
		this.h = height;
	}

	public void update() {
		x = game.ps.levelHandler.worldX + offsetX;
		y = game.ps.levelHandler.worldY + offsetY;
	}

	public int getWidth() {
		return w;
	}

	public int getHeight() {
		return h;
	}
}
