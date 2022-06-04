package Archive.critters;

import EngineX.State;
import EngineX.Util;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class PlayState extends State {
	Game game;
	Map map;
	Critter critter;

	boolean leftPointerDown = false;
	boolean rightPointerDown = false;

	protected PlayState(Game game) {
		super(game);
		this.game = game;
	}

	public void init() {
		if(initialized)
			return;

		create();

		initialized = true;
	}

	public void create() {
		map = new Map(game);
		map.generateMap(10, 10, 32);
		critter = new Critter(0, 0, 32, 32, map, game);
	}

	public void update() {
		init();

		Point mousePosition = game.getMousePosition();
		map.update(mousePosition);
		critter.update();
	}

	public void render(Graphics2D g) {
		Util.drawText(8, 34, "Play", 32, Color.WHITE, g);
		map.render(g);
		critter.render(g);
	}

	public void keyPressed(KeyEvent e) {
		map.keyPressed(e);

		if(e.getKeyCode() == KeyEvent.VK_SPACE)
			game.stateMachine.setState(game.MENU);

		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			game.stateMachine.setState(game.MENU);
	}

	public void keyReleased(KeyEvent e) {
		map.keyReleased(e);
	}

	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			critter.mousePressed(e);
			leftPointerDown = true;
		}

		if(e.getButton() == MouseEvent.BUTTON3)
			rightPointerDown = true;
	}

	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			critter.mouseReleased(e);
			leftPointerDown = false;
		}

		if(e.getButton() == MouseEvent.BUTTON3)
			rightPointerDown = false;
	}
}
