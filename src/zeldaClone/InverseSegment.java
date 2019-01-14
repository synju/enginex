package zeldaClone;

import com.badlogic.gdx.math.Vector2;

import java.awt.*;

public class InverseSegment {
	Game game;
	Vector2 a;
	Vector2 b = new Vector2();
	float len;
	float angle;

	InverseSegment(float x, float y, float len, float angle, Game game) {
		this.game = game;
		a = new Vector2(x, y);
		this.len = len;
		this.angle = angle;
		calculateB();
	}

	private void calculateB() {
		Point p = game.getMousePosition();
		angle = (float) Math.toRadians(getAngle(new Vector2(a.x, a.y), new Vector2(p.x, p.y)));
		int dx = (int) (len * Math.cos(angle));
		int dy = (int) (len * Math.sin(angle));
		b.set(a.x + dx, a.y + dy);
	}

	public void follow(Vector2 v, Vector2 target, float distance, float speed) {
			float angle = (float)Math.toRadians(getAngle(v,target));
			updateLocation(v,angle,speed);
	}

	private void updateLocation(Vector2 v, float angle, float speed) {
		v.x += speed * Math.cos(angle);
		v.y += speed * Math.sin(angle);
	}

	private float getDistance(Vector2 a, Vector2 b) {
		return (float)(Math.sqrt((b.y - a.y) * (b.y - a.y) + (b.x - a.x) * (b.x - a.x)));
	}

	private float getAngle(Vector2 a, Vector2 b) {
		return (float) Math.toDegrees(Math.atan2(b.y - a.y, b.x - a.x));
	}

	public void update() {
		calculateB();
		Vector2 m = new Vector2(game.getMousePosition().x,game.getMousePosition().y);
		float speed = 5f;
		if(getDistance(b,m) > 3) {
			follow(b, m, len, speed);
			follow(a, b, len, speed);
		}
	}

	public void render(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.drawLine((int) a.x, (int) a.y, (int) b.x, (int) b.y);
	}
}
