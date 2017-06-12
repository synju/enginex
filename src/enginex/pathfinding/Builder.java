package enginex.pathfinding;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import enginex.core.EngineX;
import enginex.core.GameObject;

@SuppressWarnings("serial")
public class Builder extends GameObject {
	boolean	mouseDown	= false;
	int		currentKey	= 1;

	public Builder(EngineX game) {
		super(game);
	}

	public void update() {
		try {
			if(mouseDown) {
				if(currentKey == 1) {
					for(Node n:getState().nodes) {
						if(n.type == Node.START) {
							n.type = Node.OPEN;
						}
					}
					currentNodeHover().type = Node.START;
					getBeatle().startXY = currentNodeHover().getCenter();
					getBeatle().visible = true;
				}
				if(currentKey == 2) {
					currentNodeHover().type = Node.CLOSED;
				}
				if(currentKey == 3) {
					for(Node n:getState().nodes) {
						if(n.type == Node.END) {
							n.type = Node.OPEN;
						}
					}
					currentNodeHover().type = Node.END;
					getState().beatle.endXY = currentNodeHover().getCenter();
				}
				if(currentKey == 4) {
					currentNodeHover().type = Node.OPEN;
				}
			}
		}
		catch(Exception e) {}
	}

	Beatle getBeatle() {
		return getState().beatle;
	}

	public Node currentNodeHover() {
		Point m = game.getMousePosition();
		for(Node n:getState().nodes)
			if(m.x > n.x * n.w && m.x < n.x * n.w + n.w && m.y > n.y * n.h && m.y < n.y * n.h + n.h)
				return n;

		return null;
	}

	public PathfinderState getState() {
		return (PathfinderState)game.stateMachine.getCurrentState();
	}

	public void mousePressed(MouseEvent e) {
		mouseDown = true;
	}

	public void mouseReleased(MouseEvent e) {
		mouseDown = false;
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_1) {
			currentKey = 1;
		}
		if(e.getKeyCode() == KeyEvent.VK_2) {
			currentKey = 2;
		}
		if(e.getKeyCode() == KeyEvent.VK_3) {
			currentKey = 3;
		}
		if(e.getKeyCode() == KeyEvent.VK_4) {
			currentKey = 4;
		}

		if(e.getKeyCode() == KeyEvent.VK_5) {
			currentKey = 5;
		}

		if(e.getKeyCode() == KeyEvent.VK_6) {
			currentKey = 6;
		}

		if(e.getKeyCode() == KeyEvent.VK_C) {
			for(Node n:getState().nodes) {
				n.type = Node.OPEN;
			}
			getBeatle().visible = false;
		}
	}
}
