package critters;

import enginex.State;
import enginex.Util;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MenuState extends State {
	Game game;

	protected MenuState(Game game) {
		super(game);
		this.game = game;
	}

	public void init() {
		if(initialized)
			return;

		create();

		initialized = true;
	}

	public void create() {

	}

	public void update() {
		init();
	}

	public void render(Graphics2D g) {
		Util.drawText(8,34,"Menu",32,Color.WHITE,g);
	}
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE)
			game.stateMachine.setState(game.PLAY);

		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			game.exit();
	}
}
