package titanclones;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import enginex.Animation;
import enginex.Phys;
import enginex.Sound;
import enginex.Sprite;
import enginex.Util;

public class Player {
	TitanClones game;

	double	x		= 0;
	double	y		= 0;
	int		w		= 18 * 2;
	int		h		= 25 * 2;
	Color	color	= Color.red;

	float	speed		= 7f;
	float	velocityX	= 0;
	float	velocityY	= 0;

	float	gravity			= 0.35f;
	float	maxGravity		= 12f;
	boolean	gravityEnabled	= true;
	float	jumpSpeed		= 9.7f;
	boolean	jumpDynamic		= true;

	boolean	left	= false;
	boolean	right	= false;
	boolean	up		= false;
	boolean	down	= false;
	boolean	rolling	= false;

	String direction = "right";

	BufferedImage[]	runRightImages;
	BufferedImage[]	runLeftImages;

	Animation	runLeft;
	Animation	runRight;
	Animation	idleLeft;
	Animation	idleRight;

	Animation currentAnimation;

	ArrayList<Collidable>	clist		= new ArrayList<>();
	boolean					correction	= true;
	int						life		= 4;

	boolean canShoot = true;

	String	jumpSound	= "res/titanclones/jump.ogg";
	String	shootSound	= "res/titanclones/shoot.ogg";
	String	hurtSound	= "res/titanclones/hurt.ogg";

	public static final int	A			= 0;
	public static final int	B			= 1;
	public static final int	C			= 2;
	public static final int	D			= 3;
	public int				playerType	= A;

	public int	score	= 0;
	int			iniX	= 0;
	int			iniY	= 0;

	ArrayList<Bullet> bullets = new ArrayList<>();

	public Player(TitanClones game, int x, int y, int playerType) {
		this.game = game;
		this.x = x;
		this.y = y;
		this.iniX = x;
		this.iniY = y;
		this.playerType = playerType;
		setupAnimations();
		clist = getState().clist;
		game.soundMachine.add(new Sound(jumpSound));
		game.soundMachine.add(new Sound(shootSound));
		game.soundMachine.add(new Sound(hurtSound));
	}

	public void update() {
		updateAnimation();
		gravity();
		move();
		updateBullets();

		updateVX();
		updateVY();

		if(x + 18 > game.width)
			x = 0 - 18;
		if(x + 18 < 0)
			x = game.width - 18;

		if(y + 25 > game.height)
			y = 0 - 25;
		//		if(y+25 < 0)
		//			y = game.height - 25;

		//		this.x += velocityX;
		//		this.y += velocityY;
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

		if(!collision)
			this.x += velocityX;
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

		if(!collision) {
			this.y += velocityY;

			//			if(this.y+velocityY > -25)
			//				this.y += velocityY;
			//			else
			//				velocityY = 0;
		}
		else
			velocityY = 0;
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
		if(onGround()) {
			game.soundMachine.playSound("res/titanclones/jump.ogg");
			velocityY = -jumpSpeed;
		}
	}

	void jumpRelease() {
		if(jumpDynamic)
			if(velocityY < -(jumpSpeed / 4))
				velocityY += jumpSpeed / 3;
	}

	public PlayState getState() {
		return (PlayState)game.stateMachine.getCurrentState();
	}

	void showID(Object o) {
		System.out.println(System.identityHashCode(o));
	}

	void setAnimation(Animation a) {
		if(currentAnimation != a)
			currentAnimation = a;
	}

