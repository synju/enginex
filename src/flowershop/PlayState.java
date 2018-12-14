package flowershop;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import enginex.State;

public class PlayState extends State {
	Game game;

	protected PlayState(Game game) {
		super(game);
		this.game = game;
	}

	public void initialize() {
		if(initialized)
			return;

		create();

		initialized = true;
	}

	public void create() {
//		System.out.println(game.profileManager.getCurrentProfile().name);
	}

	public void update() {
		initialize();
	}

	public void render(Graphics2D g) {

	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			game.stateMachine.setState(game.MENU);
		}
	}
}
