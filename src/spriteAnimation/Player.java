package spriteAnimation;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import enginex.EngineX;
import enginex.GameObject;

@SuppressWarnings("serial")
public class Player extends GameObject {
	// Images for each animation
	private BufferedImage[]	walkingUpImages			= { Sprite.getSprite(0, 3), Sprite.getSprite(2, 3) };
	private BufferedImage[]	walkingDownImages		= { Sprite.getSprite(0, 0), Sprite.getSprite(2, 0) };
	private BufferedImage[]	walkingLeftImages		= { Sprite.getSprite(0, 1), Sprite.getSprite(2, 1) };
	private BufferedImage[]	walkingRightImages	= { Sprite.getSprite(0, 2), Sprite.getSprite(2, 2) };

	private BufferedImage[]	idleDownImages			= { Sprite.getSprite(1, 0) };
	private BufferedImage[]	idleLeftImages			= { Sprite.getSprite(1, 1) };
	private BufferedImage[]	idleRightImages			= { Sprite.getSprite(1, 2) };
	private BufferedImage[]	idleUpImages				= { Sprite.getSprite(1, 3) };

	// These are animation states
	private Animation				walkUp							= new Animation(walkingUpImages, 7);
	private Animation				walkDown						= new Animation(walkingDownImages, 7);
	private Animation				walkLeft						= new Animation(walkingLeftImages, 7);
	private Animation				walkRight						= new Animation(walkingRightImages, 7);

	private Animation				idleDown						= new Animation(idleDownImages, 10);
	private Animation				idleLeft						= new Animation(idleLeftImages, 10);
	private Animation				idleRight						= new Animation(idleRightImages, 10);
	private Animation				idleUp							= new Animation(idleUpImages, 10);

	// This is the actual animation
	private Animation				animation						= idleDown;

	// Directions
	int											keyUp								= KeyEvent.VK_W;
	int											keyDown							= KeyEvent.VK_S;
	int											keyLeft							= KeyEvent.VK_A;
	int											keyRight						= KeyEvent.VK_D;

	boolean									up									= false;
	boolean									down								= false;
	boolean									left								= false;
	boolean									right								= false;

	int											speed								= 5;

	public Player(EngineX game) {
		super(game);
	}

	public void update() {
		move();
		updateAnimation();
		updateBounds();
	}

	void updateAnimation() {
		if(left && !right) {
			if(animation != walkLeft)
				animation = walkLeft;
		}
		if(right && !left) {
			if(animation != walkRight)
				animation = walkRight;
		}
		if(up && !down) {
			if(animation != walkUp)
				animation = walkUp;
		}
		if(down && !up) {
			if(animation != walkDown)
				animation = walkDown;
		}

		animation.start();
		animation.update();
	}
  
	protected void updateBounds() {
		bounds.setBounds((int)x, (int)y, animation.getSprite().getWidth(), animation.getSprite().getHeight());
	}

	void move() {
		int tSpeed = speed;

		if(left && down || left && up || right && down || right && up)
			tSpeed = 3;

		if(left) {
			if(x - tSpeed < 0)
				x = 0;
			else
				x -= tSpeed;
		}
		if(right) {
			if(x + animation.getSprite().getWidth() + tSpeed < game.getWidth())
				x += tSpeed;
		}
		if(up) {
			if(y - tSpeed < 0)
				y = 0;
			else
				y -= tSpeed;
		}
		if(down) {
			if(y + animation.getSprite().getHeight() + tSpeed > game.getHeight())
				y = game.getHeight() - animation.getSprite().getWidth();
			else
				y += tSpeed;
		}
	}

	public void render(Graphics2D g) {
		g.drawImage(animation.getSprite(), (int)x, (int)y, null);
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == keyUp)
			up = true;
		if(e.getKeyCode() == keyDown)
			down = true;
		if(e.getKeyCode() == keyLeft)
			left = true;
		if(e.getKeyCode() == keyRight)
			right = true;
	}

	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == keyUp) {
			up = false;

			if(animation == walkUp) {
				animation.stop();
				animation.reset();
				animation = idleUp;
			}
		}
		if(e.getKeyCode() == keyDown) {
			down = false;

			if(animation == walkDown) {
				animation.stop();
				animation.reset();
				animation = idleDown;
			}
		}
		if(e.getKeyCode() == keyLeft) {
			left = false;

			if(animation == walkLeft) {
				animation.stop();
				animation.reset();
				animation = idleLeft;
			}
		}
		if(e.getKeyCode() == keyRight) {
			right = false;

			if(animation == walkRight) {
				animation.stop();
				animation.reset();
				animation = idleRight;
			}
		}
	}
}