	void setupAnimations() {
		if(playerType == A) {
			runLeftImages = new BufferedImage[] {Sprite.getSprite(0, 0, 32, 32, "res/titanclones/runLeft.png"), Sprite.getSprite(1, 0, 32, 32, "res/titanclones/runLeft.png"), Sprite.getSprite(2, 0, 32, 32, "res/titanclones/runLeft.png")};

			runRightImages = new BufferedImage[] {Sprite.getSprite(0, 0, 32, 32, "res/titanclones/runRight.png"), Sprite.getSprite(1, 0, 32, 32, "res/titanclones/runRight.png"), Sprite.getSprite(2, 0, 32, 32, "res/titanclones/runRight.png")};

			runLeft = new Animation(runLeftImages, 3);
			runRight = new Animation(runRightImages, 3);
			idleLeft = new Animation(Sprite.getSprite(1, 0, 32, 32, "res/titanclones/runLeft.png"));
			idleRight = new Animation(Sprite.getSprite(1, 0, 32, 32, "res/titanclones/runRight.png"));
		}

		if(playerType == B) {
			runLeftImages = new BufferedImage[] {Sprite.getSprite(0, 0, 32, 32, "res/titanclones/runLeftB.png"), Sprite.getSprite(1, 0, 32, 32, "res/titanclones/runLeftB.png"), Sprite.getSprite(2, 0, 32, 32, "res/titanclones/runLeftB.png")};

			runRightImages = new BufferedImage[] {Sprite.getSprite(0, 0, 32, 32, "res/titanclones/runRightB.png"), Sprite.getSprite(1, 0, 32, 32, "res/titanclones/runRightB.png"), Sprite.getSprite(2, 0, 32, 32, "res/titanclones/runRightB.png")};

			runLeft = new Animation(runLeftImages, 3);
			runRight = new Animation(runRightImages, 3);
			idleLeft = new Animation(Sprite.getSprite(1, 0, 32, 32, "res/titanclones/runLeftB.png"));
			idleRight = new Animation(Sprite.getSprite(1, 0, 32, 32, "res/titanclones/runRightB.png"));
		}

		setAnimation(idleRight);
	}

	public void updateAnimation() {
		if(right)
			setAnimation(runRight);

		if(left)
			setAnimation(runLeft);

		if(!right && !left) {
			if(direction == "right")
				setAnimation(idleRight);
			else
				setAnimation(idleLeft);
		}

		currentAnimation.start();
		currentAnimation.update();
	}

	public void render(Graphics2D g) {
		renderBullets(g);

		AffineTransform at = new AffineTransform();
		at.scale(game.scale, game.scale);
		at.translate((x / game.scale) - 7, (y / game.scale) - 8);
		g.drawImage(currentAnimation.getSprite(), at, null);

		drawLife(g);
		drawScore(g);
	}

	void drawScore(Graphics2D g) {
		int fontSize = 20;
		if(playerType == A)
			Util.drawText(45, 60, "" + score, fontSize, g);
		else
			Util.drawText(game.width - 55, 60, "" + score, fontSize, g);
	}

	void drawLife(Graphics2D g) {
		g.setColor(Color.RED);
		if(playerType == A) {
			if(life >= 1)
				g.fillRect(10, 10, 20, 20);

			if(life >= 2)
				g.fillRect(40, 10, 20, 20);

			if(life >= 3)
				g.fillRect(70, 10, 20, 20);

			if(life >= 4)
				g.fillRect(100, 10, 20, 20);
		}
		else {
			if(life >= 1)
				g.fillRect(game.width - 30, 10, 20, 20);

			if(life >= 2)
				g.fillRect(game.width - 60, 10, 20, 20);

			if(life >= 3)
				g.fillRect(game.width - 90, 10, 20, 20);

			if(life >= 4)
				g.fillRect(game.width - 120, 10, 20, 20);
		}
	}

	void roll() {
		if(!rolling) {
			if(direction == "up") {

			}
			if(direction == "up") {

			}
			if(direction == "up") {

			}
			if(direction == "up") {

			}
		}
	}

	void shoot() {
		if(canShoot) {
			if(bullets.size() < 1) {
				game.soundMachine.playSound(shootSound);
				if(direction == "left")
					bullets.add(new Bullet(game, (int)x + 10, (int)y + 10, Bullet.LEFT));
				else
					bullets.add(new Bullet(game, (int)x + 10, (int)y + 10, Bullet.RIGHT));
			}
		}
	}

	void renderBullets(Graphics2D g) {
		try {
			for(Bullet b:bullets)
				b.render(g);
		}
		catch(Exception e) {}
	}

	void updateBullets() {
		try {
			for(int i = bullets.size() - 1; i >= 0; i--) {
				bullets.get(i).update();

				if(bullets.get(i).x > game.width - Bullet.w / 2)
					bullets.get(i).x = 0 - Bullet.w / 2;

				if(bullets.get(i).x < 0 - Bullet.w / 2)
					bullets.get(i).x = game.width - Bullet.w / 2;

				if(bullets.get(i).y > game.height)
					bullets.remove(i);

				if(wallCollision(bullets.get(i)))
					bullets.remove(i);

				if(playerCollision(bullets.get(i))) {
					bullets.remove(i);
				}
			}
		}
		catch(Exception e) {}
	}

