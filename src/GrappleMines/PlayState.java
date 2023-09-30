package GrappleMines;

import EngineX.State;
import EngineX.Util;
import com.studiohartman.jamepad.ControllerManager;
import com.studiohartman.jamepad.ControllerState;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class PlayState extends State {
	Game              game;
	ControllerManager controllerManager = new ControllerManager();
	boolean           initialized       = false;

	public int worldX;
	public int worldY;
	Point   mousePoint;
	boolean renderMousePoint = false;
	int     highestHeight    = 0;
	int     currentHeight    = 0;

	Camera                camera;
	Player                player;
	ArrayList<Collidable> blocks = new ArrayList<>();
	ArrayList<Mine>       mines  = new ArrayList<>();

	GameOverDisplay gameOverDisplay;
	MenuDisplay menuDisplay;

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

		// # Initialization... #

		// Mouse Point
		mousePoint = (game.getMousePosition() != null) ? game.getMousePosition() : new Point(0, 0);

		// World Coords
		worldX = game.width / 2;
		worldY = (game.height / 4) * 3;

		// Player
		player = new Player(this.game, -10, -30, 20, 20);

		// Blocks
		blocks.add(new Collidable(this.game, -138, 0, game.width, 93));

		// Mines
		addInitialMines();

		// CameraBox
		camera = new Camera(game, -(game.width / 2), -250, game.width - 17, 250);

		// GameOverDisplay
		menuDisplay = new MenuDisplay(game);
		gameOverDisplay = new GameOverDisplay(game);

		// Complete initialization
		initialized = true;
	}

	void reset() {
		// Mouse Point
		mousePoint = (game.getMousePosition() != null) ? game.getMousePosition() : new Point(0, 0);

		// World Coords
		worldX = game.width / 2;
		worldY = (game.height / 4) * 3;

		// Player
		player = new Player(this.game, -10, -30, 20, 20);

		// Blocks
		blocks = new ArrayList<>();
		blocks.add(new Collidable(this.game, -138, 0, game.width, 93));

		// Mines
		mines = new ArrayList<>();
		addInitialMines();

		// CameraBox
		camera = new Camera(game, -(game.width / 2), -250, game.width - 17, 250);

		// GameOverDisplay
		menuDisplay.visible = false;
		gameOverDisplay.visible = false;
	}

	public void update() {
		// This is Run Only Once...
		postInit();

		// # Capture Mouse Position
		try {
			mousePoint = (game.getMousePosition() != null) ? game.getMousePosition() : new Point(0, 0);
			// ## Adjust mouse coordinates for the game's world position
			int adjustedMouseX = (int)(mousePoint.getX() - worldX);
			int adjustedMouseY = (int)(mousePoint.getY() - worldY);
			mousePoint = new Point(adjustedMouseX, adjustedMouseY);
		}
		catch(Exception e) {
			// don't even bother handling it...
		}

		// Update Blocks
		for(Collidable b : blocks)
			b.update();

		// Update Mines
		for(Mine m : mines)
			m.update();

		// Update Player
		player.update();

		// Camera Correction...
		camera.cameraCorrection();

		// Displays
		menuDisplay.update();
		gameOverDisplay.update();

		// Get Controllers Info
		if(Config.controllerEnabled)
			pollControllers();
	}

	public void addInitialMines() {
		int rand = 0;
		for(int i = 0; i < 5000; i++) {
			rand = Util.getRandomNumberInRange(-118, 82);
			mines.add(new Mine(this.game, rand, -140 - (i * 80), 20, 20));
		}
	}

	public void render(Graphics2D g) {
		try {
			// Blocks
			for(Collidable b : blocks)
				b.render(g);

			// Player
			player.render(g);

			// Mines
			try {
				for(Mine m : mines)
					m.render(g);
			}catch(Exception e) {}

			// Camera Correction
			if(camera.renderCameraBox) {
				g.setColor(Color.white);
				g.drawRect(camera.x, camera.y, camera.getWidth(), camera.getHeight());
			}

			// Render MouseCursor
			if(renderMousePoint) {
				g.setColor(Color.white);
				g.fillOval(mousePoint.x + worldX, mousePoint.y + worldY, 5, 5);
			}

			// Render Stats
			g.setColor(new Color(0, 0, 0, 0.7f));
			g.fillRect(0, 0, game.width, 29);
			g.setColor(Color.white);
			g.drawString("Highest: " + highestHeight + "m", 3, 12);
			g.drawString("Current: " + currentHeight + "m", 3, 24);

			// Displays
			menuDisplay.render(g);
			gameOverDisplay.render(g);
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
				controllerUpdate(controller);
			}
		}
	}

	void controllerUpdate(ControllerState controller) {
		// See HEC player for reference
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			if(gameOverDisplay.visible) {
				game.exit();
			}
		else if(menuDisplay.visible) {
			menuDisplay.visible = false;
		}
		else {
			menuDisplay.visible = true;
		}

		if(e.getKeyCode() == KeyEvent.VK_R)
			reset();

		player.keyPressed(e);
	}

	public void keyReleased(KeyEvent e) {
		player.keyReleased(e);
	}

	public void mousePressed(MouseEvent e) {
		if(menuDisplay.visible) {
			if(menuDisplay.newGameButton.containsMouse()) reset();
			if(menuDisplay.exitButton.containsMouse()) game.exit();

		}
		else if(gameOverDisplay.visible) {
			if(gameOverDisplay.restartButton.containsMouse()) reset();
			if(gameOverDisplay.exitButton.containsMouse()) game.exit();
		}
		else {
			if(!menuDisplay.visible && !gameOverDisplay.visible) {
				player.mousePressed(e);
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		player.mouseReleased(e);
	}
}
