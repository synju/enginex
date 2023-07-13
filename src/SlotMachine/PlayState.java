package SlotMachine;

import EngineX.State;
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
		// Splash Screen
		if(Config.splashScreenEnabled && splashscreen_countdown > 0) {
			splashscreen_countdown--;
			System.out.println(splashscreen_countdown);
			return;
		}

		// This is Run Only Once...
		postInit();

		// Get Controllers Info
		if(Config.controllerEnabled)
			pollControllers();

		// Update Game
		try {
			if(initialized) {
				slotMachine.update();
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

		// For Simulation Only.
		if(Config.simulationEnabled)
			return;

		// Splashscreen
		if(Config.splashScreenEnabled && splashscreen_countdown > 0) {
			g.drawImage(game.res.splashscreen.getImage(), 0, 0, null);
		}

		// Render Game
		try {
			if(initialized) {
				slotMachine.render(g);
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

		slotMachine.keyPressed(e);
	}

	public void keyReleased(KeyEvent e) {
		slotMachine.keyReleased(e);
	}

	public void mousePressed(MouseEvent e) {
		slotMachine.mousePressed(e);
	}

	public void mouseReleased(MouseEvent e) {
		slotMachine.mouseReleased(e);
	}
}
