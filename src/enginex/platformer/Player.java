package enginex.platformer;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import enginex.core.GameObject;

@SuppressWarnings("serial")
public class Player extends GameObject {
	Platformer						game;
	double								x;
	double								y;
	int										w					= 50;
	int										h					= 50;
	Color									color			= Color.WHITE;
	boolean								moveLeft	= false;
	boolean								moveRight	= false;
	boolean								moveUp		= false;
	boolean								moveDown	= false;
	
	int										speed			= 5;
	
	float									velocityX, velocityY;
	float									gravity		= 0.5f;
	
	ArrayList<Collidable>	clist			= new ArrayList<>();
	
	public Player(Platformer game, int x, int y) {
		super(game);
		this.game = game;
		this.x = x;
		this.y = y;
	}
	
	public void update() {
		clist = getState().clist;
		gravity();
		move();
		
		x += velocityX;
		y += velocityY;
	}
	
	void gravity() {
		for(Collidable c:clist) {
			Rectangle a = new Rectangle((int)this.x, (int)(this.y + (velocityY + gravity)), (int)this.w, (int)this.h);
			Rectangle b = new Rectangle((int)c.x, (int)c.y, (int)c.w, (int)c.h);
			if(!Phys.collision(a, b))
				velocityY += gravity;
			else
				velocityY = 0;
		}
	}
	
	void jump() {
		// Fixed Jump Height
		velocityY = -12.0f;   // Give a vertical boost to the players velocity to start jump
		
		// Variable Jump Height
//		if(velocityY < -6.0f) // If character is still ascending in the jump
//			velocityY = -6.0f; // Limit the speed of ascent
	}
	
	void move() {
		if(moveUp) {
			for(Collidable c:clist) {
				Rectangle a = new Rectangle((int)this.x, (int)(this.y - speed), (int)this.w, (int)this.h);
				Rectangle b = new Rectangle((int)c.x, (int)c.y, (int)c.w, (int)c.h);
				if(!Phys.collision(a, b))
					this.y -= speed;
				else
					this.y = (b.y + b.height);
			}
		}
		if(moveDown) {
			for(Collidable c:clist) {
				Rectangle a = new Rectangle((int)this.x, (int)(this.y + speed), (int)this.w, (int)this.h);
				Rectangle b = new Rectangle((int)c.x, (int)c.y, (int)c.w, (int)c.h);
				if(!Phys.collision(a, b))
					this.y += speed;
				else
					this.y = b.y - this.h;
			}
		}
		if(moveLeft) {
			for(Collidable c:clist) {
				Rectangle a = new Rectangle((int)(this.x - speed), (int)this.y, (int)this.w, (int)this.h);
				Rectangle b = new Rectangle((int)c.x, (int)c.y, (int)c.w, (int)c.h);
				if(!Phys.collision(a, b))
					this.x -= speed;
				else
					this.x = b.x + b.width;
			}
		}
		if(moveRight) {
			for(Collidable c:clist) {
				Rectangle a = new Rectangle((int)(this.x + speed), (int)this.y, (int)this.w, (int)this.h);
				Rectangle b = new Rectangle((int)c.x, (int)c.y, (int)c.w, (int)c.h);
				if(!Phys.collision(a, b))
					this.x += speed;
				else
					this.x = b.x - this.w;
			}
		}
	}
	
	public void render(Graphics2D g) {
		g.setColor(color);
		g.fillRect((int)x, (int)y, (int)w, (int)h);
	}
	
	public PlayState getState() {
		return (PlayState)game.stateMachine.getCurrentState();
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
		
		// Reset
		if(e.getKeyCode() == KeyEvent.VK_R) {
			x = 150;
			y = 200;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_SPACE)
			jump();
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
	}
}
