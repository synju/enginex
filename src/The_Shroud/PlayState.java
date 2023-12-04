package The_Shroud;

import EngineX.State;
import com.studiohartman.jamepad.ControllerManager;
import com.studiohartman.jamepad.ControllerState;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class PlayState extends State {
	Game game;
	ControllerManager controllerManager = new ControllerManager();
	boolean initialized = false;

	public int worldX;
	public int worldY;
	public Point mousePoint;

	// Custom
	public Player player;
	ArrayList<Collidable> collidables = new ArrayList<>();

	public PlayState(Game game) {
		super(game);
		this.game = game;

		if (Config.controllerEnabled) {
			controllerManager.initSDLGamepad();
		}
	}

	public void postInit() {
		// Check if initialized
		if (initialized) return;

		// World Coords
		worldX = game.width / 2;
		worldY = game.height / 2;

		// Player
		player = new Player(game, worldX-game.width/2,worldY-game.height/2,5,5);

		// Collidables
		collidables.add(new Collidable(game,50,50,50,50));

		// Complete initialization
		initialized = true;
	}

	public void reset() {
		// Mouse Point
		mousePoint = (game.getMousePosition() != null) ? game.getMousePosition() : new Point(0, 0);

		// World Coords
		worldX = game.width / 2;
		worldY = game.height / 2;
	}

	public void update() {
		// Run Only Once...
		postInit();

		// Capture Mouse Position
		captureMousePosition();

		// Player
		player.update();

		// Get Controllers Info
		if (Config.controllerEnabled)
			pollControllers();
	}

	public void render(Graphics2D g) {
		if(game.renderEnabled == false) return;
		player.render(g);

		for(Collidable c:collidables) {
			c.render(g);
		}
	}

	public void captureMousePosition() {
		try {
			// Capture Mouse Position
			mousePoint = (game.getMousePosition() != null) ? game.getMousePosition() : new Point(0, 0);

			// Adjust mouse coordinates for the game's world position
			int adjustedMouseX = (int) (mousePoint.getX() - worldX);
			int adjustedMouseY = (int) (mousePoint.getY() - worldY);
			mousePoint = new Point(adjustedMouseX, adjustedMouseY);
		} catch (Exception e) {
			// Don't even bother handling it...
		}
	}

	public void pollControllers() {
		int numControllers = controllerManager.getNumControllers();

		for (int i = 0; i < numControllers; i++) {
			ControllerState controller = controllerManager.getState(i);
			if (controller.isConnected && i == 0) {
				controllerUpdate(controller);
			}
		}
	}

	public void controllerUpdate(ControllerState controller) {
		player.controllerUpdate(controller);
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			game.exit();
		}

		player.keyPressed(e);
	}

	public void keyReleased(KeyEvent e) {
		player.keyReleased(e);
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}
}
