package spaceshooter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import enginex.GameObject;
import enginex.Util;
import net.java.games.input.Component;

@SuppressWarnings("serial")
public class Player extends GameObject {
	Spaceshooter						game;
	Component[]							components;
	
	double									x												= 0;
	double									y												= 0;
	public static final int	WIDTH										= 50;
	public static final int	HEIGHT									= 50;
	int											h												= 50;
	Color										color										= Color.WHITE;
	
	boolean									alive										= true;
	public static final int	DEFAULT_LIVES						= 5;
	public static final int	DEFAULT_LIFE						= 100;
	public int				lives			= DEFAULT_LIVES;
	int						life			= DEFAULT_LIFE;
	
	boolean									left										= false;
	boolean									right										= false;
	boolean									up											= false;
	boolean									down										= false;
	
	boolean									boosting								= false;
	int											boostRemaining					= 0;
	int											boostMaxTime						= 5;
	float										boostSpeed							= 30;
	int											boostMaxCharge					= 20;
	int											boostCharge							= 100;
	
	boolean									verticalMovementEnabled	= true;
	float										moveX										= 0f;
	float										moveY										= 0f;
	float										speed										= 5;
	
	boolean									shooting								= false;
	int											maxBullets							= 100;
	int											bulletCooldown					= 0;
	int											bulletCooldownMax				= 10;
	ArrayList<PlayerBullet>	bullets									= new ArrayList<>();
	
	int											currentScore						= 0;
	
	boolean									drawBounds							= false;
	
	
	public Player(Spaceshooter game, int x, int y) {
		super(game);
		this.game = game;
		this.x = x;
		this.y = y;
	}
	
	public void update() {
		boost();
		move();
		updateBounds();
		
		updateBullets();
		shoot();
	}
	
	public void deductLife(int damage) {
		// Deduct Life
		if(life > 0) {
			if((life - damage) > 0) {
				life -= damage;
				game.res.playerHurt.getSound().playSound();
			}
			else {
				life = 0;
				game.res.playerExplosion.getSound().playSound();
			}
		}
		
		// Deduct Lives
		if(life <= 0) {
			lives--;
			x = game.width / 2 - Player.WIDTH / 2;
			y = (game.height - Player.HEIGHT * 2) - 75;
			if(lives > 0)
				life = 100;
		}
		
		// Check Game Over
		if(lives <= 0)
			alive = false;
	}
	
	void showID(Object o) {
		System.out.println(System.identityHashCode(o));
	}
	
	public void render(Graphics2D g) {
		// Draw Ship
		g.drawImage(game.res.player.getImage(), (int)x, (int)y, null);
		
		// Draw Score
		Util.drawText(25, 515, "Score: " + Integer.toString(currentScore), 32, Color.WHITE, g);
		
		// Draw Life
		g.setColor(Color.YELLOW);
		int lifeX = 25;
		int lifeY = game.height - 100;
		if(life > 90)
			g.fillRect(lifeX, lifeY, 100, 10);
		if(life > 80)
			g.fillRect(lifeX, lifeY, 90, 10);
		if(life > 70)
			g.fillRect(lifeX, lifeY, 80, 10);
		if(life > 60)
			g.fillRect(lifeX, lifeY, 70, 10);
		if(life > 50)
			g.fillRect(lifeX, lifeY, 60, 10);
		if(life > 40)
			g.fillRect(lifeX, lifeY, 50, 10);
		if(life > 30)
			g.fillRect(lifeX, lifeY, 40, 10);
		if(life > 20)
			g.fillRect(lifeX, lifeY, 30, 10);
		if(life > 10)
			g.fillRect(lifeX, lifeY, 20, 10);
		if(life > 0)
			g.fillRect(lifeX, lifeY, 10, 10);
		
		// Draw Lives
		for(int i = 0; i < lives; i++) {
			g.drawImage(game.res.player.getImage(), (50 * i) + 20, game.height - 90, null);
		}
		
		// Draw Bounds
		if(drawBounds) {
			g.setColor(Color.WHITE);
			g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
		}
		
		// Draw Bullets
		renderBullets(g);
	}
	
	protected void updateBounds() {
		bounds.setLocation(new Point((int)x, (int)y + 15));
		bounds.setSize(new Dimension(WIDTH, HEIGHT - 25));
	}
	
	public PlayState getCurrentState() {
		return (PlayState)game.stateMachine.getCurrentState();
	}
	
	public void renderBullets(Graphics2D g) {
		for(PlayerBullet b:bullets)
			b.render(g);
	}
	
