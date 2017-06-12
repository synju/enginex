package enginex.replicant;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import enginex.core.State;

public class RMenuState extends State {
	Replicants game;
	
	public RMenuState(Replicants game) {
		super(game);
		
		this.game = game;
	}
	
	public void update() {
		
	}
	
	public void render(Graphics2D g) {
		
	}
	
	public void keyPressed(KeyEvent e) {
		// Exit Game...
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) game.exit();
	}
}
