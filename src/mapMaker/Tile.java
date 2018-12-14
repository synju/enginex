package mapMaker;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

@SuppressWarnings("serial")
public class Tile extends Block {
	Image image;

	public Tile(MapMaker game, int x, int y, int xl, int yl, int w, int h, int layer, Image image) {
		super(game, x, y, xl, yl, w, h, layer);
		this.image = image;
	}
	
	public void render(Graphics2D g) {
		AffineTransform at = new AffineTransform();
		at.translate((int)(getState().ox+(this.x*game.scale)), (int)(getState().oy+(this.y*game.scale)));
		at.scale(game.scale, game.scale);
		g.drawImage(image, at, null);
	}
}