	public void activateBoost() {
		if(boostCharge == boostMaxCharge) {
			boosting = true;
			boostRemaining = boostMaxTime;
			boostCharge = 0;
		}
	}
	
	public void boost() {
		if(boostCharge < boostMaxCharge)
			boostCharge++;
		else
			boostCharge = boostMaxCharge;
		
		if(boosting) {
			boostRemaining--;
			
			if(boostRemaining <= 0) {
				boosting = false;
				boostRemaining = 0;
			}
		}
	}
	
	public void resetPosition() {
		x = game.width / 2 - Player.WIDTH / 2;
		y = (game.height - Player.HEIGHT * 2) - 75;
		
		// Need to put some place better
		shooting = false;
		left = false;
		right = false;
		up = false;
		down = false;
		bullets = new ArrayList<>();
	}
	
	public void resetScore() {
		currentScore = 0;
	}
	
	public void removeAllBullets() {
		bullets = new ArrayList<>();
	}
	
	public void move() {
		if(up && !down) {
			if(y > 250) {
				if(!boosting)
					y -= speed;
				else
					y -= boostSpeed;
			}
			else {
				y = 250;
			}
		}
		else if(down && !up) {
			int v1 = 79 + 75;
			if(y < game.height - v1) {
				if(!boosting)
					y += speed;
				else
					y += boostSpeed;
			}
			else {
				y = game.height - v1;
			}
		}
		
		if(left && !right) {
			if(x > 50) {
				if(!boosting)
					x -= speed;
				else
					x -= boostSpeed;
			}
			else {
				x = 50;
			}
		}
		else if(right && !left) {
			int v1 = 56 + 50;
			if(x < game.width - v1) {
				if(!boosting)
					x += speed;
				else
					x += boostSpeed;
			}
			else {
				x = game.width - v1;
			}
		}
	}
	
	public void shoot() {
		if(shooting) {
			if(bulletCooldown == 0) {
				if(bullets.size() < maxBullets) {
					game.res.playerShoot.getSound().playSound();
					bullets.add(new PlayerBullet(game, (int)this.x + WIDTH / 2, (int)this.y));
					bulletCooldown = bulletCooldownMax;
				}
			}
		}
	}
	
	public void updateBullets() {
		if(bulletCooldown > 0)
			bulletCooldown--;
		
		for(int i = 0; i < bullets.size(); i++) {
			PlayerBullet b = bullets.get(i);
			if(b.outOfBounds || b.used)
				bullets.remove(i);
		}
		
		for(PlayerBullet b:bullets)
			b.update();
	}
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_A)
			left = true;
		if(e.getKeyCode() == KeyEvent.VK_D)
			right = true;
		
		if(verticalMovementEnabled) {
			if(e.getKeyCode() == KeyEvent.VK_W)
				up = true;
			if(e.getKeyCode() == KeyEvent.VK_S)
				down = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			activateBoost();
		}
	}
	
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_A)
			left = false;
		if(e.getKeyCode() == KeyEvent.VK_D)
			right = false;
		
		if(verticalMovementEnabled) {
			if(e.getKeyCode() == KeyEvent.VK_W)
				up = false;
			if(e.getKeyCode() == KeyEvent.VK_S)
				down = false;
		}
	}
	
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1)
			shooting = true;
		if(e.getButton() == MouseEvent.BUTTON3)
			activateBoost();
	}
	
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			shooting = false;
			bulletCooldown = 0;
		}
	}
	
	public void joystickPoll(Component[] components) {
		for(Component c:components) {
			String componentName = c.getName();
			float pollData = c.getPollData();
			
			// SHOW EVERYTHING
			// System.out.println("[" + componentName + "]---:---[" + pollData + "]");
			
			// Y Axis
			if(componentName.equals("Y Axis")) {
				if(pollData == -1.0)
					up = true;
				else if(pollData == 1.0)
					down = true;
				else {
					up = false;
					down = false;
				}
			}
			
			// X Axis
			if(componentName.equals("X Axis")) {
				if(pollData == -1.0)
					left = true;
				else if(pollData == 1.0)
					right = true;
				else {
					left = false;
					right = false;
				}
				
			}
			
			// Button 2
			if(componentName.equals("Button 2")) {
				if(pollData == 1.0)
					shooting = true;
				else
					shooting = false;
			}
			
			if(componentName.equals("Button 7"))
				if(pollData == 1.0)
					activateBoost();
				
			if(componentName.equals("Button 0"))
				if(pollData == 1.0)
					getCurrentState().reset();
				
			if(componentName.equals("Button 4")) {
				if(pollData == 1.0) {
					getCurrentState().reset();
					game.stateMachine.setState(game.MENU);
				}
			}
		}
	}
}
