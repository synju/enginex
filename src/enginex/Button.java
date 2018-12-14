package enginex;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class Button extends GameObject {
	EngineX game;

	public boolean hover = false;
	
	String name = "";
	boolean displayName = false;

	boolean	hasSound	= false;
	boolean	soundPlayed	= false;
	Sound	sound;

	boolean	hasImages	= false;
	Image	defaultImage;
	Image	hoverImage;

	Point m;

	public Button(EngineX game, int w, int h) {
		super(game);
		this.game = game;
		this.x = 0;
		this.y = 0;
		this.w = w;
		this.h = h;
	}
	
	public Button(EngineX game, int x, int y, int w, int h) {
		super(game);
		this.game = game;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public Button(EngineX game, String name, int x, int y, int w, int h) {
		super(game);
		this.game = game;
		
		this.name = name;
		displayName = true;
		
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public Button(EngineX game, int x, int y, int w, int h, String defaultImagePath, String hoverImagePath) {
		super(game);
		this.game = game;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		setImages(defaultImagePath, hoverImagePath);
	}
	
	public Button(EngineX game, String name, int x, int y, int w, int h, String defaultImagePath, String hoverImagePath) {
		super(game);
		this.game = game;
		
		this.name = name;
		
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		
		setImages(defaultImagePath, hoverImagePath);
	}
	
	public Button(EngineX game, String name, int x, int y, int w, int h, String defaultImagePath, String hoverImagePath, String soundPath) {
		super(game);
		this.game = game;
		
		this.name = name;
		
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		
		setImages(defaultImagePath, hoverImagePath);
		
		// Sound
		setSound(soundPath);
	}

	public Button(EngineX game, int x, int y, int w, int h, String defaultImagePath, String hoverImagePath, String soundPath) {
		super(game);
		this.game = game;

		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;

		setImages(defaultImagePath, hoverImagePath);

		// Sound
		setSound(soundPath);
	}

	public void update() {
		hover = false;
		m = game.getMousePosition();
		if((m != null) && contains(m)) {
			hover = true;
			playSound();
		}
		else {
			soundPlayed = false;
		}
	}
	
	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setImages(String defaultImagePath, String hoverImagePath) {
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

	public void clickSound() {
		sound.play(0.1f);
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
			
			if(displayName) {
				Util.drawText((int)(x + 5), (int)(y + h/2), name, 25, Color.WHITE, g);
			}
		}
	}
}
