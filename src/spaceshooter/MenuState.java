package spaceshooter;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import enginex.Button;
import enginex.State;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;

public class MenuState extends State {
	Spaceshooter			game;
	boolean						initialized		= false;
	
	public Controller	joystick;
	
	ArrayList<Button>	buttons				= new ArrayList<>();
	Button						btnPlay;
	Button						btnQuit;
	
	ScrollingBG				spaceBG;
	Image							logoImage;
	
	boolean						musicEnabled	= true;
	boolean						musicPlaying	= false;
	
	public MenuState(Spaceshooter game) {
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
		spaceBG = new ScrollingBG(game.res.spaceBG.getPath(), 0.0f, 0, 0, 800, 600);
		logoImage = game.res.logo.getImage();
		
		playMusic();
		
		game.hideDefaultCursor();
		initControllers();
		createButtons();
	}
	
	public void createButtons() {
		btnPlay = new Button(game, "Play Game", game.width / 2 - 100, 200, 200, 75, game.res.playButton.getPath(), game.res.playButton.getPath(), game.res.buttonHoverSound.getPath());
		buttons.add(btnPlay);
		
		btnQuit = new Button(game, "Quit Game", game.width / 2 - 100, 300, 200, 75, game.res.quitButton.getPath(), game.res.quitButton.getPath(), game.res.buttonHoverSound.getPath());
		buttons.add(btnQuit);
	}
	
	public void toggleMusic() {
		if(musicEnabled) {
			musicEnabled = false;
			stopMusic();
		}
		else {
			musicEnabled = true;
			playMusic();
		}
	}
	
	public void update() {
		initialize();
		joyStickPoll();
		
		playMusic();
		
		for(Button b:buttons)
			b.update();
	}
	
	public void playMusic() {
		if(musicEnabled)
			if(!musicPlaying) {
				musicPlaying = true;
				game.res.menuSong.getSound().playSong();
			}
	}
	
	public void stopMusic() {
		musicPlaying = false;
		game.res.menuSong.getSound().stop();
	}
	
	public void render(Graphics2D g) {
		// Background
		spaceBG.render(g);
		
		// Logo
		g.drawImage(logoImage, 10, 100, null);
		
		// Buttons
		for(Button b:buttons)
			b.render(g);
		
		// Cursor
		renderCrosshair(g);
	}
	
	public void renderCrosshair(Graphics2D g) {
		try {
			Point p = game.getMousePosition();
			g.drawImage(game.res.crosshair.getImage(), (int)p.getX() - 10, (int)p.getY() - 10, null);
		}
		catch(Exception e) {}
	}
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_M)
			toggleMusic();
	}
	
	public void keyReleased(KeyEvent e) {}
	
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			// Play Button Clicked...
			if(btnPlay.hover) {
				stopMusic();
				game.playState = new PlayState(game);
				game.stateMachine.states.set(game.PLAY, game.playState);
				game.stateMachine.states.get(game.PLAY).init();
				game.stateMachine.setState(game.PLAY);
			}
			
			// Quit Button Clicked...
			if(btnQuit.hover)
				game.exit();
		}
	}
	
	public void joyStickPoll() {
		// Update Joystick Input Data
		if(joystick != null) {
			// Poll Joystick for updates
			joystick.poll();
			
			// Get Components
			// Component[] components = joystick.getComponents();
		}
	}
}
