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

public class PauseState extends State {
	Spaceshooter			game;
	boolean						initialized		= false;
	
	public Controller	joystick;
	
	ArrayList<Button>	buttons				= new ArrayList<>();
	Button						btnResume;
	Button						btnQuitToMenu;
	
	ScrollingBG				spaceBG;
	Image							crosshairImage;
	
	boolean						musicEnabled	= true;
	boolean						musicPlaying	= false;
	
	public PauseState(Spaceshooter game) {
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
		for(Controller c:ControllerEnvironment.getDefaultEnvironment().getControllers()) {
			if(c.getType() == Controller.Type.STICK)
				joystick = c;
		}
	}
	
	public void create() {
		spaceBG = new ScrollingBG(game.res.spaceBG.getPath(), 0.0f, 0, 0, 800, 600);
		
		playMusic();
		
		initControllers();
		createButtons();
	}
	
	public void playMusic() {
		if(musicEnabled)
			if(!musicPlaying) {
				game.res.pauseSong.getSound().playSong();
				musicPlaying = true;
			}
	}
	
	public void stopMusic() {
		game.res.pauseSong.getSound().stop();
		musicPlaying = false;
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
	
	public void createButtons() {
		btnResume = new Button(game, "Resume Game", game.width / 2 - 100, 200, 200, 75, game.res.resumeButton.getPath(), game.res.resumeButton.getPath(), game.res.buttonHoverSound.getPath());
		buttons.add(btnResume);
		
		btnQuitToMenu = new Button(game, "Quit To Menu", game.width / 2 - 100, 300, 200, 75, game.res.quitToMenuButton.getPath(), game.res.quitToMenuButton.getPath(), game.res.buttonHoverSound.getPath());
		buttons.add(btnQuitToMenu);
	}
	
	public void update() {
		initialize();
		joyStickPoll();
		
		// Music
		playMusic();
		
		for(Button b:buttons)
			b.update();
	}
	
	public void render(Graphics2D g) {
		// Smooth Images
		
		spaceBG.render(g);
		
		// Buttons
		for(Button b:buttons)
			b.render(g);
		
		// Cursor
		renderCrosshair(g);
	}
	
	void renderCrosshair(Graphics2D g) {
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
	
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			stopMusic();
			game.stateMachine.setState(game.PLAY);
		}
		
		if(e.getKeyCode() == KeyEvent.VK_Q) {
			stopMusic();
			game.stateMachine.setState(game.MENU);
		}
	}
	
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			// Resume Button Clicked...
			if(btnResume.hover) {
				stopMusic();
				game.stateMachine.setState(game.PLAY);
			}
			
			// Quit To Menu Button Clicked...
			if(btnQuitToMenu.hover) {
				stopMusic();
				game.stateMachine.setState(game.MENU);
			}
		}
	}
	
	public void joyStickPoll() {
		// Update Joystick Input Data
		if(joystick != null) {
			// Poll Joystick Input Data
			joystick.poll();
			
			// Get Components
			// Component[] components = joystick.getComponents();
		}
	}
}
