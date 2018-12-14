package gamepadtest;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import enginex.State;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

public class PlayState extends State{
	GamePadTest	game;
	boolean			initialized	= false;
	
	Player			player;
	Controller[] controllers;
	Component[] components;
	
	public Controller joystick;
	
	
	public PlayState(GamePadTest game) {
		super(game);
		this.game = game;
	}
	
	public void postInit() {
		if(initialized)
			return;
		
		initControllers();
		
		// Player...
		player = new Player(game, 100, (288 * (int)game.scale) - (32 * (int)game.scale) * 2);
		
		initialized = true;
	}
	
	void initControllers() {
		for(Controller c:ControllerEnvironment.getDefaultEnvironment().getControllers()) {
			if(c.getType() == Controller.Type.STICK) {
				joystick = c;
				System.out.println(joystick.getName());
			}
		}
		
		// WORKS!!!
//		controllers = ControllerEnvironment.getDefaultEnvironment().getControllers();
//		for(int i = 0; i < controllers.length; i++) {
//			String type = controllers[i].getType().toString(); 
//			if(type == "Stick") {
//				System.out.println("Controller " + controllers[i].getName() + " is of type " + controllers[i].getType().toString());
//				components = controllers[i].getComponents();
//				for(int j = 0; j < components.length; j++)
//					System.out.println(" Component #" + (j+1) + ": " + components[j].getName());
//			}
//		}
	}
	
	public void update() {
		postInit();
		
		joyStickPoll();
		
		player.update();
	}
	
	public void render(Graphics2D g) {
		player.render(g);
	}
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			game.exit();
		
		player.keyPressed(e);
	}
	
	public void keyReleased(KeyEvent e) {
		player.keyReleased(e);
	}
	
	public void joyStickPoll() {
		// Update Joystick Input Data
		joystick.poll();
		
		// Update Player
		player.joystickPoll(joystick.getComponents());
	}
}
