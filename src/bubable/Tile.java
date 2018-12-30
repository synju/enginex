package bubable;

import java.awt.*;

public class Tile {
	// Types
	static final int EMPTY = 0;
	static final int WALL = 1;

	// Game
	private Game game;

	// Properties
	int x, y, type, assignment;

	// Constructor
	Tile(Game game, int x, int y, int type, int assignment) {
		this.game = game;
		this.x = x;
		this.y = y;
		this.type = type;
		this.assignment = assignment;
	}

	// Render
	public void render(Graphics2D g) {
		if(type == WALL)
			g.drawImage(((PlayState) (game.stateMachine.getCurrentState())).resources.wallImages[assignment], x*256, y*256, null);
	}
}
