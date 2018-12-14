package towerdefense;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import enginex.State;

public class PlayState extends State {
	TowerDefense	game;
	boolean				initialized	= false;
	
//	ArrayList<Tower> towers;
//	Monster m;
	
//	Little_GUI lg;

	protected PlayState(TowerDefense game) {
		super(game);
		this.game = game;
	}

	public void create() {
		if(initialized)
			return;
		
//		lg = new Little_GUI(game);
		
//		towers = new ArrayList<Tower>();
//		towers.add(new Tower(game, 1*50, 1*50, 50, 50, true));
//		m = new Monster(game, game.width-100,500,100,200);

		initialized = true;
	}

	public void update() {
		create();
		
//		lg.update();
		
//		m.update();
//		for(Tower tower:towers)
//			tower.update();
	}

	public void render(Graphics2D g) {
//		lg.render(g);
		
//		for(Tower tower:towers)
//			tower.render(g);
//		m.render(g);
	}

	public void mousePressed(MouseEvent e) {
//		lg.mousePressed(e);
		
//		for(Tower tower:towers)
//				tower.mousePressed(e);
	}
	
	public void mouseReleased(MouseEvent e) {
//		lg.mouseReleased(e);
		
//		for(Tower tower:towers)
//				tower.mouseReleased(e);
	}
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			game.exit();
		
//		if(e.getKeyCode() == KeyEvent.VK_I)
//			lg.toggleMenu();
	}
}
