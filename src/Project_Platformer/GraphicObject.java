package Project_Platformer;

import javax.swing.*;
import java.awt.*;

public class GraphicObject {
	Game game;
	int offsetX, offsetY, x, y, w, h;
	Image image;

	GraphicObject(Game game, String path, int offsetX, int offsetY) {
		this.game = game;
		image = new ImageIcon(path).getImage();
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		w = image.getWidth(null);
		h = image.getHeight(null);
	}

	public void update() {
//		x = game.ps.levelHandler.worldX + offsetX;
//		y = game.ps.levelHandler.worldY + offsetY;
	}

	public void render(Graphics2D g) {
		g.drawImage(image, x, y, null);
	}
}