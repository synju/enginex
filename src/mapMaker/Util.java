package mapMaker;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Util {
	public static BufferedImage[] getImages(String path, int w, int h, int dim) {
		BufferedImage[] images = null;
		try {
			BufferedImage spriteSheet = ImageIO.read(new File(path));
			int xc = spriteSheet.getWidth() / dim;
			int yc = spriteSheet.getHeight() / dim;
			images = new BufferedImage[yc * xc];

			int i = 0;
			for(int y = 0; y < yc; y++) {
				for(int x = 0; x < xc; x++) {
					images[i] = spriteSheet.getSubimage(x * dim, y * dim, dim, dim);
					i++;
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		return images;
	}
}
