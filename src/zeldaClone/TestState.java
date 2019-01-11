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
		for(int i = 0; i < 5; i++)
			points.add(new Point(centerX, basePoint.y + ((i+1) * defaultDistance)));

		// State Initialized
		initialized = true;
	}

	public void update() {
		initialize();
		updatePoints();
	}

	private void updatePoints() {
		try {
			if(game.window.contains(game.getMousePosition())) {
//				oldMethod();
				newMethod();
			}
		}
		catch(Exception e) {
			// Do nothing...
		}
	}

	private void newMethod() {
		for(int i = 0; i < points.size(); i++) {
			// Establish points
			Point a = points.get(i);
			Point b = game.getMousePosition();

			// 1. Get Theta1
			float theta1 = getAngle(a,b,true);
			System.out.println(theta1);
			break;

			// 2. Get Hypotenuse1
//			float hypotenuse1 = getDistance(a,b);
//
//			// 3. Get Adjacent1 & Opposite1
//			float adjacent1 = Math.abs(a.x-b.x);
//			float opposite1 = Math.abs(a.y-b.y);

			// 4. Get Theta2
//			if()



		}
	}

	private void oldMethod() {
		for(int i = (points.size() - 1); i > 0; i--) {
			// Establish Points
			Point a, b, c;
			a = points.get(i - 1);
			b = points.get(i);
			if(i < (points.size() - 1))
				c = points.get(i + 1);
			else
				c = game.getMousePosition();

			// Get acAngle
			float acAngle = getAngle(a, c, true);

			// Move b
			updateLocation(b, acAngle, 2f);
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

	private float getDistance(Point a, Point b) {
		return (float)(Math.sqrt((b.y - a.y) * (b.y - a.y) + (b.x - a.x) * (b.x - a.x)));
	}

	private void updateLocation(Point p, float angle, float speed) {
		p.x += speed * Math.cos(angle);
		p.y += speed * Math.sin(angle);
	}

	public void render(Graphics2D g) {
		try {
			g.setColor(Color.WHITE);
			g.fillOval(game.getMousePosition().x - 5, game.getMousePosition().y - 5, 10, 10);
		}
		catch(Exception e) {}

		g.setColor(Color.BLUE);
		for(Point p : points)
			g.fillOval(p.x - 5, p.y - 5, 10, 10);
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			game.exit();
	}
}