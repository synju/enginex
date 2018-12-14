package spriteAnimation;

import java.awt.Graphics2D;

import enginex.EngineX;
import enginex.State;

public class GameState extends State {
	Player p;

	protected GameState(EngineX game) {
		super(game);
		p = new Player(game);
		addGameObject(p);
	}

	public void update() {
		p.update();
	}
	
	public void render(Graphics2D g) {
		p.render(g);
	}
}
