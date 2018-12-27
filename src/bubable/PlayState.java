package bubable;

import enginex.State;

import java.awt.*;
import java.awt.event.KeyEvent;

public class PlayState extends State {
	Player p;

	protected PlayState(Game game) {
		super(game);
		p = new Player(game);
	}

	public void update() {
		p.update();
	}

	public void render(Graphics2D g) {
		p.render(g);
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) game.exit();
		p.keyPressed(e);
	}

	public void keyReleased(KeyEvent e) {
		p.keyReleased(e);
	}
}
