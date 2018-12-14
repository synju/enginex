package multistate;

import java.awt.event.KeyEvent;

import enginex.State;

public class MSGameState extends State {
	MultiState game;

	protected MSGameState(MultiState game) {
		super(game);
		this.game = game;
	}

	public void update() {
		System.out.println("Game");
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE)
			game.setState(MultiState.MENU);
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			game.exit();
	}
}
