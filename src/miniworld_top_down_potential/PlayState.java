package miniworld_top_down_potential;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import enginex.State;

public class PlayState extends State {
	MiniWorld game;
	public ArrayList<Collidable> clist = new ArrayList<>();
	Player p;
	double gx = 0;
	double gy = 0;
	public ArrayList<Tile> tiles = new ArrayList<>();
	
	NoiseGenerator ng;
	
	public PlayState(MiniWorld game) {
		super(game);
		this.game = game;
	}
	
	public void postInit() {
		if(initialized)
			return;
		
		p = new Player(game,3*((int)game.scale*32),3*((int)game.scale*32));
		
		for(int y = 0; y < 7; y++)
			for(int x = 0; x < 7; x++)
				tiles.add(new Tile(game, x*(32*(int)game.scale), y*(32*(int)game.scale), AnimationTextures.GRASS));
		
		ng = new NoiseGenerator(game);
		
		initialized = true;
	}
	
	public void update() {
		postInit();
		
		p.update();
		ng.update();
	}
	
	public void render(Graphics2D g) {
		for(Tile tile:tiles)
			tile.render(g);
		
		p.render(g);
//		ng.render(g);
	}
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			game.exit();
		
		p.keyPressed(e);
	}
	
	public void keyReleased(KeyEvent e) {
		p.keyReleased(e);
	}
}
