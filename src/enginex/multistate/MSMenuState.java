package enginex.multistate;

import java.awt.event.KeyEvent;

import enginex.core.State;

public class MSMenuState extends State {
	MultiState game;

	protected MSMenuState(MultiState game) {
		super(game);
		this.game = game;
	}

	protected void update() {
		System.out.println("Menu");
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE)
			game.setState(MultiState.GAME);
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			game.exit();
	}
}
