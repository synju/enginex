package enginex;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;

@SuppressWarnings("serial")
public class GameObject extends Component implements KeyListener, MouseListener, MouseWheelListener {
	protected EngineX	game;
	private boolean		disposable						= false;
	boolean						renderEnabled					= true;
	public boolean		updateEnabled					= true;
	boolean						keyboardInputEnabled	= true;
	boolean						mouseInputEnabled			= true;
	public double			x											= 0;
	public double			y											= 0;
	public float			w											= 0;
	public float			h											= 0;

	public Rectangle	bounds								= new Rectangle();

	public GameObject(EngineX game) {
		this.game = game;
	}

	public void init() {

	}

	protected State getCurrentState() {
		return game.stateMachine.getCurrentState();
	}

	public void setDisposable(boolean bool) {
		disposable = bool;
	}

	public boolean isDisposable() {
		return disposable;
	}

	protected void updateBounds() {
	}

	protected boolean isColliding(Rectangle bounds) {
		if(this.bounds.intersects(bounds)) {
			return true;
		}
		return false;
	}
	
	public boolean contains(Point mousePosition) {
		Point m = mousePosition;
		if(m.x > this.x && m.x < this.x + this.w && m.y > this.y && m.y < this.y + this.h)
			return true;
		return false;
	}

	public void update() {
	}

	public void render(Graphics2D g) {
	}

	public void render(Graphics2D g, AffineTransform at) {
	}

	public void keyPressed(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void mouseWheelMoved(MouseWheelEvent e) {
	}
}
