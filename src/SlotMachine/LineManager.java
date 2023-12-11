package SlotMachine;

import java.awt.*;
import java.util.ArrayList;

public class LineManager {
	Game        game;
	SlotMachine slotMachine;
	int         x;
	int         y;
	int         width;
	int         height;
	int         symbol_width;
	int         symbol_height;

	LineManager(Game game, SlotMachine slotMachine) {
		this.game = game;
		this.slotMachine = slotMachine;
		this.x = 330;
		this.y = 194;
		this.width = 700;
		this.height = 380;
		this.symbol_width = Config.symbolWidth;
		this.symbol_height = Config.symbolHeight;
	}

	public void update() {
		if(slotMachine.renderLines) {

		}
	}

	public void render(Graphics2D g) {
		if(slotMachine.renderLines) {

		}
	}
}
