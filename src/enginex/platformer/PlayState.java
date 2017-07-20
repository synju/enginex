package enginex.platformer;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import enginex.core.State;

public class PlayState extends State {
	public Platformer							game;
	public Player									p;
	public LevGen									lg;
	public ArrayList<Collidable>	clist;
	
	boolean												initialized	= false;
	
	public PlayState(Platformer game) {
		super(game);
		this.game = game;
		clist = new ArrayList<>();
		lg = new LevGen(game);
	}
	
	public void postInit() {
		lg.generateLevel();
		initialized = true;
	}
	
	public void update() {
		if(!initialized)
			postInit();
		
		try {
			p.update();
		}
		catch(Exception e) {}
	}
	
	public void render(Graphics2D g) {
		try {
			for(Collidable c:clist)
				c.render(g);
			p.render(g);
		}
		catch(Exception e) {}
	}
	
	public void keyPressed(KeyEvent e) {
		try {
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
				System.out.println("Game Loaded");
				p.load();
			}
			
			// Reset Game
			if(e.getKeyCode() == KeyEvent.VK_R) {
				lg.generateLevel();
			}
			
			p.keyPressed(e);
		}
		catch(Exception ex) {}
	}
	
	public void keyReleased(KeyEvent e) {
		try {
			p.keyReleased(e);
		}
		catch(Exception ex) {}
	}
}
