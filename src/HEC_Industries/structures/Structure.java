package HEC_Industries.structures;


import EngineX.GameObject;
import EngineX.Resource;
import HEC_Industries.Block;
import HEC_Industries.Game;

import java.awt.*;

public class Structure extends GameObject {
	public static int STATE_PLACING = 0;
	public static int STATE_PLACED  = 1;
	public static int STATE_INVALID = 2;

	public static int TYPE_COAL_DRILL = 0;

	public int x, y, w, h, offsetX, offsetY;
	public boolean renderMarker = false;
	public boolean canPlace     = false;

	Game game;
	public int type;
	public int state            = Structure.STATE_PLACING;
	public int requiredResource = Block.TYPE_EMPTY;

	Resource placingImage;
	Resource placedImage;
	Resource invalidImage;

	public Structure(Game game, int type, int offsetX, int offsetY) {
		super(game);
		this.game = game;
		this.type = type;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.x = game.ps.levelHandler.worldX + offsetX;
		this.y = game.ps.levelHandler.worldY + offsetY;
	}

	public void setState(int state) {
		this.state = state;
	}

	public void update() {
		updatePosition();
	}

	public void updatePosition() {
		if(state == Structure.STATE_PLACING) {
			this.x = game.ps.levelHandler.player.placingCursor.x;
			this.y = game.ps.levelHandler.player.placingCursor.y - this.h;
		}
		else {
			this.x = game.ps.levelHandler.worldX + offsetX;
			this.y = game.ps.levelHandler.worldY + offsetY - this.h;
		}
	}

	public void render(Graphics2D g) {
		if(state == Structure.STATE_PLACING) {
			if(canPlace) {
				g.drawImage(placingImage.getImage(), x, y, null);
			}
			else {
				g.drawImage(invalidImage.getImage(), x, y, null);
			}
		}

		if(state == Structure.STATE_PLACED) {
			g.drawImage(placedImage.getImage(), x, y, null);
		}

		if(state == Structure.STATE_INVALID) {
			g.drawImage(invalidImage.getImage(), x, y, null);
		}

		if(renderMarker) {
			g.setColor(Color.BLUE);
			g.fillRect(x, y , w, h);
			System.out.println(canPlace);
		}
	}
}
