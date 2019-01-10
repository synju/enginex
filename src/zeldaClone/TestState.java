package zeldaClone;

import enginex.State;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;

public class TestState extends State {
	Game game;

	boolean initialized = false;

	ArrayList<Point> points;

	public TestState(Game game) {
		super(game);
		this.game = game;
	}

	public void initialize() {
		if(initialized) return;

		game.hideDefaultCursor();

		int centerX = game.width / 2;
		int centerY = game.height / 2;
		int defaultDistance = 30;

		points = new ArrayList<>();
		points.add(new Point(centerX, centerY));
		Point basePoint = points.get(0);
		for(int i = 0; i < 5; i++) {
			points.add(new Point(centerX, basePoint.y + (i * defaultDistance)));
		}

		// State Initialized
		initialized = true;
	}

	public void update() {
		initialize();
		updatePoints();
	}

	private void updatePoints() {
		try {
			for(int i = points.size() - 1; i > 0; i--) {
				// Establish Points
				Point a, b, c;
				a = points.get(i - 1);
				b = points.get(i);
				if(i < points.size() - 1)
					c = points.get(i + 1);
				else
					c = game.getMousePosition();

				// Get ac angle
				float acAngle = getAngle(a, c, true);
//				int acDistance = (int)getDistance(a,c);

				float x = (float)(30 * Math.cos(acAngle));
				float y = (float)(30 * Math.sin(acAngle));
				Point target = new Point((int)x, (int)y);

				// Move x
				updateLocation(b,acAngle,0.1f);
				if(getDistance(b,target) > 5)
					b.setLocation(target);
			}
		}
		catch(Exception e) {
			// Do nothing...
		}
	}

	private float getAngle(Point a, Point b, boolean keepPositive) {
		float angle = (float) Math.toDegrees(Math.atan2(b.y - a.y, b.x - a.x));

		if(keepPositive) {
			if(angle < 0)
				angle += 360;
		}

		return angle;
	}

	private double getDistance(Point a, Point b) {
		return Math.sqrt((b.y - a.y) * (b.y - a.y) + (b.x - a.x) * (b.x - a.x));
	}

	private void updateLocation(Point p, float angle, float speed) {
		p.x += speed * Math.cos(angle);
		p.y += speed * Math.sin(angle);
	}

	public void render(Graphics2D g) {
		try {
			g.setColor(Color.WHITE);
			g.fillOval(game.getMousePosition().x - 5, game.getMousePosition().y - 5, 10, 10);

			for(Point p : points) {
				g.setColor(Color.RED);
				g.fillOval(p.x, p.y, 20, 20);
			}
		}
		catch(Exception e) {
			// Do nothing...
		}
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			game.exit();
	}
}