package Complete.bubable;

import EngineX.Animation;
import EngineX.Sprite;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Player {
	private Game game;

	// Images for each animation
	private BufferedImage[] idleImages = {
			Sprite.getSprite(0, 0, 250, 250, "src/Complete.bubable/resources/idle.png")
	};

	private BufferedImage[] walkingRightImages = {
			Sprite.getSprite(0, 0, 250, 250, "src/Complete.bubable/resources/000.png"),
			Sprite.getSprite(0, 0, 250, 250, "src/Complete.bubable/resources/001.png"),
			Sprite.getSprite(0, 0, 250, 250, "src/Complete.bubable/resources/002.png"),
			Sprite.getSprite(0, 0, 250, 250, "src/Complete.bubable/resources/003.png"),
			Sprite.getSprite(0, 0, 250, 250, "src/Complete.bubable/resources/004.png"),
			Sprite.getSprite(0, 0, 250, 250, "src/Complete.bubable/resources/003.png"),
			Sprite.getSprite(0, 0, 250, 250, "src/Complete.bubable/resources/002.png"),
			Sprite.getSprite(0, 0, 250, 250, "src/Complete.bubable/resources/001.png")
	};

	private BufferedImage[] walkingLeftImages = {
			Sprite.getSprite(0, 0, 250, 250, "src/Complete.bubable/resources/left000.png"),
			Sprite.getSprite(0, 0, 250, 250, "src/Complete.bubable/resources/left001.png"),
			Sprite.getSprite(0, 0, 250, 250, "src/Complete.bubable/resources/left002.png"),
			Sprite.getSprite(0, 0, 250, 250, "src/Complete.bubable/resources/left003.png"),
			Sprite.getSprite(0, 0, 250, 250, "src/Complete.bubable/resources/left004.png"),
			Sprite.getSprite(0, 0, 250, 250, "src/Complete.bubable/resources/left003.png"),
			Sprite.getSprite(0, 0, 250, 250, "src/Complete.bubable/resources/left002.png"),
			Sprite.getSprite(0, 0, 250, 250, "src/Complete.bubable/resources/left001.png")
	};

	// Animation States
	private Animation idle = new Animation(idleImages, 1);
	private Animation walkLeft = new Animation(walkingLeftImages, 6);
	private Animation walkRight = new Animation(walkingRightImages, 6);

	// Current Animation
	private String currentAnimation = "idle";
	private Animation animation = idle;

	// Movement Booleans
	private boolean left = false;
	private boolean right = false;

	// Player Properties
	private float x = 250;
	private float y = 150;
	private int speed = 6;

	Player(Game game) {
		this.game = game;
		this.x = (float)((game.width/2)-(250/2));
		this.y = (float)((game.height/2)-(250/2));
	}

	public void update() {
		move();
		updateAnimation();
		updateBounds();
	}

	private void move() {
		if(left && right)
			return;

		if(left) {
			x-=speed;
		}

		if(right) {
			x+=speed;
		}
	}

	private void updateAnimation() {
		if((!left && !right) || (left && right)) {
			if(!currentAnimation.equals("idle")) {
				animation = idle;
				currentAnimation = "idle";
			}
		}

		if(left && !right) {
			if(!currentAnimation.equals("walkLeft")) {
				animation = walkLeft;
				currentAnimation = "walkLeft";
			}
		}

		if(right && !left) {
			if(!currentAnimation.equals("walkRight")) {
				animation = walkRight;
				currentAnimation = "walkRight";
			}
		}

		animation.start();
		animation.update();
	}

	private void updateBounds() {
		//...
	}

	public void render(Graphics2D g) {
		g.drawImage(animation.getSprite(), (int)x, (int)y, null);
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_A) left = true;
		if(e.getKeyCode() == KeyEvent.VK_D) right = true;
	}

	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_A) left = false;
		if(e.getKeyCode() == KeyEvent.VK_D) right = false;
	}
}
