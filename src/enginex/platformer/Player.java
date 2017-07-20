package enginex.platformer;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import enginex.core.FileManager;
import enginex.core.GameObject;
import enginex.core.Phys;

@SuppressWarnings("serial")
public class Player extends GameObject {
	Platformer						game;
	public double					x;
	public double					y;
	public int						w										= 50;
	public int						h										= 50;
	Color									color								= Color.RED;
	boolean								moveLeft						= false;
	boolean								moveRight						= false;
	boolean								moveUp							= false;
	boolean								moveDown						= false;
	boolean								upDownMovement			= false;
	
	boolean								renderJumpCollider	= false;
	int										speed								= 5;
	// float jumpSpeed = 8.61f;
	float									jumpSpeed						= 6.2f;
	
	float									velocityX, velocityY;
	
	float									gravity							= 0.35f;
	float									maxGravity					= 12f;
	boolean								gravityEnabled			= true;
	FileManager						fm									= new FileManager();
	
	ArrayList<Collidable>	clist								= new ArrayList<>();
	
	boolean								renderMC						= false;
	MovementContainer			mc									= null;
	boolean								mcCorrection				= false;
	boolean								correction					= true;
	
	public Player(Platformer game, int x, int y) {
		super(game);
		this.game = game;
		this.x = x;
		this.y = y;
		
		mc = new MovementContainer(game, 175, 150, 150, 100);
	}
	
	public void save() {
		List<String> saveList = Arrays.asList(fm.toString(x), fm.toString(y));
		fm.save("playerSaveFile", saveList);
	}
	
	public void load() {
		ArrayList<String> saveList = (ArrayList<String>)fm.load("playerSaveFile");
		if(!saveList.isEmpty()) {
			this.x = fm.toDouble(saveList.get(0));
			this.y = fm.toDouble(saveList.get(1));
			this.velocityX = 0;
			this.velocityY = 0;
		}
	}
	
	void reset() {
		x = 225;
		y = 175;
		velocityX = 0;
		velocityY = 0;
		clist = getState().clist;
	}
	
	public void update() {
		clist = getState().clist;
		gravity();
		move();
		
		updateVX();
		updateVY();
	}
	
