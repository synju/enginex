package HEC_Industries.structures;

import HEC_Industries.Block;
import HEC_Industries.Game;

public class CoalDrill extends Structure {
	public static int TYPE = Structure.TYPE_COAL_DRILL;

	public CoalDrill(Game game, int offsetX, int offsetY) {
		super(game, TYPE, offsetX, offsetY);
		this.w = Block.blockWidth * 2;
		this.h = Block.blockHeight * 2;
		this.requiredResource = Block.TYPE_COAL;
		this.placingImage = game.res.coaldrill_placing;
		this.placedImage = game.res.coaldrill_placed;
		this.invalidImage = game.res.coaldrill_invalid;
	}

	public void update() {
		// Required ... Lives in Super
		updatePosition();

		// Work to do..
	}
}
