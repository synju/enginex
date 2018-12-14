package mapMaker;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;

import enginex.GameObject;

@SuppressWarnings("serial")
public class ImageLibElement extends GameObject {
	MapMaker			game;
	ImageLibrary	imgLib;
	Image					image;
	boolean				hover	= false;
	int xpos = 0;
	boolean selected = false;
	int imageID = 0;

	public ImageLibElement(MapMaker game, ImageLibrary imgLib, Image image, int imageID) {
		super(game);
		this.game = game;
		this.imgLib = imgLib;
		this.image = image;
		this.imageID = imageID;
		this.w = getState().blockSize;
		this.h = getState().blockSize;
	}

	public void setLocation(int x, int y, int xpos) {
		this.x = x;
		this.y = y;
		this.xpos = xpos;
		this.x = imgLib.x + xpos*getState().blockSize;
	}

	public void update() {
		this.x = imgLib.x + xpos*getState().blockSize;
		try {
			hover = contains(game.mousePosition);
		}
		catch(Exception e) {
		}
	}

	public void render(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect((int)x,(int)y,(int)w,(int)h);
		
		AffineTransform at = new AffineTransform();
		at.translate((int)x, (int)y);
		g.drawImage(image, at, null);
	}
	
	public void mouseWheelMoved(MouseWheelEvent e) {
		boolean direction = e.getWheelRotation() < 0; // true = up.. false = down..
		if(direction) {
			this.y += getState().blockSize;
		}
		else {
			this.y -= getState().blockSize;
		}
	}

	public boolean contains(Point mousePosition) {
		Point m = mousePosition;

		if((m.x > this.x) && (m.x < this.x + this.w) && (m.y > this.y) && (m.y < this.y + this.h))
			return true;

		return false;
	}

	public PlayState getState() {
		return (PlayState)game.stateMachine.getCurrentState();
	}
}
