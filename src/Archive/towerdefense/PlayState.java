package Archive.towerdefense;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import EngineX.State;

public class PlayState extends State {
	Game    game;
	boolean initialized = false;

	protected PlayState(Game game) {
		super(game);
		this.game = game;
	}

	public void postInit() {
		if(initialized)
			return;

		initialized = true;
	}

	public void update() {
		postInit();
	}

	public void render(Graphics2D g) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			game.exit();
	}
}
