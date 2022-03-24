package megamoney;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import enginex.EngineX;
import enginex.State;

public class PlayState extends State {
	Game		game;
	boolean	initialized	= false;
	Player p;

	public PlayState(Game game) {
		super(game);
		this.game = game;
	}

	public void postInit() {
		// Check if initialized
		if(initialized) return;
		
		// Initialize Stuff Here...
		p = new Player(game);

		// Complete initialization
		initialized = true;
	}

	public void update() {
		// This is Run Only Once...
		postInit();

		// Update Player...
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
