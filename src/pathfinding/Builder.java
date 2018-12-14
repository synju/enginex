package pathfinding;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import enginex.EngineX;
import enginex.GameObject;

@SuppressWarnings("serial")
public class Builder extends GameObject {
	boolean	mouseDown		= false;
	int			currentKey	= 1;
	boolean rightClick 	= false;

	/**
	 * Constructor
	 * 
	 * @param game
	 */
	public Builder(EngineX game) {
		super(game);
	}

	/**
	 * Basic update Method of the Builder.. Only works when "mouseDown" is TRUE
	 */
	public void update() {
		try {
			if(mouseDown) {
				if(rightClick) {
					currentNodeHover().type = Node.OPEN;
				}
				else {
				// START NODE TYPE
					if(currentKey == 1) {
						for(Node n:getState().nodes) {
							if(n.type == Node.START) {
								n.type = Node.OPEN;
							}
						}
						currentNodeHover().type = Node.START;
						getPathFinder().visible = true;
					}
	
					// CLOSED NODE TYPE... Basically a Wall...
					if(currentKey == 2) {
						currentNodeHover().type = Node.CLOSED;
					}
	
					// END NODE TYPE... The Destination Node....
					if(currentKey == 3) {
						for(Node n:getState().nodes) {
							if(n.type == Node.END) {
								n.type = Node.OPEN;
							}
						}
						currentNodeHover().type = Node.END;
					}
	
					// OPEN NODE TYPE... Used as an Eraser in the Builder.....
					if(currentKey == 4) {
						currentNodeHover().type = Node.OPEN;
					}
				}
			}
		}
		catch(Exception e) {
			// Do Nothing...
		}
	}

	/**
	 * Gets the PathFinder from the game... Remember: the pathfinder is the Entity
	 * that does the pathFinding...
	 * 
	 * @return
	 */
	public PathFinder getPathFinder() {
		return getState().pathfinder;
	}

	/**
	 * Gets the CURRENT NODE being HOVERED OVER
	 * 
	 * @return
	 */
	public Node currentNodeHover() {
		Point m = game.getMousePosition();
		for(Node n:getState().nodes)
			if(m.x > n.x * n.w && m.x < n.x * n.w + n.w && m.y > n.y * n.h && m.y < n.y * n.h + n.h)
				return n;

		return null;
	}

	/**
	 * Gets the PathFinderState of the game, may as well be any Main State....
	 * 
	 * @return
	 */
	public PathfinderState getState() {
		return (PathfinderState)game.stateMachine.getCurrentState();
	}

	/**
	 * Changes the mouseDown Boolean to true if Mouse Button is Pressed Down.....
	 */
	public void mousePressed(MouseEvent e) {
		mouseDown = true;
		
		if(e.getButton() == MouseEvent.BUTTON3) {
			rightClick = true;
		}
	}

	/**
	 * Changes the mouseDown Boolean to false if Mouse Button is Released.....
	 */
	public void mouseReleased(MouseEvent e) {
		mouseDown = false;
		
		if(e.getButton() == MouseEvent.BUTTON3)
			rightClick = false;
	}

	/**
	 * Listens for the keyPress Events in order to determine what Node to place..
	 */
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

		/**
		 * Changes Visibility of Beatle...
		 */
		if(e.getKeyCode() == KeyEvent.VK_C) {
			for(Node n:getState().nodes) {
				n.type = Node.OPEN;
			}
			getPathFinder().visible = false;
		}
	}
}
