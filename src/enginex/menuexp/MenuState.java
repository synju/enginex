package enginex.menuexp;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;

import enginex.core.Button;
import enginex.core.EngineX;
import enginex.core.Sound;
import enginex.core.SoundMachine;
import enginex.core.State;

public class MenuState extends State {
	Image		blue_sky;
	Button	playButton;
	
	public MenuState(EngineX game) {
		super(game);
	}
	
	public void init() {
		blue_sky = new ImageIcon("res/blue_sky.png").getImage();
		playButton = new Button(game, game.getWidth() / 2 - 400 / 2, game.getHeight() / 2 - 144 / 2, 400, 144, "res/upButton.png", "res/hoverButton.png");
		
		new Thread(()-> {
			game.soundMachine.add(new Sound("res/replicants/sfx/doom_e1m8_sign_of_evil.ogg"), SoundMachine.MUSIC);
		}).start();
	}
	
	public void update() {
		playButton.update();
	}
	
	public void render(Graphics2D g) {
		g.drawImage(blue_sky, 0, 0, null);
		playButton.render(g);
	}
	
	public void mousePressed(MouseEvent e) {
		super.mousePressed(e);
		
		if(playButton.containsMouse())
			game.soundMachine.play(SoundMachine.MUSIC);
	}
}
