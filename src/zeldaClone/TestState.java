package zeldaClone;

import com.badlogic.gdx.math.Vector2;
import enginex.State;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class TestState extends State {
	Game game;
	boolean initialized = false;

	FixedPointInverseSegmentCollection segmentCollection;
	ArrayList<FixedPointInverseSegmentCollection> segmentCollections = new ArrayList<>();

	TestState(Game game) {
		super(game);
		this.game = game;
	}

	public void initialize() {
		if(initialized) return;

//		for(int i = 0; i < 5; i++)
//			segmentCollections.add(new FixedPointInverseSegmentCollection(game, i*10, i*2, new Vector2(400, 600), new Vector2(i*20, i*20)));

		segmentCollection = new FixedPointInverseSegmentCollection(game, 10, 30, new Vector2(400, 600), new Vector2(400, 300));

		// State Initialized
		initialized = true;
	}

	public void update() {
		initialize();
//		for(FixedPointInverseSegmentCollection c: segmentCollections)
//			c.update();

		segmentCollection.update();
	}

	public void render(Graphics2D g) {
//		for(FixedPointInverseSegmentCollection c: segmentCollections)
//			c.render(g);

		segmentCollection.render(g);
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			game.exit();
	}
}