package flowershop;

import java.awt.Graphics2D;

import enginex.State;

public class BaseState extends State {
	Game game;

	protected BaseState(Game game) {
		super(game);
		this.game = game;
	}

	public void initialize() {
		if(initialized)
			return;

		create();

		initialized = true;
	}

	public void create() {

	}

	public void update() {

	}

	public void render(Graphics2D g) {

	}
}
