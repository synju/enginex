package zeldaClone;

import enginex.State;

import java.awt.*;
import java.awt.event.KeyEvent;


public class PlayState extends State {
	Game game;

	boolean initialized = false;

	Player player;

	public PlayState(Game game) {
		super(game);
		this.game = game;
	}

	public void initialize() {
		if(initialized) return;

		// Create Things...
		player = new Player(game);

		// State Initialized
		initialized = true;
	}

	public void update() {
		// Initialize State... ( Only Runs Once! )
		initialize();

		// Update Things..
		player.update();
	}

	public void render(Graphics2D g) {
		g.drawImage(game.resources.testRoomBG, 0,0,null);
		player.render(g);
	}

	public void keyPressed(KeyEvent e) {
		player.keyPressed(e);
	}

	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			exitState();

		player.keyReleased(e);
	}

	public void exitState() {
		// Reset
		initialized = false;

		// Go Back Menu
		game.stateMachine.setState(Game.MENU);
	}
}
