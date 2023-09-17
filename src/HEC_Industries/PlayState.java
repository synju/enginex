package HEC_Industries;

import EngineX.State;
import com.studiohartman.jamepad.ControllerManager;
import com.studiohartman.jamepad.ControllerState;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class PlayState extends State {
	Game    game;
	boolean initialized = false;

	public LevelHandler      levelHandler;
	ControllerManager controllerManager = new ControllerManager();

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

		// Initialize Level Handler
		levelHandler = new LevelHandler(game);

		// Complete initialization
		initialized = true;
		System.out.println("Done Initializing PlayState...");
	}

	public void update() {
		// This is Run Only Once...
		postInit();

		// Get Controllers Info
		if(Config.controllerEnabled && levelHandler.initialized)
			pollControllers();

		// Level Updates
		levelHandler.update();
	}

	public void render(Graphics2D g) {
		try {
			levelHandler.render(g);
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
				levelHandler.controllerUpdate(controller);
			}
		}
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			game.exit();

		levelHandler.keyPressed(e);
	}

	public void keyReleased(KeyEvent e) {
		levelHandler.keyReleased(e);
	}

	public void mousePressed(MouseEvent e) {
		levelHandler.mousePressed(e);
	}

	public void mouseReleased(MouseEvent e) {
		levelHandler.mouseReleased(e);
	}
}
