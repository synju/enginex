package zeldaClone;

import com.badlogic.gdx.math.Vector2;
import processing.core.PApplet;

import java.awt.*;
import java.util.Random;

public class Segment {
	Vector2 a;
	Vector2 b;
	float len;
	float angle;
	float selfAngle;

	Segment parent = null;
	Segment child = null;

	Segment(float x, float y, float len, float angle) {
		this.a = new Vector2(x,y);
		this.len = len;
		this.angle = angle;
		calculateB();
		parent = null;
	}

	Segment(Segment parent, float len, float angle) {
		this.parent = parent;
		this.a = new Vector2(parent.b.x,parent.b.y);
		this.len = len;
		this.angle = angle;
		selfAngle = angle;
		calculateB();
	}

	void calculateB() {
		int dx = (int)(len * Math.cos(angle));
		int dy = (int)(len * Math.sin(angle));
		this.b = new Vector2(a.x+dx,a.y+dy);
	}

	public void update() {
		angle = selfAngle;

		if(parent != null) {
			a.x = parent.b.x;
			a.y = parent.b.y;
			angle += parent.angle;
		}

		calculateB();
	}

	void wiggle() {
		selfAngle = selfAngle + 0.01f;
	}

	public void render(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.drawLine((int)a.x,(int)a.y,(int)b.x,(int)b.y);
	}
}
