package enginex.platformer;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import enginex.core.State;

public class PlayState extends State {
	Platformer game;
	Player p;
	ArrayList<Collidable> clist = new ArrayList<>();
	
	protected PlayState(Platformer game) {
		super(game);
		this.game = game;
		
		clist.add(new Collidable(game, 100, 400, game.width-200, 50));
		p = new Player(game,150,200);
	}
	
	public void update() {
		p.update();
	}
	
	public void render(Graphics2D g) {
		for(Collidable c:clist)
			c.render(g);
		p.render(g);
	}
	
	public void keyPressed(KeyEvent e) {
		// Exit Game...
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			game.exit();
		
		p.keyPressed(e);
	}
	
	public void keyReleased(KeyEvent e) {
		p.keyReleased(e);
	}
}
