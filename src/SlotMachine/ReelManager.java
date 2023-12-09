package SlotMachine;

import java.awt.*;
import java.util.ArrayList;

public class ReelManager {
	Game        game;
	SlotMachine slotMachine;
	int         originX;
	int         originY;
	int         x;
	int         y;
	int         width;
	int         height;
	int         symbol_width;
	int         symbol_height;

	// Reels
	ArrayList<Reel> reels = new ArrayList<>();

	ReelManager(Game game, SlotMachine slotMachine) {
		this.game = game;
		this.slotMachine = slotMachine;
		this.x = 330;
		this.y = 194;
		this.width = 700;
		this.height = 380;
		this.symbol_width = 140;
		this.symbol_height = 95;

		reels.add(new Reel(game, this, x + (0 * Symbol.WIDTH), y + (0 * Symbol.HEIGHT)));
		reels.add(new Reel(game, this, x + (1 * Symbol.WIDTH), y + (0 * Symbol.HEIGHT)));
		reels.add(new Reel(game, this, x + (2 * Symbol.WIDTH), y + (0 * Symbol.HEIGHT)));
		reels.add(new Reel(game, this, x + (3 * Symbol.WIDTH), y + (0 * Symbol.HEIGHT)));
		reels.add(new Reel(game, this, x + (4 * Symbol.WIDTH), y + (0 * Symbol.HEIGHT)));
	}

	public void update() {
		for(Reel reel : reels) {
			reel.update();
		}
	}

	public void render(Graphics2D g) {
		for(Reel reel : reels) {
			reel.render(g);
		}
	}
}
