package zombieapocalypse;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.swing.ImageIcon;

import enginex.EngineX;
import enginex.State;

public class PlayState extends State {
	Player								player;
	Enemy									enemy;
	
	ArrayList<Enemy>			enemies											= new ArrayList<>();
	ArrayList<Blood>			bloods											= new ArrayList<>();
	ArrayList<Healthpack>	healthpacks									= new ArrayList<>();
	
	int										startingEnemies							= 10;
	int										maxEnemies									= startingEnemies;
	int										enemyIncreaseCountdownTime	= 5000;
	int										enemyIncreaseCountdown			= enemyIncreaseCountdownTime;
	int										score												= 0;
	int										timeSpent										= 0;
	int										healthpackCount							= 2000;
	int										healthpackCounter						= healthpackCount;
	long									tStart											= 0;
	long									tEnd												= 0;
	long									tDelta											= tEnd - tStart;
	double								elapsedSeconds							= tDelta / 1000;
	int										timeAlive										= 0;
	
	boolean								paused											= false;
	
	Image									enemyImage									= new ImageIcon("res/zombie.png").getImage();
	Image									bloodImage0									= new ImageIcon("res/blood0.png").getImage();
	Image									bloodImage1									= new ImageIcon("res/blood1.png").getImage();
	Image									grassImage									= new ImageIcon("res/grass.png").getImage();
	Image									healthpackImage							= new ImageIcon("res/healthpack.png").getImage();
	Image									bombImage										= new ImageIcon("res/bomb.png").getImage();
	Image									playerImage									= new ImageIcon("res/player.png").getImage();
	Image									deadImage										= new ImageIcon("res/dead.png").getImage();
	Image									crosshairImage							= new ImageIcon("res/crosshair.png").getImage();
	
	public PlayState(EngineX game) {
		super(game);
	}
	
	public void init() {
		tStart = getTime();
		player = new Player(game);
		super.addGameObject(player);
		
		for(int i = 0; i < maxEnemies; i++) {
			addEnemy();
		}
		game.hideDefaultCursor();
	}
	
	void updateBloods() {
		for(Iterator<Blood> it = bloods.iterator(); it.hasNext();) {
			Blood b = it.next();
			if(b.isDisposable()) {
				// Dispose of Unwanted Bloods
				it.remove();
			}
			else {
				if(b.updateEnabled) {
					// Update Bloods
					b.update();
				}
			}
		}
	}
	
	long getTime() {
		return System.currentTimeMillis();
	}
	
	int getElapsedSeconds() {
		return (int)((getTime() - tStart) / 1000);
	}
	
	int getElapsedMillis() {
		return (int)(getTime() - tStart);
	}
	
	public void update() {
		if(!paused) {
			if(player.alive) {
				timeSpent++;
				player.update();
				updateEnemies();
				updateBloods();
				updateHealthpacks();
			}
		}
	}
	
	public void render(Graphics2D g) {
		renderGrass(g);
		renderBloods(g);
		player.render(g);
		renderHealthpacks(g);
		renderEnemies(g);
		player.renderHealth(g);
		
		renderText(g);
		renderCrosshair(g);
	}
	
	void updateHealthpacks() {
		if(healthpackCounter == 0) {
			if(healthpacks.size() == 0) {
				if(player.health < 100) {
					// healthpacks.add(new Healthpack(game, (int)Math.random() *
					// game.width - 30, (int)Math.random() * game.height - 30));
					int healthx = (int)(Math.random() * game.getWidth() - Healthpack.w);
					int healthy = (int)(Math.random() * game.getHeight() - Healthpack.h);
					
					// X Location
					if(healthx < Healthpack.w) {
						healthx = Healthpack.w * 2;
					}
					if(healthx > game.getWidth() - Healthpack.w * 3) {
						healthx = game.getWidth() - Healthpack.w * 3;
					}
					
					// Y Location
					if(healthy < Healthpack.h) {
						healthy = Healthpack.h * 2;
					}
					if(healthy > game.getHeight() - Healthpack.h * 3) {
						healthy = game.getHeight() - Healthpack.h * 3;
					}
					
					healthpacks.add(new Healthpack(game, healthx, healthy));
				}
			}
		}
		
		for(Iterator<Healthpack> it = healthpacks.iterator(); it.hasNext();) {
			Healthpack h = it.next();
			if(h.isDisposable()) {
				// Dispose of Unwanted Healthpacks
				it.remove();
			}
			else {
				if(h.updateEnabled) {
					h.update();
				}
			}
		}
		
		if(healthpackCounter <= 0) {
			healthpackCounter = healthpackCount;
		}
		healthpackCounter--;
	}
	