	boolean playerCollision(Bullet b) {
		if(playerType == A) {
			for(Player p:getState().players)
				if(p.playerType == B)
					if(Phys.collision(new Rectangle((int)p.x, (int)(int)p.y, (int)p.w, (int)p.h), new Rectangle((int)b.x, (int)b.y, (int)Bullet.w, (int)Bullet.h))) {
						p.getHurt();
						return true;
					}
		}

		if(playerType == B) {
			for(Player p:getState().players)
				if(p.playerType == A)
					if(Phys.collision(new Rectangle((int)p.x, (int)(int)p.y, (int)p.w, (int)p.h), new Rectangle((int)b.x, (int)b.y, (int)Bullet.w, (int)Bullet.h))) {
						p.getHurt();
						return true;
					}
		}

		return false;
	}

	public void getHurt() {
		game.soundMachine.playSound(hurtSound);
		if(life > 0)
			life--;

		if(life == 0) {
			resetPositions();

			if(playerType == A) {
				getState().playerB.score++;
			}

			if(playerType == B) {
				getState().playerA.score++;
			}

			getState().playerA.life = 4;
			getState().playerB.life = 4;
		}
	}

	void reset() {
		getState().playerA.life = 4;
		getState().playerB.life = 4;
		resetPositions();
		getState().playerA.score = 0;
		getState().playerB.score = 0;
	}

	void resetPositions() {
		getState().playerA.x = getState().playerA.iniX;
		getState().playerA.y = getState().playerA.iniY;
		getState().playerB.x = getState().playerB.iniX;
		getState().playerB.y = getState().playerB.iniY;
	}

	boolean wallCollision(Bullet b) {
		for(Collidable c:clist) {
			if(Phys.collision(new Rectangle((int)c.x, (int)(int)c.y, (int)c.w, (int)c.h), new Rectangle((int)b.x, (int)b.y, (int)Bullet.w, (int)Bullet.h)))
				return true;
		}

		return false;
	}

	void aPressed() {
		jump();
	}

	void aReleased() {
		jumpRelease();
	}

	void bPressed() {
		shoot();
		canShoot = false;
	}

	void bReleased() {
		canShoot = true;
	}

	public void move() {
		if(up)
			velocityY = -speed;
		if(down)
			velocityY = speed;
		if(left) {
			direction = "left";
			velocityX = -speed;
		}
		if(right) {
			direction = "right";
			velocityX = speed;
		}
	}

	public void keyPressed(KeyEvent e) {
		if(playerType == A) {
			if(e.getKeyCode() == KeyEvent.VK_A)
				left = true;

			if(e.getKeyCode() == KeyEvent.VK_D)
				right = true;

			if(e.getKeyCode() == KeyEvent.VK_F)
				aPressed();

			if(e.getKeyCode() == KeyEvent.VK_G)
				bPressed();
		}

		if(playerType == B) {
			if(e.getKeyCode() == KeyEvent.VK_LEFT)
				left = true;

			if(e.getKeyCode() == KeyEvent.VK_RIGHT)
				right = true;

			if(e.getKeyCode() == KeyEvent.VK_NUMPAD1)
				aPressed();

			if(e.getKeyCode() == KeyEvent.VK_NUMPAD2)
				bPressed();
		}
	}

	public void keyReleased(KeyEvent e) {
		if(playerType == A) {
			if(e.getKeyCode() == KeyEvent.VK_A) {
				left = false;
				direction = "left";
				velocityX = 0;

				currentAnimation.stop();
				currentAnimation.reset();
				currentAnimation = idleLeft;
			}
			if(e.getKeyCode() == KeyEvent.VK_D) {
				right = false;
				direction = "right";
				velocityX = 0;

				currentAnimation.stop();
				currentAnimation.reset();
				currentAnimation = idleRight;
			}

			if(e.getKeyCode() == KeyEvent.VK_F)
				aReleased();

			if(e.getKeyCode() == KeyEvent.VK_G)
				bReleased();
		}

		if(playerType == B) {
			if(e.getKeyCode() == KeyEvent.VK_LEFT) {
				left = false;
				direction = "left";
				velocityX = 0;

				currentAnimation.stop();
				currentAnimation.reset();
				currentAnimation = idleLeft;
			}
			if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
				right = false;
				direction = "right";
				velocityX = 0;

				currentAnimation.stop();
				currentAnimation.reset();
				currentAnimation = idleRight;
			}

			if(e.getKeyCode() == KeyEvent.VK_NUMPAD1)
				aReleased();

			if(e.getKeyCode() == KeyEvent.VK_NUMPAD2)
				bReleased();
		}
	}
}
