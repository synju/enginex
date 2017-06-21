package enginex.replicant;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import enginex.core.Sound;
import enginex.core.State;

public class RMenuState extends State {
	Replicants				game;
	Button						powerButton;
	
	Sound							sound;
	Button						playButton;
	Button						pauseButton;
	Button						stopButton;
	
	static final int	WIDTH		= 1280;
	static final int	HEIGHT	= 720;
	
	public RMenuState(Replicants game) {
		super(game);
		this.game = game;
		powerButton = new Button(game, game.width - 32 - 10, 0 + 10, 32, 32, "res/replicants/powerOn.png", "res/replicants/powerHover.png", "res/replicants/sfx/buttonHover.ogg");
		
		sound = new Sound("res/replicants/sfx/doom_e1m8_sign_of_evil.ogg");
		playButton = new Button(game, 10, 10, 50, 50, "res/replicants/play.png", "res/replicants/playHover.png");
		pauseButton = new Button(game, 65, 10, 50, 50, "res/replicants/pause.png", "res/replicants/pauseHover.png");
		stopButton = new Button(game, 120, 10, 50, 50, "res/replicants/stop.png", "res/replicants/stopHover.png");
	}
	
	public void update() {
		powerButton.update();
		
		playButton.update();
		pauseButton.update();
		stopButton.update();
	}
	
	public void render(Graphics2D g) {
		powerButton.render(g);
		playButton.render(g);
		pauseButton.render(g);
		stopButton.render(g);
	}
	
	public void keyPressed(KeyEvent e) {
		// Exit Game...
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			game.exit();
	}
	
	public void mousePressed(MouseEvent e) {
		Point m = game.getMousePosition();
		
		if(playButton.contains(m))
			sound.play();
		
		if(pauseButton.contains(m))
			sound.pause();
		
		if(stopButton.contains(m))
			sound.stop();
	}
}
