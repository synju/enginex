package titanclones;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;

import javax.swing.ImageIcon;

public class Collidable {
	TitanClones	game;
	double		x;
	double		y;
	int			w;
	int			h;
	boolean		visible	= false;
	Image		bg		= new ImageIcon("res/titanclones/wall_tile.png").getImage();

	public Collidable(TitanClones game, int x, int y, int w, int h) {
		this.game = game;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public void render(Graphics2D g) {
		AffineTransform at = new AffineTransform();
		at.scale(game.scale, game.scale);
		at.translate((x / game.scale), (y / game.scale));
		g.drawImage(bg, at, null);

		if(visible) {
			g.setColor(Color.white);
			g.fillRect((int)x, (int)y, (int)w, (int)h);
		}
	}
}
