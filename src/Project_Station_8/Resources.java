package Station_8;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class Resources {
	Game game;
	Image[] wallImages;
	Image[] dungeonSet;
	Image[] scifi_set_000;
	Image[] scifi_set_001;

	Resources(Game game) {
		this.game = game;
		wallImages = loadWallImages();
		dungeonSet = loadDungeonImages();
		scifi_set_000 = loadScifiImages("scifi_floor_000.png");
		scifi_set_001 = loadScifiImages("scifi_floor_001.png");
	}

	private Image[] loadWallImages() {
		return new Image[]{
				new ImageIcon("src/"+game.gameName+"/resources/0.png").getImage(),
				new ImageIcon("src/"+game.gameName+"/resources/1.png").getImage(),
				new ImageIcon("src/"+game.gameName+"/resources/2.png").getImage(),
				new ImageIcon("src/"+game.gameName+"/resources/3.png").getImage(),
				new ImageIcon("src/"+game.gameName+"/resources/4.png").getImage(),
				new ImageIcon("src/"+game.gameName+"/resources/5.png").getImage(),
				new ImageIcon("src/"+game.gameName+"/resources/6.png").getImage(),
				new ImageIcon("src/"+game.gameName+"/resources/7.png").getImage(),
				new ImageIcon("src/"+game.gameName+"/resources/8.png").getImage(),
				new ImageIcon("src/"+game.gameName+"/resources/9.png").getImage(),
				new ImageIcon("src/"+game.gameName+"/resources/10.png").getImage(),
				new ImageIcon("src/"+game.gameName+"/resources/11.png").getImage(),
				new ImageIcon("src/"+game.gameName+"/resources/12.png").getImage(),
				new ImageIcon("src/"+game.gameName+"/resources/13.png").getImage(),
				new ImageIcon("src/"+game.gameName+"/resources/14.png").getImage(),
				new ImageIcon("src/"+game.gameName+"/resources/15.png").getImage()
		};
	}

	private Image[] loadScifiImages(String pathFile) {
		String path = "src/" + game.gameName + "/resources/floors/"+pathFile;
		Image[] collection = loadImages(path, game.dim);
		return new Image[]{collection[15], collection[11], collection[12], collection[8], collection[3], collection[7], collection[0], collection[4], collection[14], collection[10], collection[13], collection[9], collection[2], collection[6], collection[1], collection[5]};
	}

	private Image[] loadDungeonImages() {
		String path = "src/" + game.gameName + "/resources/custom5-32x32.png";

//		Image[] collection = loadImages(path, 8);
		Image[] collection = loadImages(path, game.dim);

		return new Image[]{collection[15], collection[11], collection[12], collection[8], collection[3], collection[7], collection[0], collection[4], collection[14], collection[10], collection[13], collection[9], collection[2], collection[6], collection[1], collection[5]};
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
