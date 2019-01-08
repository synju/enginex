package enginex;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.util.Stack;

public class StateMachine implements KeyListener, MouseListener, MouseWheelListener {
	EngineX game;
	public Stack<State> states = new Stack<State>();
	int currentIndex = 0;

	StateMachine(EngineX game) {
		this.game = game;
	}

	public void pushState(State state) {
		states.push(state);
	}

	public void popState() {
		states.pop();
	}

	public State getCurrentState() {
		if(states.size() > 0)
			return states.get(currentIndex);

		return null;
	}

	public State getState(int stateIndex) {
		return states.get(stateIndex);
	}

	public void setState(int stateIndex) {
		if(states.get(stateIndex) != null)
			currentIndex = stateIndex;
	}

	public void initAll() {
		for(State s:states)
			s.init();
	}

	void update() {
		if(getCurrentState() != null)
			try {
				getCurrentState().update();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
	}

	void render(Graphics2D g) {
		// Smooth Images
		game.smoothRendering(g);

		if(getCurrentState() != null) {
			try {
				getCurrentState().render(g);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	void render(Graphics2D g, AffineTransform at) {
		// Smooth Images
		game.smoothRendering(g);

		if(at == null) {
			if(getCurrentState() != null) {
				try {
					getCurrentState().render(g, at);
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void keyPressed(KeyEvent e) {
		if(getCurrentState() != null)
			getCurrentState().keyPressed(e);
	}

	public void keyReleased(KeyEvent e) {
		if(getCurrentState() != null)
			getCurrentState().keyReleased(e);
	}

	public void keyTyped(KeyEvent e) {
		if(getCurrentState() != null)
			getCurrentState().keyTyped(e);
	}

	public void mouseClicked(MouseEvent e) {
		if(getCurrentState() != null)
			getCurrentState().mouseClicked(e);
	}

	public void mouseEntered(MouseEvent e) {
		if(getCurrentState() != null)
			getCurrentState().mouseEntered(e);
	}

	public void mouseExited(MouseEvent e) {
		if(getCurrentState() != null)
			getCurrentState().mouseExited(e);
	}

	public void mousePressed(MouseEvent e) {
		if(getCurrentState() != null)
			getCurrentState().mousePressed(e);
	}

	public void mouseReleased(MouseEvent e) {
		if(getCurrentState() != null)
			getCurrentState().mouseReleased(e);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if(getCurrentState() != null)
			getCurrentState().mouseWheelMoved(e);
	}
}
