package Tamagotchi;

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
	int option = 0;

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
				game.tamagotchi.update();
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
				g.drawImage(game.res.bg001.getImage(), 0, 0, null);
				g.drawImage(game.res.gui002.getImage(), 0, 0, null);
				renderButtons(g);

				// Render Tamagotchi
				game.tamagotchi.render(g);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			game.exit();
		}
	}

	public void renderButtons(Graphics2D g) {
		// Stats
		if(option == 0) {
			g.drawImage(game.res.stats_on.getImage(), 0, 0, null);
		}
		else {
			g.drawImage(game.res.stats_off.getImage(), 0, 0, null);
		}

		// Food
		if(option == 1) {
			g.drawImage(game.res.food_on.getImage(), 0, 0, null);
		}
		else {
			g.drawImage(game.res.food_off.getImage(), 0, 0, null);
		}

		// Activity
		if(option == 2) {
			g.drawImage(game.res.activity_on.getImage(), 0, 0, null);
		}
		else {
			g.drawImage(game.res.activity_off.getImage(), 0, 0, null);
		}

		// Toilet
		if(option == 3) {
			g.drawImage(game.res.toilet_on.getImage(), 0, 0, null);
		}
		else {
			g.drawImage(game.res.toilet_off.getImage(), 0, 0, null);
		}

		// Medicine
		if(option == 4) {
			g.drawImage(game.res.medicine_on.getImage(), 0, 0, null);
		}
		else {
			g.drawImage(game.res.medicine_off.getImage(), 0, 0, null);
		}

		// Shop
		if(option == 5) {
			g.drawImage(game.res.shop_on.getImage(), 0, 0, null);
		}
		else {
			g.drawImage(game.res.shop_off.getImage(), 0, 0, null);
		}

		// Business
		if(option == 6) {
			g.drawImage(game.res.business_on.getImage(), 0, 0, null);
		}
		else {
			g.drawImage(game.res.business_off.getImage(), 0, 0, null);
		}

		// Settings
		if(option == 7) {
			g.drawImage(game.res.settings_on.getImage(), 0, 0, null);
		}
		else {
			g.drawImage(game.res.settings_off.getImage(), 0, 0, null);
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
		game.tamagotchi.keyReleased(e);

		if(e.getKeyCode() == KeyEvent.VK_A) {
			if(option > 0) {
				option--;
			}
			else {
				option = 7;
			}
		}

		if(e.getKeyCode() == KeyEvent.VK_Z) {
			if(option < 7) {
				option++;
			}
			else {
				option = 0;
			}
		}

		if(e.getKeyCode() == KeyEvent.VK_NUMPAD1) {
			System.out.println("select");
		}

		if(e.getKeyCode() == KeyEvent.VK_NUMPAD2) {
			System.out.println("cancel");
		}
	}

	public void mousePressed(MouseEvent e) {
		// Machine mousePressed...
	}

	public void mouseReleased(MouseEvent e) {
		// Machine mouseReleased...
	}
}
