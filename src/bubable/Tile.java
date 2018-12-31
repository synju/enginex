package bubable;

import java.awt.*;
import java.awt.geom.AffineTransform;

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
		try {
			if(type == WALL) {
				AffineTransform at = new AffineTransform();
				at.scale(game.scale, game.scale);
				at.translate(x*8, y*8);
				g.drawImage(((PlayState) (game.stateMachine.getCurrentState())).resources.dungeonSet[assignment], at, null);

//				g.drawImage(((PlayState) (game.stateMachine.getCurrentState())).resources.dungeonSet[assignment], (int)(x * 8 * game.scale), (int)(y * 8 * game.scale), null);
			}
		}
		catch(Exception e) {
//			e.printStackTrace();
		}
	}
}
