package enginex.replicant;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import enginex.core.State;

public class RMenuState extends State {
	Replicants				game;
	Button						powerButton;
	
	static final int	WIDTH		= 1280;
	static final int	HEIGHT	= 720;
	
	public RMenuState(Replicants game) {
		super(game);
		this.game = game;
		powerButton = new Button(game, game.width - 32 - 10, 0 + 10, 32, 32, "res/replicants/powerOn.png", "res/replicants/powerHover.png", "res/replicants/sfx/buttonHover.ogg");
	}
	
	public void update() {
		powerButton.update();
	}
	
	public void render(Graphics2D g) {
		powerButton.render(g);
	}
	
	public void keyPressed(KeyEvent e) {
		// Exit Game...
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			game.exit();
	}
}
