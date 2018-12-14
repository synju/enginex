package platformer;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import enginex.State;

@SuppressWarnings(value = { "all" })
public class PlayState extends State {
	Platformer						game;
	Player								p;
	public ArrayList<Collidable>	clist;
	LevelGenerator				lg;

	// Stage Origin
	int										ox	= 0;
	int										oy	= 0;
	
	boolean initialized = false;

	protected PlayState(Platformer game) {
		super(game);
		this.game = game;
	}
	
	public void postInit() {
		if(!initialized) {
			lg = new LevelGenerator(game);
			lg.generateLevel();
			initialized = true;
		}
	}

	public void update() {
		postInit();
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

		// Save Game
		if(e.getKeyCode() == KeyEvent.VK_F5) {
			p.save();
			System.out.println("Game Saved");
		}

		// Load Game
		if(e.getKeyCode() == KeyEvent.VK_F6) {
			p.load();
			System.out.println("Game Loaded");
		}

		// Reset Game
		if(e.getKeyCode() == KeyEvent.VK_R) {
			lg.generateLevel();
			System.out.println("Game Reset");
		}

		p.keyPressed(e);
	}

	public void keyReleased(KeyEvent e) {
		p.keyReleased(e);
	}
}
