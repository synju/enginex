package enginex.replicant;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;

import enginex.core.GameObject;
import enginex.core.SoundEngine;

@SuppressWarnings("serial")
public class Button extends GameObject {
	Replicants	game;
	boolean			hover				= false;
	Image				defaultImage;
	Image				hoverImage;
	String			soundPath;
	Point				m;
	boolean			soundPlayed	= false;
	
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
		
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		
		defaultImage = new ImageIcon(defaultImagePath).getImage();
		hoverImage = new ImageIcon(hoverImagePath).getImage();
		this.soundPath = soundPath;
	}
	
	public void update() {
		hover = false;
		m = game.getMousePosition();
		
		if((m != null) && (m.x > this.x && m.x < this.x + this.w) && (m.y > this.y && m.y < this.y + this.h)) {
			hover = true;
			if(soundPlayed == false) {
				SoundEngine.play(soundPath);
				soundPlayed = true;
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
