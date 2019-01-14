package zeldaClone;

import enginex.State;

import java.awt.*;
import java.awt.event.KeyEvent;

public class TestState extends State {
	Game game;
	boolean initialized = false;
	InverseSegment seg;

	public TestState(Game game) {
		super(game);
		this.game = game;
	}

	public void initialize() {
		if(initialized) return;

		seg = new InverseSegment(400,300,30,(float)Math.toRadians(0), game);

		// State Initialized
		initialized = true;
	}

	public void update() {
		initialize();
		seg.update();
	}

	public void render(Graphics2D g) {
		seg.render(g);
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			game.exit();
	}
}