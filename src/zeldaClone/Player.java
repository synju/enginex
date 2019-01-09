package zeldaClone;

import org.lwjgl.util.vector.Vector2f;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;

public class Player {
	private Game game;
	private Vector2f location;

	private boolean moveUp = false;
	private boolean moveDown = false;
	private boolean moveLeft = false;
	private boolean moveRight = false;

	private boolean shootUp = false;
	private boolean shootDown = false;
	private boolean shootLeft = false;
	private boolean shootRight = false;

	private int maxBullets = 30;
	private int shootCoolDown = 0;
	private int shootCoolDownMax = 15;
	private float bulletSpeed = 5f;

	private static final int UP = 0;
	private static final int DOWN = 1;
	private static final int LEFT = 2;
	private static final int RIGHT = 3;

	private float speed = 4f;

	private ArrayList<Bullet> bullets = new ArrayList<>();

	public Player(Game game) {
		this.game = game;
		setLocation(new Vector2f((float) (game.width / 2 - 35), (float) (game.height / 2 - 65)));
	}

	public void setLocation(Vector2f location) {
		this.location = location;
	}

	public void update() {
		checkMoving();
		checkShooting();
	}

	private void checkMoving() {
		if(moveUp)
			location.y -= speed;
		if(moveDown)
			location.y += speed;
		if(moveLeft)
			location.x -= speed;
		if(moveRight)
			location.x += speed;
	}

	private void checkShooting() {
		if(shootCoolDown > 0)
			shootCoolDown--;

		if(shootUp)
			shoot(UP);
		else if(shootDown)
			shoot(DOWN);
		else if(shootLeft)
			shoot(LEFT);
		else if(shootRight)
			shoot(RIGHT);

		updateBullets();
	}

	private void updateBullets() {
		for(Iterator<Bullet> it = bullets.iterator(); it.hasNext(); ) {
			Bullet b = it.next();

			if(!b.outOfBounds)
				b.update(); // Update Bullet
			else
				it.remove(); // Remove Bullet

			// Exited Top
			if(b.getLocation().y < -64)
				b.outOfBounds = true;

			// Exited Down
			if(b.getLocation().y > game.height + 64)
				b.outOfBounds = true;

			// Exited Right
			if(b.getLocation().x > game.width + 64)
				b.outOfBounds = true;

			// Exited Right
			if(b.getLocation().x < -64)
				b.outOfBounds = true;
		}
	}

	private void shoot(int direction) {
		if(shootCoolDown <= 0 && bullets.size() < maxBullets) {
			if(direction == UP)
				bullets.add(new Bullet(game, Bullet.UP, new Vector2f(location.x + 20, location.y - 15), bulletSpeed));
			if(direction == DOWN)
				bullets.add(new Bullet(game, Bullet.DOWN, new Vector2f(location.x + 20, location.y + 55), bulletSpeed));
			if(direction == LEFT)
				bullets.add(new Bullet(game, Bullet.LEFT, new Vector2f(location.x - 20, location.y + 20), bulletSpeed));
			if(direction == RIGHT)
				bullets.add(new Bullet(game, Bullet.RIGHT, new Vector2f(location.x + 63, location.y + 20), bulletSpeed));

			shootCoolDown = shootCoolDownMax;
		}
	}

	public void render(Graphics2D g) {
		// Render Player
		g.drawImage(game.resources.playerIdle, (int) location.x, (int) location.y, null);

		// Render Bullets
		for(Bullet b : bullets)
			b.render(g);
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_W)
			moveUp = true;
		if(e.getKeyCode() == KeyEvent.VK_S)
			moveDown = true;
		if(e.getKeyCode() == KeyEvent.VK_A)
			moveLeft = true;
		if(e.getKeyCode() == KeyEvent.VK_D)
			moveRight = true;

		if(e.getKeyCode() == KeyEvent.VK_UP)
			shootUp = true;
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
			shootDown = true;
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
			shootLeft = true;
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			shootRight = true;
	}

	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_W)
			moveUp = false;
		if(e.getKeyCode() == KeyEvent.VK_S)
			moveDown = false;
		if(e.getKeyCode() == KeyEvent.VK_A)
			moveLeft = false;
		if(e.getKeyCode() == KeyEvent.VK_D)
			moveRight = false;

		if(e.getKeyCode() == KeyEvent.VK_UP)
			shootUp = false;
		if(e.getKeyCode() == KeyEvent.VK_DOWN)
			shootDown = false;
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
			shootLeft = false;
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			shootRight = false;
	}
}
