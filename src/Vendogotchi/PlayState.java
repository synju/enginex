package Vendogotchi;

import EngineX.State;
import SlotMachine.SlotMachine;
import com.studiohartman.jamepad.ControllerManager;
import com.studiohartman.jamepad.ControllerState;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class PlayState extends State {
	Game              game;
	boolean           initialized            = false;
	ControllerManager controllerManager      = new ControllerManager();
	int               splashscreen_countdown = 100;

	SlotMachine slotMachine;
	String[]    rows = {};

	public PlayState(Game game) {
		super(game);
		this.game = game;

		// Controller Manager
		if(Config.controllerEnabled)
			controllerManager.initSDLGamepad();
	}

	public void postInit() {
		// Check if initialized
		if(initialized)
			return;

		// Initialize Things...
		slotMachine = new SlotMachine(game);
		slotMachine.initialize();

		// Complete initialization
		initialized = true;
		System.out.println("Done Initializing...");
	}

	public void update() {
		// This is Run Only Once...
		postInit();

		// Get Controllers Info
		if(Config.controllerEnabled)
			pollControllers();

		// Update Game
		try {
			if(initialized) {
				// Update Machine...
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			game.exit();
		}
	}

	public void render(Graphics2D g) {
		// Smooth Rendering
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

		// Render Game
		try {
			if(initialized) {
				// Render Machine
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			game.exit();
		}
	}

	public void pollControllers() {
		int numControllers = controllerManager.getNumControllers();

		for(int i = 0; i < numControllers; i++) {
			ControllerState controller = controllerManager.getState(i);
			if(controller.isConnected && i == 0) {

			}
		}
	}

	public void keyPressed(KeyEvent e) {
		// Exit Game
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) game.exit();

		// Machine keyPressed...
	}

	public void keyReleased(KeyEvent e) {
		// Machine keyReleased...
	}

	public void mousePressed(MouseEvent e) {
		// Machine mousePressed...
	}

	public void mouseReleased(MouseEvent e) {
		// Machine mouseReleased...
	}
}
