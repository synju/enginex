package enginex.core;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class Button extends GameObject {
	EngineX	game;
	
	boolean	hover				= false;
	
	boolean	hasSound		= false;
	boolean	soundPlayed	= false;
	Sound		sound;
	
	boolean	hasImages		= false;
	Image		defaultImage;
	Image		hoverImage;
	
	Point		m;
	
	public Button(EngineX game, int x, int y, int w, int h) {
		super(game);
		this.game = game;
		
		// Position & Dimension
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public Button(EngineX game, int x, int y, int w, int h, String defaultImagePath, String hoverImagePath) {
		super(game);
		this.game = game;
		
		// Position & Dimension
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		
		// Images
		setImages(defaultImagePath, hoverImagePath);
	}
	
	public Button(EngineX game, int x, int y, int w, int h, String defaultImagePath, String hoverImagePath, String soundPath) {
		super(game);
		this.game = game;
		
		// Position & Dimension
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		
		// Images
		setImages(defaultImagePath, hoverImagePath);
		
		// Sound
		setSound(soundPath);
	}
	
	public void update() {
		hover = false;
		m = game.getMousePosition();
		if((m != null) && contains(m)) {
			hover = true;
			// do Actions...
			playSound();
		}
		else {
			soundPlayed = false;
		}
	}
	
	public void setImages(String defaultImagePath, String hoverImagePath) {
		// Images
		defaultImage = new ImageIcon(defaultImagePath).getImage();
		hoverImage = new ImageIcon(hoverImagePath).getImage();
		hasImages = true;
	}
	
	public void setSound(String soundPath) {
		sound = new Sound(soundPath);
		hasSound = true;
	}
	
	public boolean contains(Point mousePosition) {
		Point m = mousePosition;
		
		if(m.x > this.x && m.x < this.x + this.w && m.y > this.y && m.y < this.y + this.h)
			return true;
		
		return false;
	}
	
	public boolean containsMouse() {
		Point m = game.getMousePosition();
		
		if(m.x > this.x && m.x < this.x + this.w && m.y > this.y && m.y < this.y + this.h)
			return true;
		
		return false;
	}
	
	public void playSound() {
		if(soundPlayed == false) {
			if(hasSound) {
				sound.play(0.1f);
				soundPlayed = true;
			}
		}
	}
	
	public void render(Graphics2D g) {
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		
		if(hasImages) {
			if(!hover)
				g.drawImage(defaultImage, (int)x, (int)y, null);
			else
				g.drawImage(hoverImage, (int)x, (int)y, null);
		}
		else {
			if(!hover) {
				g.setColor(Color.GREEN);
				g.fillRect((int)x, (int)y, (int)w, (int)h);
			}
			else {
				g.setColor(Color.RED);
				g.fillRect((int)x, (int)y, (int)w, (int)h);
			}
		}
	}
}
