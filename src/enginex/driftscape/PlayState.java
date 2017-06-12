package enginex.driftscape;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.ImageIcon;

import enginex.core.EngineX;
import enginex.core.State;

public class PlayState extends State {
	World	w;
	Image	grassImage		= new ImageIcon("res/driftscape/grass.png").getImage();
	Image	rectWhite			= new ImageIcon("res/driftscape/rectWhite.png").getImage();
	Image	rectGray			= new ImageIcon("res/driftscape/rectGray.png").getImage();
	Point	mousePosition	= game.getMousePosition();
	
	protected PlayState(EngineX game) {
		super(game);
	}
	
	public Point getMousePosition() {
		return game.getMousePosition();
	}
	
	public void init() {
		w = new World(game);
	}
	
	protected void update() {
		mousePosition = game.getMousePosition();
		w.update(mousePosition);
	}
	
	protected void render(Graphics2D g) {
		w.render(g);
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		super.mouseWheelMoved(e);
		w.mouseWheelMoved(e);
	}
	
	public void mousePressed(MouseEvent e) {
		w.mousePressed(e);
	}
	
	public void mouseReleased(MouseEvent e) {
		w.mouseReleased(e);
	}
	
	public void keyPressed(KeyEvent e) {
		w.keyPressed(e);
	}
	
	public void keyReleased(KeyEvent e) {
		w.keyReleased(e);
	}
}
