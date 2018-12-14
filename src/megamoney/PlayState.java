package megamoney;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import enginex.EngineX;
import enginex.State;

public class PlayState extends State {
	Game		game;
	boolean	initialized	= false;
	Player p;

	protected PlayState(EngineX game) {
		super(game);
	}

	public PlayState(Game game) {
		super(game);
		this.game = game;
	}

	public void postInit() {
		if(initialized)
			return;
		else
			initialized = true;
		
		// Initialize Stuff here...
		p = new Player(game);
	}

	public void update() {
		// This is Run Only Once...
		postInit();
		
		p.update();
	}

	public void render(Graphics2D g) {
		p.render(g);
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			game.exit();
		
		p.keyPressed(e);
	}
	
	public void keyReleased(KeyEvent e) {
		p.keyReleased(e);
	}
}
