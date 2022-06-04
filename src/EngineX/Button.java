package EngineX;

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

	public boolean	hasImages	= false;
	public boolean hasHoverImage = true;
	Image	defaultImage;
	Image	hoverImage;

	// Offsets
	boolean offsetEnabled = false;
	int xOffset = 0;
	int yOffset = 0;

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

	public void setImage(Image defaultImage) {
		this.defaultImage = defaultImage;
	}
	
	public void setImages(String defaultImagePath, String hoverImagePath) {
		defaultImage = new ImageIcon(defaultImagePath).getImage();
		hoverImage = new ImageIcon(hoverImagePath).getImage();
		hasImages = true;
	}

	public void setOffsets(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}

	public void toggleOffset(boolean v) {
		this.offsetEnabled = v;
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
		try {
			Point m = game.getMousePosition();
			if(m.x > this.x && m.x < this.x + this.w && m.y > this.y && m.y < this.y + this.h)
				return true;
		}
		catch(Exception e) {
			// Do Nothing..
		}
		return false;
	}

	public void playSound() {
		if(hasSound) {
			if(soundPlayed == false) {
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
			if(hover && hasHoverImage) {
				g.drawImage(hoverImage, (int)x, (int)y, null);
			}
			else {
				if(offsetEnabled) {
					g.drawImage(defaultImage, (int)x + this.xOffset, (int)y + this.yOffset, null);
					if(!containsMouse()) {
						toggleOffset(false);
					}
				}
				else {
					g.drawImage(defaultImage, (int)x, (int)y, null);
				}
			}
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

	public int getX() {
		return (int)x;
	}

	public int getY() {
		return (int)y;
	}

	public int getWidth() {
		return (int)w;
	}

	public int getHeight() {
		return (int)h;
	}
}
