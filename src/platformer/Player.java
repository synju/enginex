package platformer;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import enginex.FileManager;
import enginex.GameObject;

@SuppressWarnings("serial")
public class Player extends GameObject {
	Platformer	game;
	double		x;
	double		y;
	int			w			= 50;
	int			h			= 50;
	Color		color		= Color.RED;
	boolean		moveLeft	= false;
	boolean		moveRight	= false;
	boolean		moveUp		= false;
	boolean		moveDown	= false;

	int		speed		= 5;
	// float jumpSpeed = 8.61f;
	float	jumpSpeed	= 6.41f;
	boolean	jumpDynamic	= false;

	float velocityX, velocityY;

	float		gravity			= 0.35f;
	float		maxGravity		= 12f;
	boolean		gravityEnabled	= true;
	FileManager	fm				= new FileManager();

	ArrayList<Collidable> clist = new ArrayList<>();

	MovementContainer	mc;
	boolean				renderMC		= true;
	boolean				correction		= true;
	boolean				mcCorrection	= false;

	public Player(Platformer game, int x, int y) {
		super(game);
		this.game = game;
		this.x = x;
		this.y = y;

		mc = new MovementContainer(game, 200, 150, 2 * 50, 2 * 50);
	}

	void save() {
		List<String> saveList = Arrays.asList(fm.toString(x), fm.toString(y));
		fm.save("playerSaveFile", saveList);
	}

	void load() {
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

		// Collision Correction
		if(correction)
			for(Collidable c:clist) {
				Rectangle a = new Rectangle((int)this.x, (int)this.y, (int)this.w, (int)this.h);
				Rectangle b = new Rectangle((int)c.x, (int)c.y, (int)c.w, (int)c.h);
				if(Phys.collision(a, b))
					this.y--;
			}

		// Camera Adjustment
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
	}

	public void centerCamera() {
		Player p = getState().p;
		ArrayList<Collidable> clist = getState().clist;

		while(p.x < game.width / 2 - p.w / 2) {
			p.x++;
			for(Collidable c:clist)
				c.x++;
		}

		while(p.y < game.height / 2 - p.h / 2) {
			p.y++;
			for(Collidable c:clist)
				c.y++;
		}

		while(p.x > game.width / 2 - p.w / 2) {
			p.x--;
			for(Collidable c:clist)
				c.x--;
		}

		while(p.y > game.height / 2 - p.h / 2) {
			p.y--;
			for(Collidable c:clist)
				c.y--;
		}
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

	void jump() {
		if(onGround())
			velocityY = -jumpSpeed;
	}

	void jumpRelease() {
		if(jumpDynamic)
			if(velocityY < -(jumpSpeed / 4))
				velocityY += jumpSpeed / 3;
	}

	void duck() {
		if(onGround())
			if(this.h > 25) {
				this.h = 25;
				this.y += 25;
			}
	}

	void duckRelease() {
		if(this.h < 50) {
			this.h = 50;
			this.y -= 25;
		}
	}

	void move() {
		// if(moveUp && (velocityY > -speed))
		// velocityY = -speed;

		// if(moveDown && (velocityY < speed))
		// velocityY = speed;

		if(moveLeft)
			if(velocityX > -speed)
				velocityX -= speed;

		if(moveRight)
			if(velocityX < speed)
				velocityX += speed;

		if(moveLeft && moveRight)
			velocityX = 0;
	}

	public void render(Graphics2D g) {
		g.setColor(color);
		g.fillRect((int)x, (int)y, (int)w, (int)h);

		// onGround check...
		g.setColor(Color.blue);
		g.fillRect((int)this.x, (int)(this.y + this.h), (int)this.w, 1);

		// Render MC
		if(renderMC)
			mc.render(g);
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

		// Jump
		if(e.getKeyCode() == KeyEvent.VK_NUMPAD0)
			jump();

		if(e.getKeyCode() == KeyEvent.VK_G) {
			if(gravityEnabled) {
				gravityEnabled = false;
				velocityY = 0;
			}
			else
				gravityEnabled = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_W) {
			// velocityY += speed;
			moveUp = false;
		}

		if(e.getKeyCode() == KeyEvent.VK_S) {
			// velocityY -= speed;
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

		// Jump Release
		if(e.getKeyCode() == KeyEvent.VK_NUMPAD0)
			jumpRelease();

		if(e.getKeyCode() == KeyEvent.VK_F1) {
			if(correction) {
				correction = false;
				System.out.println("Correction Disabled");
			}
			else {
				correction = true;
				System.out.println("Correction Enabled");
			}
		}

		// Render MovementCamera
		if(e.getKeyCode() == KeyEvent.VK_F2)
			if(renderMC)
				renderMC = false;
			else
				renderMC = true;

		if(e.getKeyCode() == KeyEvent.VK_F3) {
			if(!mcCorrection) {
				mcCorrection = true;
				System.out.println("Movement Camera Enabled");
			}
			else {
				mcCorrection = false;
				System.out.println("Movement Camera Disabled");
			}
		}

		if(e.getKeyCode() == KeyEvent.VK_F4) {
			if(!jumpDynamic) {
				jumpDynamic = true;
				System.out.println("Dynamic Jump Enabled");
			}
			else {
				jumpDynamic = false;
				System.out.println("Dynamic Jump Disabled");
			}
		}

		// Jumpspeed Modifier
		if(e.getKeyCode() == KeyEvent.VK_ADD) {
			jumpSpeed += 0.01f;
			System.out.println(-jumpSpeed);
		}
		if(e.getKeyCode() == KeyEvent.VK_SUBTRACT) {
			jumpSpeed -= 0.01f;
			System.out.println(-jumpSpeed);
		}
	}
}
