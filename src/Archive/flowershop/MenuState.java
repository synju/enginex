package Archive.flowershop;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import EngineX.Button;
import EngineX.State;

public class MenuState extends State {
	// Game
	Game	game;
	boolean	renderEnabled	= true;

	// Logo and Sky background
	Image	logoImage			= new ImageIcon("res/Archive.flowershop/logo.png").getImage();
	Image	skyBackgroundImage	= new ImageIcon("res/Archive.flowershop/sky.png").getImage();

	// Buttons
	ArrayList<Button>	buttons	= new ArrayList<>();
	Button				playButton;
	Button				profileButton;
	Button				quitButton;

	protected MenuState(Game game) {
		super(game);
		this.game = game;
	}

	public void initialize() {
		if(initialized)
			return;

		create();

		initialized = true;
	}

	public void create() {
		game.profileManager = new ProfileManager(game);

		createButtons();
	}

	public void createButtons() {
		playButton = new Button(game, game.width / 2 - 211 / 2, 250, 211, 93, "res/Archive.flowershop/play_button.png", "res/Archive.flowershop/play_button.png");
		buttons.add(playButton);

		profileButton = new Button(game, 10, 10, 152, 67, "res/Archive.flowershop/profile.png", "res/Archive.flowershop/profile.png");
		buttons.add(profileButton);

		quitButton = new Button(game, 10, game.height - 50, 90, 42, "res/Archive.flowershop/quit.png", "res/Archive.flowershop/quit.png");
		buttons.add(quitButton);
	}

	public void update() {
		initialize();

		game.profileManager.update();

		// Buttons
		for(Button b:buttons)
			b.update();
	}

	public void render(Graphics2D g) {
		if(renderEnabled) {
			// Smooth Images
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

			// Background
			g.drawImage(skyBackgroundImage, 0, 0, null);

			// Logo
			g.drawImage(logoImage, 80, 165, null);

			// Buttons
			for(Button b:buttons)
				b.render(g);

			// Profile Manager
			game.profileManager.render(g);
		}
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			if(game.profileManager.visible)
				game.profileManager.toggleOff();
			else
				game.exit();
		}
	}

	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			if(!game.profileManager.visible) {
				// Play Button Clicked...
				if(playButton.hover) {
					// Change to play state...
					game.stateMachine.setState(game.PLAY);
				}

				// Load Button Clicked...
				if(profileButton.hover) {
					// Show Profiles...
					game.profileManager.toggleOn();
				}

				// Quit Button Clicked...
				if(quitButton.hover) {
					// Quit Game
					game.exit();
				}
			}
			else {
				game.profileManager.mousePressed(e);
			}
		}
	}
}
