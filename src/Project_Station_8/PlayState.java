package Project_Station_8;

import com.studiohartman.jamepad.ControllerManager;
import com.studiohartman.jamepad.ControllerState;
import EngineX.State;

import java.awt.*;
import java.awt.event.KeyEvent;

public class PlayState extends State {
	Player p;
	Resources resources;
	Map map;

	ControllerManager controllerManager = new ControllerManager();

	protected PlayState(Game game) {
		super(game);

		// Resources
		resources = new Resources(game);

		// Controller Manager
		controllerManager.initSDLGamepad();

		// Other...
		map = new Map(game);
		p = new Player(game);
	}

	public void update() {
		// Get Controllers Info
		pollControllers();

		// Update Other...
		p.update();
	}

	public void render(Graphics2D g) {
		map.render(g);
		p.render(g);
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			game.exit();
		p.keyPressed(e);
	}

	public void keyReleased(KeyEvent e) {
		p.keyReleased(e);
	}

	public void pollControllers() {
		int numControllers = controllerManager.getNumControllers();

		for(int i = 0; i < numControllers; i++) {
			ControllerState controller = controllerManager.getState(i);
			if(controller.isConnected && i == 0) {
				p.controllerUpdate(controller);
			}
		}
	}
}
