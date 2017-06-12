package enginex.driftscape;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;

import enginex.core.EngineX;
import enginex.core.GameObject;

@SuppressWarnings("serial")
public class World extends GameObject {
	ArrayList<Node>	nodes					= new ArrayList<>();
	public double		scale					= 1;
	int							size					= 100;
	double					zoom					= 1f;
	double					max						= 18;
	double					min						= 1;
	boolean					up						= false;
	boolean					down					= false;
	boolean					left					= false;
	boolean					right					= false;
	double					moveSpeed			= 10;
	Point						o							= null;
	boolean					middleDown		= false;
	double					x							= 0;
	double					y							= 0;
	Point						mousePosition	= game.getMousePosition();
	int							moveX					= 0;
	int							moveY					= 0;
	
	public World(EngineX game) {
		super(game);
		for(int y = 0; y < size; y++)
			for(int x = 0; x < size; x++)
				nodes.add(new Node(this.game, (int)(this.x + x * scale), (int)(this.y + y * scale)));
	}
	
	public void update(Point m) {
		middleDrag(m);
		updateNodes(m);
		checkKeys();
	}
	
	private void checkKeys() {
		if(up)
			this.y += moveSpeed;
		if(down)
			this.y -= moveSpeed;
		if(left)
			this.x += moveSpeed;
		if(right)
			this.x -= moveSpeed;
	}
	
	public void updateNodes(Point m) {
		for(Node n:nodes)
			n.update(m);
	}
	
	public void middleDrag(Point m) {
		if(m != null && middleDown) {
			if(o.x != m.x || o.y != m.x) {
				// move right
				if(o.x < m.x)
					this.x += (int)((m.x - o.x) / scale);
				
				// move left
				if(o.x > m.x)
					this.x -= (int)((o.x - m.x) / scale);
				
				// move up
				if(o.y < m.y)
					this.y += (int)((m.y - o.y) / scale);
				
				// move down
				if(o.y > m.y)
					this.y -= (int)((o.y - m.y) / scale);
				
				o = m;
			}
		}
	}
	
	public void render(Graphics2D g) {
		for(Node n:nodes)
			n.render(g);
	}
	
	public void mouseWheelMoved(MouseWheelEvent e) {
		super.mouseWheelMoved(e);
		mousePosition = getPlayState().mousePosition;
		
		if(e.getWheelRotation() < 0)
			zoomIn();
		else
			zoomOut();
		
		for(Node n:nodes) {
			n.scale = scale;
		}
	}
	
	public void zoomIn() {
		System.out.println("mx:" + game.getMousePosition().getX());
		if(scale < max) {
			scale += zoom;
		}
		else
			scale = max;
		
		this.x -= (Node.w * scale);
		this.y -= (Node.h * scale);
	}
	
	public void zoomOut() {
		if(scale > min) {
			scale -= zoom;
		}
		else
			scale = min;
	}
	
	public PlayState getPlayState() {
		return (PlayState)game.stateMachine.getCurrentState();
	}
	
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON2) {
			o = game.getMousePosition();
			middleDown = true;
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON2)
			middleDown = false;
	}
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W)
			up = true;
		if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S)
			down = true;
		if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A)
			left = true;
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D)
			right = true;
		
		if(e.getKeyCode() == KeyEvent.VK_ADD)
			adjustNodeRotation("increase");
		
		if(e.getKeyCode() == KeyEvent.VK_MINUS)
			adjustNodeRotation("decrease");
	}
	
	private void adjustNodeRotation(String s) {
		if(s == "increase") {
			for(Node n:nodes)
				n.rotation++;
		}
		else if(s == "decrease") {
			for(Node n:nodes)
				n.rotation--;
		}
	}

	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W)
			up = false;
		if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S)
			down = false;
		if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A)
			left = false;
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D)
			right = false;
	}
}
