package enginex.core;

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
	EngineX				game;
	public Stack<State>	states			= new Stack<State>();
	int					currentIndex	= 0;

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
		if(states.size() > 0) {
			return states.get(currentIndex);
		}
		return null;
	}

	public void setState(int stateIndex) {
		if(states.get(stateIndex) != null)
			currentIndex = stateIndex;
	}

	void update() {
		if(getCurrentState() != null)
			getCurrentState().update();
	}

	void render(Graphics2D g) {
		if(getCurrentState() != null)
			getCurrentState().render(g);
	}
	
	void render(Graphics2D g, AffineTransform at) {
		if(at == null)
			if(getCurrentState() != null)
				getCurrentState().render(g,at);
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