	void renderCrosshair(Graphics2D g) {
		try {
			Point p = game.getMousePosition();
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
			g.drawImage(crosshairImage, (int)p.getX() - 10, (int)p.getY() - 10, null);
		}
		catch(Exception e) {}
	}
	
	void renderHealthpacks(Graphics2D g) {
		for(Healthpack h:healthpacks) {
			h.render(g);
		}
	}
	
	void renderGrass(Graphics2D g) {
		g.drawImage(grassImage, 0, 0, null);
	}
	
	void renderText(Graphics2D g) {
		String s = "";
		int textSize = 15;
		
		// Kills
		s = "Kills: " + score;
		drawText(s, 5, 1 * textSize, g);
		
		// Health
		s = "Health: " + player.health;
		drawText(s, 5, 2 * textSize, g);
		
		// Time Alive
		if(player.alive)
			timeAlive = getElapsedMillis();
		s = getDateTimeString("mm:ss", timeAlive);
		drawText("Time Alive: " + s, 5, 3 * textSize, g);
	}
	
	String getDateTimeString(String format, int millis) {
		return (new SimpleDateFormat(format)).format(new Date(millis));
	}
	
	void drawText(String s, int x, int y, Graphics2D g) {
		g.setFont(new Font("Arial", Font.BOLD, 12));
		g.setColor(Color.WHITE);
		g.drawString(s, x, y);
	}
	
	void updateEnemies() {
		for(Iterator<Enemy> it = enemies.iterator(); it.hasNext();) {
			Enemy e = it.next();
			if(e.isDisposable()) {
				// Dispose of Unwanted Enemies
				bloods.add(new Blood(game, (int)e.x, (int)e.y));
				it.remove();
				score++;
			}
			else {
				if(e.updateEnabled) {
					// Update Game Object
					e.update();
				}
			}
		}
		
		if(enemies.size() < maxEnemies) {
			addEnemy();
		}
		
		increaseEnemies();
	}
	
	void increaseEnemies() {
		enemyIncreaseCountdown--;
		if(enemyIncreaseCountdown == 0 && maxEnemies > 0) {
			maxEnemies++;
			enemyIncreaseCountdown = enemyIncreaseCountdownTime;
		}
	}
	
	void addEnemy() {
		if(enemies.size() < maxEnemies && maxEnemies > 0) {
			if(getRandomDirection() == "up") {
				enemies.add(new Enemy(game, (double)(Math.random() * game.width), 0 - Enemy.HEIGHT));
			}
			else if(getRandomDirection() == "down") {
				enemies.add(new Enemy(game, (double)(Math.random() * game.width), game.height + Enemy.HEIGHT));
			}
			else if(getRandomDirection() == "left") {
				enemies.add(new Enemy(game, 0 - Enemy.WIDTH, (double)(Math.random() * game.height)));
			}
			else {
				enemies.add(new Enemy(game, game.width + Enemy.WIDTH, (double)(Math.random() * game.height)));
			}
		}
	}
	
	String getRandomDirection() {
		int v = 0 + (int)(Math.random() * 4);
		
		if(v == 0)
			return "up";
		else if(v == 1)
			return "down";
		else if(v == 2)
			return "left";
		else if(v == 3)
			return "right";
		else
			return "";
	}
	
	void renderEnemies(Graphics2D g) {
		for(Enemy e:enemies) {
			e.render(g);
		}
	}
	
	void renderBloods(Graphics2D g) {
		for(Blood b:bloods) {
			b.render(g);
		}
	}
	
	void pauseGame() {
		if(!paused) {
			paused = true;
		}
		else {
			paused = false;
		}
	}
	
	void resetGame() {
		score = 0;
		player.x = game.width / 2 - player.w / 2;
		player.y = game.height / 2 - player.h / 2;
		player.alive = true;
		player.health = 100;
		enemies = new ArrayList<>();
		healthpacks = new ArrayList<>();
		bloods = new ArrayList<>();
		player.bullets = new ArrayList<>();
		healthpackCounter = healthpackCount;
		maxEnemies = startingEnemies;
		enemyIncreaseCountdown = enemyIncreaseCountdownTime;
		paused = false;
		addEnemy();
		tStart = getTime();
	}
	
	public void keyPressed(KeyEvent e) {
		super.keyPressed(e);
		
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}
		
		if(e.getKeyCode() == KeyEvent.VK_R) {
			resetGame();
		}
		
		if(e.getKeyCode() == KeyEvent.VK_Q) {
			pauseGame();
		}
	}
	
	public void mouseWheelMoved(MouseWheelEvent e) {
		super.mouseWheelMoved(e);
		
		if(e.getWheelRotation() < 0)
			game.adjustScale(2);
		else
			game.adjustScale(0.5);
	}
}
