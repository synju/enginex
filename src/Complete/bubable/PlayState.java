package Complete.bubable;

import EngineX.State;

import java.awt.*;
import java.awt.event.KeyEvent;

public class PlayState extends State {
	Player p;
	Resources resources;
	Map map;

	protected PlayState(Game game) {
		super(game);
		resources = new Resources();
		map = new Map(game);
		p = new Player(game);
	}

	public void update() {
//		p.update();
	}

	public void render(Graphics2D g) {
		map.render(g);
//		p.render(g);
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) game.exit();
		p.keyPressed(e);
	}

	public void keyReleased(KeyEvent e) {
		p.keyReleased(e);
	}
}
