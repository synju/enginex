package Complete.zombieapocalypse;

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

import EngineX.EngineX;
import EngineX.State;
import Project_Platformer.Config;
import com.studiohartman.jamepad.ControllerManager;
import com.studiohartman.jamepad.ControllerState;

public class PlayState extends State {
	Game game;
	Player player;
	Enemy enemy;

	ArrayList<Enemy> enemies = new ArrayList<>();
	ArrayList<Blood> bloods = new ArrayList<>();
	ArrayList<Healthpack> healthpacks = new ArrayList<>();

	int startingEnemies = 5;
	//	int startingEnemies = 0;
	int maxEnemies = startingEnemies;
	int enemyIncreaseCountdownTime = 5000;
	int enemyIncreaseCountdown = enemyIncreaseCountdownTime;
	int score = 0;
	int timeSpent = 0;
	int healthpackCount = 2000;
	int healthpackCounter = healthpackCount;
	long tStart = 0;
	long tEnd = 0;
	long tDelta = tEnd - tStart;
	double elapsedSeconds = (int)(tDelta / 1000);
	int timeAlive = 0;

	boolean paused = false;

	Image enemyImage = new ImageIcon("res/zombie.png").getImage();
	Image bloodImage0 = new ImageIcon("res/blood0.png").getImage();
	Image bloodImage1 = new ImageIcon("res/blood1.png").getImage();
	Image grassImage = new ImageIcon("res/grass.png").getImage();
	Image healthpackImage = new ImageIcon("res/healthpack.png").getImage();
	Image bombImage = new ImageIcon("res/bomb.png").getImage();
	Image playerImage = new ImageIcon("res/player.png").getImage();
	Image deadImage = new ImageIcon("res/dead.png").getImage();
	Image crosshairImage = new ImageIcon("res/crosshair.png").getImage();

	ControllerManager controllerManager = new ControllerManager();

	boolean initialized = false;

	public PlayState(Game game) {
		super((EngineX)game);

		this.game = game;

		// Controller Manager
		if(Config.controllerEnabled)
			controllerManager.initSDLGamepad();
	}

	public void init() {

	}

	void postInit() {
		if(!initialized) {
			tStart = getTime();
			player = new Player(game);
			super.addGameObject(player);

			for(int i = 0; i < maxEnemies; i++) {
				addEnemy();
			}
			game.hideDefaultCursor();

			initialized = true;
		}
	}

	void updateBloods() {
		for(Iterator<Blood> it = bloods.iterator(); it.hasNext(); ) {
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
		postInit();

		// Get Controllers Info
		if(Config.controllerEnabled)
			pollControllers();

		if(!paused) {
			if(player.alive) {
				timeSpent++;
				updateEnemies();
				updateBloods();
				updateHealthpacks();
			}
		}

		player.update(paused);
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

	public void pollControllers() {
		int numControllers = controllerManager.getNumControllers();

		for(int i = 0; i < numControllers; i++) {
			ControllerState controller = controllerManager.getState(i);
			if(controller.isConnected && i == 0) {
				player.controllerUpdate(controller);
			}
		}
	}

	void updateHealthpacks() {
		if(healthpackCounter == 0) {
			if(healthpacks.size() == 0) {
				if(player.health < 100) {
					double distance = 999;
					int healthx = 999;
					int healthy = 999;
					while(distance > 200 || distance < (Healthpack.w * 2)) {
						healthx = (int)(Math.random() * game.getWidth());
						healthy = (int)(Math.random() * game.getHeight());
						distance = Math.sqrt(Math.pow(healthx - player.x, 2) + Math.pow(healthy - player.y, 2));
					}

					// X Location
					if(healthx >= game.getWidth() - Healthpack.w) {
						healthx = (game.getWidth() - Healthpack.w * 2) - (Healthpack.w / 2);
					}
					if(healthx <= 0 + Healthpack.w) {
						healthx = Healthpack.w;
					}

					// Y Location
					if(healthy <= 0 + Healthpack.h) {
						healthy = Healthpack.h;
					}
					if(healthy >= game.getHeight() - Healthpack.h * 3) {
						healthy = game.getHeight() - (Healthpack.h * 3);
					}

					healthpacks.add(new Healthpack(game, healthx, healthy));

					System.out.println("Distance between the points is " + distance);
				}
			}
		}

		for(Iterator<Healthpack> it = healthpacks.iterator(); it.hasNext(); ) {
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
		catch(Exception e) {
		}
	}

	void renderHealthpacks(Graphics2D g) {
		for(Healthpack h : healthpacks) {
			h.render(g);
		}
	}

	void renderGrass(Graphics2D g) {
		g.drawImage(grassImage, 0, 0, null);
	}

	void renderText(Graphics2D g) {
		String s;
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
		for(Iterator<Enemy> it = enemies.iterator(); it.hasNext(); ) {
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
		for(Enemy e : enemies) {
			e.render(g);
		}
	}

	void renderBloods(Graphics2D g) {
		for(Blood b : bloods) {
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

	public void resetGame() {
		score = 0;
		player.x = game.width / 2 - player.w / 2;
		player.y = game.height / 2 - player.h / 2;
		player.alive = true;
		player.health = 50;
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

		if(e.getKeyCode() == KeyEvent.VK_F1) {
			game.controllerEnabled = !game.controllerEnabled;
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
