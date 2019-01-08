package zeldaClone;

import enginex.State;
import zeldaClone.Game;

import java.awt.*;
import java.awt.event.KeyEvent;


public class PlayState extends State {
	Game game;

	public  PlayState(Game game) {
		super(game);
		this.game = game;
	}

	public void init() {

	}

	public void update() {

	}

	public void render(Graphics2D g) {

	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			game.stateMachine.setState(Game.MENU);
	}
}
