package mapMaker;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import enginex.GameObject;

@SuppressWarnings("serial")
public class ImageLibrary extends GameObject {
	MapMaker					game;
	boolean						hover		= false;
	Image[]						images;
	ImageLibElement[]	elements;
	int								xBlocks	= 0;

	public ImageLibrary(MapMaker game, Image[] images, int xBlocks, int x, int y, int w, int h) {
		super(game);
		this.game = game;
		this.images = images;
		this.xBlocks = xBlocks;
		this.x = game.getWidth() - (xBlocks * getState().blockSize) - 17 - 10;
		this.y = y;
		this.w = xBlocks * w;
		this.h = h;

		setupImages();
	}

	public void setupImages() {
		// Get Elements
		elements = new ImageLibElement[images.length];
		for(int i = 0; i < elements.length; i++) {
			elements[i] = new ImageLibElement(game, this, images[i], i);
		}

		// Arrange Elements
		int index = 0;
		int xc = 0;
		int yc = 0;
		while(index < elements.length) {
			elements[index].setLocation((int)xc, (int)y + yc * getState().blockSize, xc);
			if(xc < xBlocks - 1) {
				xc++;
			}
			else {
				xc = 0;
				yc++;
			}
			index++;
		}

		this.x = game.getWidth() - (xBlocks * getState().blockSize) - 17;
		this.y = getState().liby;
	}

	public void update() {
		h = game.getHeight();
		this.x = game.getWidth() - (xBlocks * getState().blockSize) - 17;

		try {
			hover = contains(game.mousePosition);
		}
		catch(Exception e) {
		}

		for(ImageLibElement element:elements) {
			element.update();
		}
	}

	public boolean hasMouse() {
		boolean hasMouse = false;

		try {
			hasMouse = contains(game.mousePosition);
		}
		catch(Exception e) {
		}

		return hasMouse;
	}

	public void render(Graphics2D g) {
		g.setColor(new Color(0.1f, 0.1f, 0.1f, 1.0f));
		g.fillRect((int)x, (int)y, (int)w, (int)h);

		for(ImageLibElement element:elements)
			element.render(g);

		for(ImageLibElement element:elements)
			if(element.hover) {
				g.setColor(Color.WHITE);
				g.drawRect((int)element.x, (int)element.y, (int)element.w, (int)element.h);
			}

		for(ImageLibElement element:elements)
			if(element.selected) {
				g.setColor(Color.WHITE);
				g.drawRect((int)element.x, (int)element.y, (int)element.w, (int)element.h);
			}

		g.setColor(new Color(1f, 1f, 1f, 0.5f));
		g.drawRect((int)x, (int)y, (int)w, (int)h);
	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		for(ImageLibElement element:elements)
			element.mouseWheelMoved(e);
	}

	public boolean contains(Point mousePosition) {
		Point m = mousePosition;

		if((m.x > this.x) && (m.x < this.x + xBlocks * this.w) && (m.y > this.y) && (m.y < this.y + this.h))
			return true;

		return false;
	}

	public PlayState getState() {
		return (PlayState)game.stateMachine.getCurrentState();
	}

	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			if(!getState().paintToolEnabled && hasMouse())
				getState().paintToolEnabled = true;
			
			for(ImageLibElement element:elements) {
				element.selected = false;
				if(element.contains(game.mousePosition)) {
					element.selected = true;
					getState().currentImage = element.imageID;
				}
			}
		}
	}
}
