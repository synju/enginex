package spinner;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import enginex.Button;
import enginex.State;

public class PlayState extends State {
	Game game;
	boolean initialized = false;
	Wheel wheel;
	Button spinButton;
	
	long start;
	long elapsedTime;
	int seconds;

	public PlayState(Game game) {
		super(game);
		this.game = game;
	}

	public void postInit() {
		// Return if already Initialized
		if (initialized)
			return;

		// Initialize stuff..
		wheel = new Wheel(game);
		spinButton = new Button(game,game.width-151-20,game.height-65-20,151,65,"res/spinner/spinButton.png","res/spinner/spinButton.png","res/spinner/click.ogg");

		// Initialize switch...
		initialized = true;
		
		// Start Time
		start = System.nanoTime();
	}

	public void update() {
		try {
			// Initialize stuff..
			postInit();

			// Update stuff..
			wheel.update();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	void captureTime() {
		elapsedTime = System.nanoTime() - start;
		seconds = (int)((double)elapsedTime / 1000000000.0);
	}

	public void render(Graphics2D g) {
		try {
			// Render stuff..
			wheel.render(g);
			spinButton.render(g);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			game.exit();
	}
	
	public void mousePressed(MouseEvent e) {
		Point m = game.getMousePosition();
		
		if(spinButton.contains(m)) {
//			spinButton.clickSound();
//			wheel.spin();
			captureTime();
			System.out.println(elapsedTime + " nanoseconds");
			System.out.println(seconds + " seconds");
		}
	}
}
