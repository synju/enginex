package zeldaClone;

import enginex.Button;
import enginex.State;
import org.lwjgl.BufferUtils;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Mouse;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.IntBuffer;


public class MenuState extends State {
	Game game;

	Button playButton;
	Button quitButton;

	boolean quiting = false;

	ScrollingBG bg;

	boolean initialized = false;

	static final int PLAY_POSITION = 125;
	static final int QUIT_POSITION = 350;
	int previousPostion = PLAY_POSITION;
	int currentPostion = PLAY_POSITION;

	Image currentMouse;

	public MenuState(Game game) {
		super(game);
		this.game = game;
	}

	public void initialize() {
		if(initialized) return;

		// ScrollingBG
		bg = new ScrollingBG(game.resources.bg, 0.25f, 0, 0, game.resources.bg.getWidth(null), game.resources.bg.getHeight(null));

		// Play Button
		playButton = new Button(game, 255, 197);
		playButton.setLocation(game.width / 2 - game.resources.playButton.getWidth(null) / 2, 75);
		playButton.setImage(game.resources.playButton);
		playButton.hasImages = true;
		playButton.hasHoverImage = false;

		// Quit Button
		quitButton = new Button(game, 255, 197);
		quitButton.setLocation(game.width / 2 - game.resources.quitButton.getWidth(null) / 2, 300);
		quitButton.setImage(game.resources.quitButton);
		quitButton.hasImages = true;
		quitButton.hasHoverImage = false;

		// Custom Mouse
		currentMouse = game.resources.normalMouse;
		game.hideDefaultCursor();

		// State Initialized
		initialized = true;
	}

	public void update() {
		// Initialize State... ( Only Runs Once! )
		initialize();

		// Update State
		bg.update();

		// Update Selector Position
		if(playButton.containsMouse()) {
			previousPostion = currentPostion;
			currentPostion = PLAY_POSITION;
		}
		if(quitButton.containsMouse()) {
			previousPostion = currentPostion;
			currentPostion = QUIT_POSITION;
		}
		checkPreviousPosition();
	}

	public void checkPreviousPosition() {
		if(previousPostion != currentPostion) {
			previousPostion = currentPostion;

			if(quiting) return;

			game.soundMachine.playSound(game.resources.buttonChange);
		}
	}

	public void render(Graphics2D g) {
		// Scrolling BG
		bg.render(g);

		if(!quiting) {
			// Buttons
			playButton.render(g);
			quitButton.render(g);

			// Selector Positioning
			if(currentPostion == PLAY_POSITION)
				g.drawImage(game.resources.selector, game.width / 2 - game.resources.selector.getWidth(null) / 2, PLAY_POSITION, null);
			if(currentPostion == QUIT_POSITION)
				g.drawImage(game.resources.selector, game.width / 2 - game.resources.selector.getWidth(null) / 2, QUIT_POSITION, null);
		}
		else {
			g.drawImage(game.resources.byebye, game.width / 2 - game.resources.byebye.getWidth(null) / 2, game.height / 2 - game.resources.byebye.getHeight(null) / 2, null);
		}

		renderMouse(g);
	}

	void renderMouse(Graphics2D g) {
		try {
			Point p = game.getMousePosition();
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
			g.drawImage(currentMouse, (int)p.getX() - 10, (int)p.getY() - 10, null);
		}
		catch(Exception e) {}
	}

	public void quitButtonClicked() {
		// Shutdown Sound
		game.soundMachine.playSound(game.resources.quitSound);

		quiting = true;

		// Delayed Quit
		new java.util.Timer().schedule(
				new java.util.TimerTask() {
					@Override
					public void run() {
						game.exit();
					}
				},
				1531
		);
	}

	public void mouseReleased(MouseEvent e) {
		// Check if already quitting
		if(quiting) return;

		// Left Click
		if(e.getButton() == MouseEvent.BUTTON1) {
			// Change Mouse Back
			currentMouse = game.resources.normalMouse;

			// Play Button Click!!
			if(playButton.containsMouse())
				game.stateMachine.setState(Game.PLAY);

			// Quit Button Click!!
			if(quitButton.containsMouse())
				quitButtonClicked();
		}
	}

	public void mousePressed(MouseEvent e) {
		// Check if already quitting
		if(quiting) return;

		// Check if left click pressed
		if(e.getButton() == MouseEvent.BUTTON1) {
			// Change Mouse
			currentMouse = game.resources.clickMouse;
		}
	}

	public void keyPressed(KeyEvent e) {
		// Exit Game!!!
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			game.exit();

		// Selection UP...
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			if(playButton.containsMouse() || quitButton.containsMouse()) return;

			if(currentPostion == PLAY_POSITION) {
				previousPostion = currentPostion;
				currentPostion = QUIT_POSITION;
			}
			else {
				previousPostion = currentPostion;
				currentPostion = PLAY_POSITION;
			}
		}

		// Selection Down...
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			if(playButton.containsMouse() || quitButton.containsMouse()) return;

			if(currentPostion == PLAY_POSITION) {
				previousPostion = currentPostion;
				currentPostion = QUIT_POSITION;
			}
			else {
				previousPostion = currentPostion;
				currentPostion = PLAY_POSITION;
			}
		}

		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			// Check if already quitting
			if(quiting) return;

			if(currentPostion == PLAY_POSITION)
				game.stateMachine.setState(Game.PLAY);
			else {
				quitButtonClicked();
			}
		}
	}
}