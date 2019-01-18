package zeldaClone;

import com.badlogic.gdx.math.Vector2;
import enginex.State;

import java.awt.*;
import java.awt.event.KeyEvent;

public class TestState extends State {
	Game game;
	boolean initialized = false;

	FixedPointInverseSegmentCollection segmentCollection;

	TestState(Game game) {
		super(game);
		this.game = game;
	}

	public void initialize() {
		if(initialized) return;

		segmentCollection = new FixedPointInverseSegmentCollection(game, 10, 30, new Vector2(400, 600), new Vector2(400, 300));

		// State Initialized
		initialized = true;
	}

	public void update() {
		initialize();

		segmentCollection.update();
	}

	public void render(Graphics2D g) {
		segmentCollection.render(g);
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			game.exit();
	}
}