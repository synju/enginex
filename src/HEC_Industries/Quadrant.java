package HEC_Industries;

import java.util.ArrayList;

public class Quadrant {
	Game game;
	int  x;
	int  y;
	static int w = 8;
	static int h = 8;
	ArrayList<Block> blocks;
	Chunk            chunk;

	Quadrant(Game game, int x, int y, Chunk chunk) {
		this.game = game;
		this.x = x;
		this.y = y;
		this.blocks = new ArrayList<>();
		this.chunk = chunk;
	}

	int getX() {
		return chunk.x + (this.x * getWidth());
	}

	int getY() {
		return chunk.y + (this.y * getHeight());
	}

	int getWidth() {
		return (Quadrant.w * Block.blockWidth);
	}

	int getHeight() {
		return (Quadrant.h * Block.blockHeight);
	}

	boolean containsEntity(int ex, int ey, int ew, int eh) {
		int e_center_x = ex + (ew / 2);
		int e_center_y = ey + (eh / 2);

		int quadrant_x = getX();
		int quadrant_y = getY();
		int quadrant_w = getWidth();
		int quadrant_h = getHeight();

		return (e_center_x >= quadrant_x && e_center_x <= quadrant_x + quadrant_w) && (e_center_y >= quadrant_y && e_center_y <= quadrant_y + quadrant_h);
	}

}
