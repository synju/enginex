package enginex.core;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Button extends GameObject {
	int	x;
	int	y;
	int	w;
	int	h;

	Image	upImage;
	Image	hoverImage;
	Image	downImage;
	Image	currentImage;

	String label = "";

	boolean down = false;

	Rectangle bounds;

	ArrayList<ActionListener> actionListeners = new ArrayList<>();

	public Button(EngineX game, Image upImage, Image hoverImage, Image downImage) {
		super(game);
		setImages(upImage, hoverImage, downImage);
	}

	private void setImages(Image upImage, Image hoverImage, Image downImage) {
		if(upImage != null && hoverImage != null && downImage != null) {
			this.upImage = upImage;
			this.hoverImage = hoverImage;
			this.downImage = downImage;
			currentImage = upImage;
		}
	}

	public void init(int x, int y) {
		setLocation(x, y);
		bounds = new Rectangle(x, y, getWidth(), getHeight());
	}

	public void update() {
		try {
			if(bounds.contains(game.getMousePosition())) {
				if(down)
					currentImage = downImage;
				else
					currentImage = hoverImage;
			}
			else {
				if(down)
					currentImage = downImage;
				else
					currentImage = upImage;
			}
		}
		catch(Exception e) {}
	}

	public void render(Graphics2D g) {
		g.drawImage(currentImage, x, y, null);
	}

	public void addActionListener(ActionListener actionListener) {
		actionListeners.add(actionListener);
	}

	public void action(ActionEvent e) {
		for(ActionListener al:actionListeners) {
			al.actionPerformed(e);
		}
	}

	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getWidth() {
		return currentImage.getWidth(null);
	}

	public int getHeight() {
		return currentImage.getHeight(null);
	}

	public void mousePressed(MouseEvent e) {
		super.mousePressed(e);

		if(bounds.contains(game.getMousePosition()))
			down = true;
	}

	public void mouseReleased(MouseEvent e) {
		super.mouseReleased(e);

		down = false;

		try {
			if(bounds.contains(game.getMousePosition()))
				action(new ActionEvent(e, 0, null));
		}
		catch(Exception ex) {}
	}
}
