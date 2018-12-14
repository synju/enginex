package spaceshooter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import enginex.State;
import enginex.Util;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

public class PlayState extends State {
	Spaceshooter			game;
	boolean						initialized			= false;
	
	public Controller	joystick;
	boolean						joystickEnabled	= true;
	
	Player						player;
	WaveHandler				waveHandler;
	
	ScrollingBG				spaceBG;
	
	boolean						gameOver				= false;
	boolean						levelComplete		= false;
	
	boolean						musicEnabled		= true;
	boolean						musicPlaying		= false;
	
	public PlayState(Spaceshooter game) {
		super(game);
		this.game = game;
	}
	
	public void initialize() {
		if(initialized)
			return;
		
		create();
		
		initialized = true;
	}
	
	void initControllers() {
		for(Controller c:ControllerEnvironment.getDefaultEnvironment().getControllers())
			if(c.getType() == Controller.Type.STICK)
				joystick = c;
	}
	
	public void create() {
		spaceBG = new ScrollingBG(game.res.spaceBG.getPath(), 0.5f, 0, 0, 800, 600);
		playMusic();
		initControllers();
		gameOver = false;
		player = new Player(game, game.width / 2 - Player.WIDTH / 2, (game.height - Player.HEIGHT * 2) - 75);
		waveHandler = new WaveHandler(game);
	}
	
	public void playMusic() {
		if(musicEnabled)
			if(!musicPlaying) {
				game.res.playSong.getSound().stop();
				musicPlaying = true;
				game.res.playSong.getSound().playSong();
			}
	}
	
	public void stopMusic() {
		musicPlaying = false;
		game.res.playSong.getSound().stop();
	}
	
	public void toggleMusic() {
		if(musicEnabled) {
			stopMusic();
			musicEnabled = false;
		}
		else {
			playMusic();
			musicEnabled = true;
		}
	}
	
	public void update() {
		initialize();
		joyStickPoll();
		
		playMusic();
		
		spaceBG.update();
		
		if(!gameOver && !levelComplete) {
			player.update();
			waveHandler.update();
		}
	}
	
	public void gameOver() {
		game.stateMachine.setState(game.MENU);
	}
	
	public void reset() {
		create();
	}
	
	public void render(Graphics2D g) {
		// Smooth Images
		try {
			spaceBG.render(g);
			
			if(!gameOver && !levelComplete) {
				player.render(g);
				waveHandler.render(g);
			}
			else {
				if(gameOver && !levelComplete)
					renderGameOver(g);
				else if(!gameOver && levelComplete)
					renderLevelComplete(g);
				else
					setMenuState();
			}
		}
		catch(Exception e) {}
	}
	
	public void setMenuState() {
		stopMusic();
		game.menuState = new MenuState(game);
		game.stateMachine.states.set(game.MENU, game.menuState);
		game.stateMachine.states.get(game.MENU).init();
		game.stateMachine.setState(game.MENU);
	}
	
	public void setPlayState() {
		game.playState = new PlayState(game);
		game.stateMachine.states.set(game.PLAY, game.playState);
		game.stateMachine.states.get(game.PLAY).init();
		game.stateMachine.setState(game.PLAY);
	}
	
	public void renderLevelComplete(Graphics2D g) {
		// Level Complete Graphic
		g.drawImage(game.res.levelCompleteImage.getImage(), 0, 0, null);
	}
	
	public void renderGameOver(Graphics2D g) {
		// Game Over Graphic
		g.drawImage(game.res.gameOverImage.getImage(), (int)game.width / 2 - 200, 100, null);
		
		// Draw Score
		Util.drawText(250, 295, "Score: " + Integer.toString(player.currentScore), 32, Color.WHITE, g);
		
		// Draw Score
		Util.drawText(190, 350, "Press Enter To Go To Menu", 32, Color.WHITE, g);
	}
	
	public void keyPressed(KeyEvent e) {
		if(!gameOver && !levelComplete)
			player.keyPressed(e);
		
		if(e.getKeyCode() == KeyEvent.VK_M)
			toggleMusic();
	}
	
	public void keyReleased(KeyEvent e) {
		if(!gameOver && !levelComplete) {
			if(e.getKeyCode() == KeyEvent.VK_R) {
				setPlayState();
			}
			
			if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				stopMusic();
				game.stateMachine.setState(game.PAUSE);
			}
			
			player.keyReleased(e);
		}
		else {
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				if(!gameOver && levelComplete) {
					musicEnabled = true;
					waveHandler.increaseLevel();
				}
				else if(gameOver && !levelComplete)
					setMenuState();
				else
					setMenuState();
			}
		}
	}
	
	public void mousePressed(MouseEvent e) {
		if(!gameOver && !levelComplete)
			player.mousePressed(e);
	}
	
	public void mouseReleased(MouseEvent e) {
		try {
			if(!gameOver && !levelComplete)
				player.mouseReleased(e);
		}
		catch(Exception ev) {}
	}
	
	public void joyStickPoll() {
		if(!gameOver && !levelComplete) {
			// Update Joystick Input Data
			if(joystick != null) {
				// Poll Joystick for updates
				joystick.poll();
				
				// Update Player
				if(joystickEnabled) {
					player.joystickPoll(joystick.getComponents());
				}
			}
		}
	}
}