	void setCList() {
		if(clist.isEmpty())
			clist = getState().clist;
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
			if(mcCorrection) {
				if((this.x + velocityX > mc.x) && (this.x + this.w + velocityX < mc.x + mc.w))
					this.x += velocityX;
				else {
					for(Collidable c:clist)
						c.x -= velocityX;
				}
			}
			else {
				this.x += velocityX;
				centerCamera();
			}
		}
	}
	
	void updateVY() {
		boolean collision = false;
		for(Collidable c:clist) {
			Rectangle a = new Rectangle((int)(this.x), (int)(this.y + velocityY), (int)this.w, (int)this.h);
			Rectangle b = new Rectangle((int)c.x, (int)c.y, (int)c.w, (int)c.h);
			if(Phys.collision(a, b)) {
				collision = true;
				
				// Potential Bottom Collision
				if(a.y < b.y) {
					Rectangle aTemp = new Rectangle((int)(this.x), (int)(this.y + 1), (int)this.w, (int)this.h);
					Rectangle bTemp = new Rectangle((int)c.x, (int)c.y, (int)c.w, (int)c.h);
					
					while(!Phys.collision(aTemp, bTemp)) {
						aTemp.y++;
						this.y++;
					}
				}
				
				break;
			}
		}
		
		// Old Way
		if(correction)
			for(Collidable c:clist) {
				Rectangle a = new Rectangle((int)this.x, (int)this.y, (int)this.w, (int)this.h);
				Rectangle b = new Rectangle((int)c.x, (int)c.y, (int)c.w, (int)c.h);
				if(Phys.collision(a, b))
					this.y--;
			}
		
		if(!collision) {
			if(mcCorrection) {
				int top = (int)(this.y + velocityY);
				int mcTop = (int)mc.y;
				int bottom = (int)(this.y + this.h + velocityY);
				int mcBottom = (int)(mc.y + mc.h);
				if(top > mcTop && bottom < mcBottom)
					this.y += velocityY;
				else
					for(Collidable c:clist)
						c.y -= velocityY;
			}
			else {
				this.y += velocityY;
				centerCamera();
			}
		}
		else
			velocityY = 0;
			
		// New Way
		// if(!collision) {
		// if(((this.y + velocityY) > getState().mc.y) && ((this.y + this.h +
		// velocityY) < getState().mc.y + getState().mc.h))
		// this.y += velocityY;
		// else {
		// for(Collidable c:clist)
		// c.y -= velocityY;
		// }
		// }
		// else
		// velocityY = 0;
	}
	
	void gravity() {
		if(gravityEnabled) {
			velocityY += gravity;
			
			if(velocityY > maxGravity)
				velocityY = maxGravity;
		}
	}
	
	boolean onGround() {
		for(Collidable c:clist) {
			Rectangle a = new Rectangle((int)this.x, (int)(this.y + this.h), (int)this.w, 1);
			Rectangle b = new Rectangle((int)c.x, (int)c.y, (int)c.w, (int)c.h);
			if(Phys.collision(a, b))
				return true;
		}
		
		return false;
	}
	
	void grabLedge() {
		
	}
	
	void jump() {
		if(onGround() && gravityEnabled)
			velocityY = -jumpSpeed;
	}
	
	void jumpRelease() {
		if(velocityY < -(jumpSpeed / 2))
			velocityY += jumpSpeed / 2;
	}
	
	void duck() {
		if(h > 25 && onGround()) {
			h = 25;
			y += h;
		}
	}
	
	void duckRelease() {
		if(h == 25) {
			h = 50;
			y -= 25;
		}
	}
	
	void move() {
		if(upDownMovement) {
			if(moveUp)
				if(velocityY > -speed)
					velocityY = -speed;
				
			if(moveDown)
				if(velocityY < speed)
					velocityY = speed;
		}
		
		if(moveUp && moveDown)
			velocityY = 0;
		
		if(moveLeft)
			if(velocityX > -speed)
				velocityX -= speed;
			
		if(moveRight)
			if(velocityX < speed)
				velocityX += speed;
			
		if(moveLeft && moveRight)
			velocityX = 0;
	}
	
	public void centerCamera() {
		Player p = this;
		ArrayList<Collidable> clist = getState().clist;
		int gw = game.width / 2 - p.w / 2;
		int gh = game.height / 2;
		
		while(p.x > gw) {
			p.x--;
			for(Collidable c:clist)
				c.x--;
		}
		while(p.x < gw) {
			p.x++;
			for(Collidable c:clist)
				c.x++;
		}
		
		while(p.y > gh) {
			p.y--;
			for(Collidable c:clist)
				c.y--;
		}
		while(p.y < gh) {
			p.y++;
			for(Collidable c:clist)
				c.y++;
		}
	}
	
	public void render(Graphics2D g) {
		if(renderMC)
			mc.render(g);
		
		g.setColor(color);
		g.fillRect((int)x, (int)y, (int)w, (int)h);
		
		if(renderJumpCollider) {
			// onGround check...
			g.setColor(Color.blue);
			g.fillRect((int)this.x, (int)(this.y + this.h), (int)this.w, 1);
		}
	}
	
	public PlayState getState() {
		return (PlayState)game.stateMachine.getCurrentState();
	}
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_W)
			moveUp = true;
		
		if(e.getKeyCode() == KeyEvent.VK_S) {
			moveDown = true;
			duck();
		}
		
		if(e.getKeyCode() == KeyEvent.VK_A)
			moveLeft = true;
		
		if(e.getKeyCode() == KeyEvent.VK_D)
			moveRight = true;
		
		if(e.getKeyCode() == KeyEvent.VK_NUMPAD0)
			jump();
	}
	
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_W) {
			velocityY = 0;
			moveUp = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_S) {
			velocityY = 0;
			moveDown = false;
			duckRelease();
		}
		
		if(e.getKeyCode() == KeyEvent.VK_A) {
			velocityX += speed;
			moveLeft = false;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_D) {
			velocityX -= speed;
			moveRight = false;
		}
		
		// Release Jump....
		if(e.getKeyCode() == KeyEvent.VK_NUMPAD0)
			jumpRelease();
		
		if(e.getKeyCode() == KeyEvent.VK_F1) {
			if(mcCorrection) {
				mcCorrection = false;
				System.out.println("Correction Disabled");
			}
			else {
				mcCorrection = true;
				System.out.println("Correction Enabled");
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_F2) {
			if(renderMC)
				renderMC = false;
			else
				renderMC = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_G) {
			if(!gravityEnabled) {
				gravityEnabled = true;
				System.out.println("Gravity Enabled");
				velocityY = 0;
			}
			else {
				gravityEnabled = false;
				System.out.println("Gravity Disabled");
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_F3) {
			if(upDownMovement) {
				upDownMovement = false;
				System.out.println("UpDown Disabled");
			}
			else {
				upDownMovement = true;
				System.out.println("UpDown Enabled");
			}
		}
	}
}
