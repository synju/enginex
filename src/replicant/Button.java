package replicant;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;

import enginex.GameObject;
import enginex.Sound;

@SuppressWarnings("serial")
public class Button extends GameObject {
	Replicants	game;
	boolean		hover		= false;
	Image		defaultImage;
	Image		hoverImage;
	Point		m;
	boolean		soundPlayed	= false;
	Sound		sound;
	boolean		hasSound	= false;

	public Button(Replicants game, int x, int y, int w, int h, String defaultImagePath, String hoverImagePath) {
		super(game);

		this.game = game;

		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;

		defaultImage = new ImageIcon(defaultImagePath).getImage();
		hoverImage = new ImageIcon(hoverImagePath).getImage();
	}

	public Button(Replicants game, int x, int y, int w, int h, String defaultImagePath, String hoverImagePath, String soundPath) {
		super(game);
		this.game = game;

		// Position & Dimension
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;

		// Images
		defaultImage = new ImageIcon(defaultImagePath).getImage();
		hoverImage = new ImageIcon(hoverImagePath).getImage();

		// Sound
		sound = new Sound(soundPath);
		hasSound = true;
	}

	public boolean contains(Point mousePosition) {
		Point m = mousePosition;

		if(m.x > this.x && m.x < this.x + this.w && m.y > this.y && m.y < this.y + this.h)
			return true;

		return false;
	}

	public void update() {
		hover = false;
		m = game.getMousePosition();

		if((m != null) && (m.x > this.x && m.x < this.x + this.w) && (m.y > this.y && m.y < this.y + this.h)) {
			hover = true;
			if(soundPlayed == false) {
				if(hasSound) {
					sound.play(0.1f);
					soundPlayed = true;
				}
			}
		}
		else {
			soundPlayed = false;
		}
	}

	public void render(Graphics2D g) {
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

		if(!hover)
			g.drawImage(defaultImage, (int)x, (int)y, null);
		else
			g.drawImage(hoverImage, (int)x, (int)y, null);
	}
}
