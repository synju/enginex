package enginex.spriteAnimation;

import java.awt.Graphics2D;

import enginex.core.EngineX;
import enginex.core.State;

public class GameState extends State {
	Player p;

	protected GameState(EngineX game) {
		super(game);
		p = new Player(game);
		addGameObject(p);
	}

	protected void update() {
		p.update();
	}
	
	protected void render(Graphics2D g) {
		p.render(g);
	}
}