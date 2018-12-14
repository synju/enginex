package gamepadtest;

import enginex.State;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

import java.awt.*;
import java.awt.event.KeyEvent;

public class PlayStateTest extends State{
	GamePadTest	game;
	boolean			initialized	= false;

	Controller[] controllers;
	Component[] components;
	public Controller joystick;

	public PlayStateTest(GamePadTest game) {
		super(game);
		this.game = game;
	}
	
	public void postInit() {
		if(initialized)
			return;
		
		initControllers();
		
		initialized = true;
	}
	
	void initControllers() {

	}
	
	public void update() {
		postInit();

	}
	
	public void render(Graphics2D g) {

	}
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			game.exit();
	}
	
	public void keyReleased(KeyEvent e) {

	}
}
