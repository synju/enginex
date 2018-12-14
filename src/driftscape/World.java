package driftscape;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;

import enginex.EngineX;
import enginex.GameObject;

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
	boolean					leftDown			= false;
	boolean					rightDown			= false;
	double					x							= 0;
	double					y							= 0;
	Point						mousePosition	= game.getMousePosition();
	int							moveX					= 0;
	int							moveY					= 0;
	
	public World(EngineX game) {
		super(game);
		int id = 0;
		for(int y = 0; y < size; y++) {
			for(int x = 0; x < size; x++) {
				nodes.add(new Node(this.game, (int)(this.x + x * scale), (int)(this.y + y * scale),id));
				id++;
			}
		}
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
	
	public void mouseLeftClick() {
		if(leftDown)
			System.out.println("Left Pressed!");
		else
			System.out.println("Left Released!");
	}
	
	public void mouseRightClick() {
		if(rightDown)
			System.out.println("Right Pressed!");
		else
			System.out.println("Right Released!");
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
	
	public void zoomIn() {
		if(scale < max) {
			// scale += zoom;
//			o = game.getMousePosition();
//			this.x -= (int)(o.x / scale);
//			this.y -= (int)(o.y / scale);
			
			scale += zoom;
			this.x -= 32/scale;
			this.y -= 0;
		}
		else
			scale = max;
	}
	
	public void zoomOut() {
		if(scale > min) {
//			scale -= zoom;
			// o = game.getMousePosition();
			// this.x += (int)(o.x / scale);
			// this.y += (int)(o.y / scale);
			
			scale -= zoom;
			this.x += 32*scale;
			this.y += 0;
		}
		else
			scale = min;
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
	
	public PlayState getPlayState() {
		return (PlayState)game.stateMachine.getCurrentState();
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
	
	public void mousePressed(MouseEvent e) {
		o = game.getMousePosition();
		
		if(e.getButton() == MouseEvent.BUTTON1) {
			leftDown = true;
			mouseLeftClick();
		}
		
		if(e.getButton() == MouseEvent.BUTTON2)
			middleDown = true;
		
		if(e.getButton() == MouseEvent.BUTTON3) {
			rightDown = true;
			mouseRightClick();
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			leftDown = false;
			mouseLeftClick();
		}
		
		if(e.getButton() == MouseEvent.BUTTON2)
			middleDown = false;
		
		if(e.getButton() == MouseEvent.BUTTON3) {
			rightDown = false;
			mouseRightClick();
		}
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
		
		if(e.getKeyCode() == KeyEvent.VK_PERIOD)
			adjustNodeRotation("increase");
		
		if(e.getKeyCode() == KeyEvent.VK_COMMA)
			adjustNodeRotation("decrease");
		
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			game.exit();
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
