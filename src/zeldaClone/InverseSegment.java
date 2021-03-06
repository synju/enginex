package zeldaClone;

import com.badlogic.gdx.math.Vector2;

import java.awt.*;

public class InverseSegment {
	Game game;
	Vector2 a;
	Vector2 b = new Vector2();
	float len;
	float angle;
	InverseSegment parent = null;

	InverseSegment(float x, float y, float len, float angle, Game game) {
		this.game = game;
		a = new Vector2(x, y);
		this.len = len;
		this.angle = angle;
		calculateB();
	}

	InverseSegment(InverseSegment parent, float len, float angle, Game game) {
		this.game = game;
		this.parent = parent;
		a = new Vector2(0, 0);
		this.len = len;
		this.angle = angle;
		calculateB();
	}

	private void calculateB() {
		if(parent == null) {
			try {
				Point p = game.getMousePosition();
				angle = (float) Math.toRadians(getAngle(new Vector2(a.x, a.y), new Vector2(p.x, p.y)));
				int dx = (int) (len * Math.cos(angle));
				int dy = (int) (len * Math.sin(angle));
				b.set(a.x + dx, a.y + dy);
			}
			catch(Exception e) {
			}
		}
		else {
			angle = (float) Math.toRadians(getAngle(new Vector2(a.x, a.y), new Vector2(parent.a.x, parent.a.y)));
			int dx = (int) (len * Math.cos(angle));
			int dy = (int) (len * Math.sin(angle));
			b.set(a.x + dx, a.y + dy);
		}
	}

	public void follow(float tx, float ty) {
		Vector2 target = new Vector2(tx, ty);
		Vector2 dir = vectorSubtract(target, a);
		dir = setVector2Mag(dir, len);
		dir = vector2Multiply(dir, -1);
		a = vectorAdd(target, dir);
	}

	private float getVector2Mag(Vector2 v) {
		return (float) Math.sqrt(v.x * v.x + v.y * v.y);
	}

	private Vector2 setVector2Mag(Vector2 v, float newMag) {
		float mag = getVector2Mag(v);
		float x = v.x * newMag / mag;
		float y = v.y * newMag / mag;
		return new Vector2(x, y);
	}

	private Vector2 vector2Multiply(Vector2 v, float value) {
		float x = v.x * value;
		float y = v.y * value;
		return new Vector2(x, y);
	}

	private void updateLocation(Vector2 v, float angle, float speed) {
		v.x += speed * Math.cos(angle);
		v.y += speed * Math.sin(angle);
	}

	private float getDistance(Vector2 a, Vector2 b) {
		return (float) (Math.sqrt((b.y - a.y) * (b.y - a.y) + (b.x - a.x) * (b.x - a.x)));
	}

	private float getAngle(Vector2 a, Vector2 b) {
		return (float) Math.toDegrees(Math.atan2(b.y - a.y, b.x - a.x));
	}

	private Vector2 vectorSubtract(Vector2 a, Vector2 b) {
		return new Vector2(a.x - b.x, a.y - b.y);
	}

	private Vector2 vectorAdd(Vector2 a, Vector2 b) {
		return new Vector2(a.x + b.x, a.y + b.y);
	}

	public void update() {
		try {
			calculateB();
			if(parent == null)
				follow(game.getMousePosition().x, game.getMousePosition().y);
			else
				follow(parent.a.x, parent.a.y);
		}
		catch(Exception e) {
		}
	}

	public void render(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.drawLine((int) a.x, (int) a.y, (int) b.x, (int) b.y);
	}
}
