package Archive.zeldaClone;

import org.lwjgl.util.vector.Vector2f;

import java.awt.*;

public class Bullet {
	Game game;

	public static final int UP = 0;
	public static final int DOWN = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;

	private int direction;
	private Vector2f location;
	private float speed;

	public boolean outOfBounds = false;

	public Bullet(Game game, int direction, Vector2f location, float speed) {
		this.game = game;
		this.direction = direction;
		this.location = location;
		this.speed = speed;
	}

	public Vector2f getLocation() {
		return location;
	}

	public void update() {
		if(direction == UP)
			location.y -= speed;
		if(direction == DOWN)
			location.y += speed;
		if(direction == LEFT)
			location.x -= speed;
		if(direction == RIGHT)
			location.x += speed;
	}

	public void render(Graphics2D g) {
		// Render Bullet
		g.drawImage(game.resources.standardBullet, (int) location.x, (int) location.y, null);
	}
}
