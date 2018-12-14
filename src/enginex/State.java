package enginex;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Iterator;

import processing.core.PApplet;

public class State extends PApplet implements KeyListener, MouseListener, MouseWheelListener {
	protected EngineX		game;
	ArrayList<GameObject>	gameObjects	= new ArrayList<>();
	String					stateName	= "";
	protected boolean initialized = false;

	protected State(EngineX game) {
		this.game = game;
		init();
	}

	public void init() {}

	protected void addGameObject(GameObject go) {
		gameObjects.add(go);
	}

	public void update() {
		for(Iterator<GameObject> it = gameObjects.iterator(); it.hasNext();) {
			GameObject go = it.next();
			if(go.isDisposable()) {
				// Dispose of Unwanted Game Objects
				it.remove();
			}
			else {
				if(go.updateEnabled) {
					// Update Game Object
					go.update();
				}
			}
		}
	}

	public void render(Graphics2D g) {
		for(GameObject go:gameObjects) {
			if(go.renderEnabled) {
				go.render(g);
			}
		}
	}
	
	protected void render(Graphics2D g, AffineTransform at) {
		for(GameObject go:gameObjects) {
			if(go.renderEnabled) {
				go.render(g, at);
			}
		}
	}

	public void keyPressed(KeyEvent e) {
		for(GameObject go:gameObjects) {
			if(go.keyboardInputEnabled) {
				go.keyPressed(e);
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		for(GameObject go:gameObjects) {
			if(go.keyboardInputEnabled) {
				go.keyReleased(e);
			}
		}
	}

	public void keyTyped(KeyEvent e) {
		for(GameObject go:gameObjects) {
			if(go.keyboardInputEnabled) {
				go.keyTyped(e);
			}
		}
	}

	public void mouseClicked(MouseEvent e) {
		for(GameObject go:gameObjects) {
			if(go.mouseInputEnabled) {
				go.mouseClicked(e);
			}
		}
	}

	public void mouseEntered(MouseEvent e) {
		for(GameObject go:gameObjects) {
			if(go.mouseInputEnabled) {
				go.mouseEntered(e);
			}
		}
	}

	public void mouseExited(MouseEvent e) {
		for(GameObject go:gameObjects) {
			if(go.mouseInputEnabled) {
				go.mouseExited(e);
			}
		}
	}

	public void mousePressed(MouseEvent e) {
		for(GameObject go:gameObjects) {
			if(go.mouseInputEnabled) {
				go.mousePressed(e);
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		for(GameObject go:gameObjects) {
			if(go.mouseInputEnabled) {
				go.mouseReleased(e);
			}
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		for(GameObject go:gameObjects) {
			if(go.mouseInputEnabled) {
				go.mouseWheelMoved(e);
			}
		}
	}
}
