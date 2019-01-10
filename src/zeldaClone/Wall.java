package zeldaClone;

import java.awt.*;

public class Wall {
	public Rectangle bounds;

	public Wall(int x, int y, int width, int height) {
		bounds = new Rectangle(x, y, width, height);
	}
}