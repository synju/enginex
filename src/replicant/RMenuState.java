package replicant;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;

import enginex.Sound;
import enginex.State;

public class RMenuState extends State {
	Replicants	game;
	Button		powerButton;

	Sound	sound;
	Button	playButton;
	Button	pauseButton;
	Button	stopButton;

	static final int MUSIC = 0;
	
	Image		bg	= new ImageIcon("res/replicants/bg.jpg").getImage();

	public RMenuState(Replicants game) {
		super(game);
		this.game = game;

		powerButton = new Button(game, game.width - 32 - 10, 0 + 10, 32, 32, "res/replicants/powerOn.png", "res/replicants/powerHover.png", "res/replicants/sfx/buttonHover.ogg");

		playButton = new Button(game, 10, 10, 50, 50, "res/replicants/play.png", "res/replicants/playHover.png");
		pauseButton = new Button(game, 65, 10, 50, 50, "res/replicants/pause.png", "res/replicants/pauseHover.png");
		stopButton = new Button(game, 120, 10, 50, 50, "res/replicants/stop.png", "res/replicants/stopHover.png");

		game.soundMachine.add(new Sound("res/replicants/sfx/doom_e1m8_sign_of_evil.ogg"), MUSIC);
	}

	public void update() {
		powerButton.update();

		playButton.update();
		pauseButton.update();
		stopButton.update();
	}

	public void render(Graphics2D g) {
		// Smooth Images
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		
		g.drawImage(bg, 0, 0, null);
		
		powerButton.render(g);
		playButton.render(g);
		pauseButton.render(g);
		stopButton.render(g);
	}

	public void keyPressed(KeyEvent e) {
		// Exit Game...
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			game.exit();

		if(e.getKeyCode() == KeyEvent.VK_ADD)
			game.soundMachine.increaseVolume(MUSIC);

		if(e.getKeyCode() == KeyEvent.VK_SUBTRACT)
			game.soundMachine.decreaseVolume(MUSIC);

		if(e.getKeyCode() == KeyEvent.VK_SPACE)
			game.soundMachine.pause(MUSIC);

		if(e.getKeyCode() == KeyEvent.VK_ENTER)
			game.soundMachine.play(MUSIC);
	}

	public void mousePressed(MouseEvent e) {
		Point m = game.getMousePosition();

		if(playButton.contains(m))
			game.soundMachine.play(MUSIC);

		if(pauseButton.contains(m))
			game.soundMachine.pause(MUSIC);

		if(stopButton.contains(m))
			game.soundMachine.stop(MUSIC);

		if(powerButton.contains(m))
			game.exit();
	}
}
