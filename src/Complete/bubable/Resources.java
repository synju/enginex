package Complete.bubable;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class Resources {
	Image[] wallImages;
	Image[] dungeonSet;

	Resources() {
		wallImages = loadWallImages();
		dungeonSet = loadDungeonImages();
	}

	private Image[] loadWallImages() {
		return new Image[]{
				new ImageIcon("src/Complete.bubable/resources/0.png").getImage(),
				new ImageIcon("src/Complete.bubable/resources/0001.png").getImage(),
				new ImageIcon("src/Complete.bubable/resources/2.png").getImage(),
				new ImageIcon("src/Complete.bubable/resources/3.png").getImage(),
				new ImageIcon("src/Complete.bubable/resources/4.png").getImage(),
				new ImageIcon("src/Complete.bubable/resources/5.png").getImage(),
				new ImageIcon("src/Complete.bubable/resources/6.png").getImage(),
				new ImageIcon("src/Complete.bubable/resources/7.png").getImage(),
				new ImageIcon("src/Complete.bubable/resources/8.png").getImage(),
				new ImageIcon("src/Complete.bubable/resources/9.png").getImage(),
				new ImageIcon("src/Complete.bubable/resources/10.png").getImage(),
				new ImageIcon("src/Complete.bubable/resources/11.png").getImage(),
				new ImageIcon("src/Complete.bubable/resources/12.png").getImage(),
				new ImageIcon("src/Complete.bubable/resources/13.png").getImage(),
				new ImageIcon("src/Complete.bubable/resources/14.png").getImage(),
				new ImageIcon("src/Complete.bubable/resources/15.png").getImage()
		};
	}

	private Image[] loadDungeonImages() {
//		Image[] collection = loadImages("src/Complete.bubable/resources/cavesofgallet/cavesofgallet_tiles.png", 8);
		Image[] collection = loadImages("src/Complete.bubable/resources/cavesofgallet/custom.png", 8);

		return new Image[]{
				collection[15],
				collection[11],
				collection[12],
				collection[8],
				collection[3],
				collection[7],
				collection[0],
				collection[4],
				collection[14],
				collection[10],
				collection[13],
				collection[9],
				collection[2],
				collection[6],
				collection[1],
				collection[5]
		};
	}

	// Utility Functions
	private Image[] loadImages(String path, int tileSize) {
		Image[] imageSet;
		ArrayList<Image> list = new ArrayList<>();

		BufferedImage[] rf = getImages(path, tileSize);
		for(Image tx:rf)
			list.add(tx);

		imageSet = new Image[list.size()];
		for(int i = 0; i < imageSet.length; i++)
			imageSet[i] = list.get(i);

		return imageSet;
	}
	private BufferedImage[] getImages(String path, int dim) {
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
