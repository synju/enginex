package miniworld;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import enginex.Animation;
import enginex.GameObject;
import enginex.Sprite;

@SuppressWarnings("serial")
public class Player extends GameObject {
	MiniWorld							game;
	
	double								x					= 0;
	double								y					= 0;
	int										w					= 32;
	int										h					= 32;
	
	boolean								left			= false;
	boolean								right			= false;
	boolean								up				= false;
	boolean								down			= false;
	
	String								direction	= "right";
	
	Color									color			= Color.red;
	
	float									speed			= 3f;
	float									velocityX	= 0;
	float									velocityY	= 0;
	
	BufferedImage[]				runUpImages;
	BufferedImage[]				runDownImages;
	BufferedImage[]				runRightImages;
	BufferedImage[]				runLeftImages;
	
	Animation							runUp;
	Animation							runDown;
	Animation							runLeft;
	Animation							runRight;
	Animation							idleUp;
	Animation							idleDown;
	Animation							idleLeft;
	Animation							idleRight;
	
	Animation							currentAnimation;
	
	ArrayList<Collidable>	clist			= new ArrayList<>();
	
	public Player(MiniWorld game, int x, int y) {
		super(game);
		this.game = game;
		this.x = x;
		this.y = y;
		
		setupAnimations();
		clist = getState().clist;
	}
	
	public void update() {
		updateAnimation();
		move();
		updateVX();
		updateVY();
	}
	
	public void render(Graphics2D g) {
		AffineTransform at = new AffineTransform();
		at.scale(game.scale, game.scale);
		at.translate((x / game.scale), (y / game.scale));
		g.drawImage(currentAnimation.getSprite(), at, null);
	}
	
	public void move() {
		if(up)
			velocityY = -speed;
		if(down)
			velocityY = speed;
		if(left)
			velocityX = -speed;
		if(right)
			velocityX = speed;
	}
	
	void setupAnimations() {
		String characterImage = "res/miniworld/character.png";
		
		runDownImages = new BufferedImage[] {
			Sprite.getSprite(0, 0, 32, 32, characterImage),
			Sprite.getSprite(1, 0, 32, 32, characterImage),
			Sprite.getSprite(2, 0, 32, 32, characterImage)
		};
		
		runLeftImages = new BufferedImage[] {
			Sprite.getSprite(0, 1, 32, 32, characterImage),
			Sprite.getSprite(1, 1, 32, 32, characterImage),
			Sprite.getSprite(2, 1, 32, 32, characterImage)
		};
		
		runRightImages = new BufferedImage[] {
			Sprite.getSprite(0, 2, 32, 32, characterImage),
			Sprite.getSprite(1, 2, 32, 32, characterImage),
			Sprite.getSprite(2, 2, 32, 32, characterImage)
		};
		
		runUpImages = new BufferedImage[] {
			Sprite.getSprite(0, 3, 32, 32, characterImage),
			Sprite.getSprite(1, 3, 32, 32, characterImage),
			Sprite.getSprite(2, 3, 32, 32, characterImage)
		};
		
		runDown = new Animation(runDownImages, 3);
		runUp = new Animation(runUpImages, 3);
		runLeft = new Animation(runLeftImages, 3);
		runRight = new Animation(runRightImages, 3);
		
		idleDown = new Animation(Sprite.getSprite(1, 0, 32, 32, characterImage));
		idleLeft = new Animation(Sprite.getSprite(1, 0, 32, 32, characterImage));
		idleRight = new Animation(Sprite.getSprite(1, 0, 32, 32, characterImage));
		idleUp = new Animation(Sprite.getSprite(1, 0, 32, 32, characterImage));
		
		setAnimation(idleDown);
	}
	
	void setAnimation(Animation a) {
		if(currentAnimation != a)
			currentAnimation = a;
	}
	
	public void updateAnimation() {
		if(up)
			setAnimation(runUp);
		if(down)
			setAnimation(runDown);
		if(left)
			setAnimation(runLeft);
		if(right)
			setAnimation(runRight);
		
		if(!right && !left && !up && !down) {
			if(direction == "up")
				setAnimation(idleUp);
			if(direction == "down")
				setAnimation(idleDown);
			if(direction == "left")
				setAnimation(idleLeft);
			if(direction == "right")
				setAnimation(idleRight);
		}
		
		currentAnimation.start();
		currentAnimation.update();
	}
	
	public PlayState getState() {
		return (PlayState)game.stateMachine.getCurrentState();
	}
	
	void updateVX() {
		boolean collision = false;
		for(Collidable c:clist) {
			Rectangle a = new Rectangle((int)(this.x + velocityX), (int)(this.y), (int)this.w, (int)this.h);
			Rectangle b = new Rectangle((int)c.x, (int)c.y, (int)c.w, (int)c.h);
			if(Phys.collision(a, b)) {
				collision = true;
				break;
			}
		}
		if(!collision) {
//			this.x += velocityX;
			for(Tile tile:getState().tiles)
				tile.x -= velocityX;
		}
	}
	
	void updateVY() {
		boolean collision = false;
		for(Collidable c:clist) {
			Rectangle a = new Rectangle((int)(this.x), (int)(this.y + velocityY), (int)this.w, (int)this.h);
			Rectangle b = new Rectangle((int)c.x, (int)c.y, (int)c.w, (int)c.h);
			if(Phys.collision(a, b)) {
				collision = true;
				break;
			}
		}
		if(!collision) {
//			this.y += velocityY;
			for(Tile tile:getState().tiles)
				tile.y -= velocityY;
		}
	}
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_W)
			up = true;
		if(e.getKeyCode() == KeyEvent.VK_S)
			down = true;
		if(e.getKeyCode() == KeyEvent.VK_A)
			left = true;
		if(e.getKeyCode() == KeyEvent.VK_D)
			right = true;
	}
	
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_W) {
			up = false;
			direction = "up";
			velocityY = 0;
			setAnimation(idleUp);
		}
		if(e.getKeyCode() == KeyEvent.VK_S) {
			down = false;
			direction = "down";
			velocityY = 0;
			setAnimation(idleDown);
		}
		if(e.getKeyCode() == KeyEvent.VK_A) {
			left = false;
			direction = "left";
			velocityX = 0;
			setAnimation(idleLeft);
		}
		if(e.getKeyCode() == KeyEvent.VK_D) {
			right = false;
			direction = "right";
			velocityX = 0;
			setAnimation(idleRight);
		}
		
	}
}
