package moo;

import enginex.State;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class PlayState extends State {
	Game game;
	boolean initialized = false;
	Player p;
	ArrayList<Collidable> clist = new ArrayList<>();

	public PlayState(Game game) {
		super(game);
		this.game = game;
	}

	public void postInit() {
		// Check if initialized
		if(initialized)
			return;

		// Initialize Stuff Here...
		p = new Player(game);

		// Floor
		clist.add(new Collidable(game, 0*32,16*32, 25*32, 32));

		// Ceiling
		clist.add(new Collidable(game, 0*32,0*32, 25*32, 32));

		// Walls
		clist.add(new Collidable(game, 0*32,1*32, 1*32, 15*32));
		clist.add(new Collidable(game, 24*32,1*32, 1*32, 15*32));

		// Platforms
		clist.add(new Collidable(game, 15*32,13*32, 2*32, 32));
		clist.add(new Collidable(game, 11*32,11*32, 2*32, 32));
		clist.add(new Collidable(game, 15*32,8*32, 2*32, 32));
		clist.add(new Collidable(game, 10*32,5*32, 2*32, 32));
		clist.add(new Collidable(game, 5*32,5*32, 2*32, 32));
		clist.add(new Collidable(game, 2*32,10*32, 2*32, 32));

		// Complete initialization
		initialized = true;
	}

	public void update() {
		// This is Run Only Once...
		postInit();

		// Update Player...
		p.update();
	}

	public void render(Graphics2D g) {
		p.render(g);

		for(Collidable c : clist) {
			c.render(g);
		}
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			game.exit();

		p.keyPressed(e);
	}

	public void keyReleased(KeyEvent e) {
		p.keyReleased(e);
	}

	public void mousePressed(MouseEvent e) {
		p.mousePressed(e);
	}

	public void mouseReleased(MouseEvent e) {
		p.mouseReleased(e);
	}
}
