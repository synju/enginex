package enginex;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite {
	public static BufferedImage getSprite(int x, int y, int w, int h, String filepath) {
		BufferedImage	spriteSheet = loadSprite(filepath);
		return spriteSheet.getSubimage(x * w, y * h, w, h);
	}
	
	public static BufferedImage loadSprite(String filepath) {
		BufferedImage sprite = null;

		try {
			sprite = ImageIO.read(new File(filepath));
		}
		catch(IOException e) {
			e.printStackTrace();
		}

		return sprite;
	}
}
