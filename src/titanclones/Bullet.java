package titanclones;

import java.awt.Color;
import java.awt.Graphics2D;

import enginex.GameObject;

@SuppressWarnings("serial")
public class Bullet extends GameObject {
	public double			x											= 0;
	public double			y											= 0;
	public static float			w											= 10;
	public static float			h											= 10;
	
	public float speed = 10f;
	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	int direction;
	
	float gravity = 0.5f;
	int maxGravity = 99;
	float yVelocity = 0;
	float initialYVelocity = 3.8f;
	
	public Bullet(TitanClones game, int x, int y, int direction) {
		super(game);
		this.x = x;
		this.y = y;
		this.direction = direction;
		yVelocity -= initialYVelocity;
	}
	
	public void update() {
		gravity();
		
		this.y += yVelocity;
		
		if(direction == LEFT)
			this.x -= speed;
		else
			this.x += speed;
	}
	
	public void gravity() {
		yVelocity += gravity;
		if(yVelocity > maxGravity)
			yVelocity = maxGravity;
	}
	
	public void render(Graphics2D g) {
		g.setColor(Color.white);
		g.fillRect((int)x, (int)y, (int)w, (int)h);
	}
}
