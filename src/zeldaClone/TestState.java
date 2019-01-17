package zeldaClone;

import com.badlogic.gdx.math.Vector2;
import enginex.State;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class TestState extends State {
	Game game;
	boolean initialized = false;

	//	ArrayList<InverseSegment> segments = new ArrayList<>();
	ArrayList<FixedPointInverseSegment> segments = new ArrayList<>();
	Vector2 base;

	public TestState(Game game) {
		super(game);
		this.game = game;
	}

	public void initialize() {
		if(initialized) return;

		int len = 10;
		int count = 30;

		// Fixed Point Inverse Segments
		base = new Vector2(400, 600);
		FixedPointInverseSegment initial = new FixedPointInverseSegment(400, 300, len, (float) Math.toRadians(0), game);
		for(int i = 0; i < count; i++) {
			if(i == 0)
				segments.add(initial);
			else
				segments.add(new FixedPointInverseSegment(segments.get(i - 1), len, (float) Math.toRadians(0), game));
		}
		segments.get(segments.size() - 1).setA(base);

		// Inverse Segments
//		InverseSegment initial = new InverseSegment(400, 300, 5, (float) Math.toRadians(0), game);
//		for(int i = 0; i < 200; i++) {
//			if(i == 0)
//				segments.add(initial);
//			else
//				segments.add(new InverseSegment(segments.get(i - 1), 5, (float) Math.toRadians(0), game));
//		}

		// State Initialized
		initialized = true;
	}

	public void update() {
		initialize();

		// Fixed Point Inverse Segments
		for(FixedPointInverseSegment s : segments)
			s.update();

//		for(int i = segments.size()-2; i >= 0; i--)
//			segments.get(i).setA(segments.get(i).child.b);

		for(FixedPointInverseSegment s : segments)
			s.updatePhase2();

		// Inverse Segments
//		for(InverseSegment s : segments)
//			s.update();

	}

	public void render(Graphics2D g) {
		// Fixed Point Inverse Segments
		for(FixedPointInverseSegment s : segments)
			s.render(g);

		// Inverse Segments
//		for(InverseSegment s : segments)
//			s.render(g);
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			game.exit();
	}
}