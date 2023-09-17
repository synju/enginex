package HEC_Industries;

import EngineX.GameObject;

import java.awt.*;

public class Block extends GameObject {
	Game game;

	public static char TYPE_EMPTY  = '.';
	public static char TYPE_DIRT   = 'D';
	public static char TYPE_IRON   = 'I';
	public static char TYPE_COAL   = 'C';
	public static char TYPE_COPPER = '^';

	public static int blockWidth  = 64;
	public static int blockHeight = 64;

	int     offsetX;
	int     offsetY;
	char    blockType;
	boolean isResource = false;
	boolean isHidden   = false;
	int     condition  = 100;
	boolean drawInfo   = false;
	boolean selected   = false;

	Block(Game game, int offsetX, int offsetY, char blockType) {
		super(game);

		this.game = game;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.x = game.ps.levelHandler.worldX + offsetX;
		this.y = game.ps.levelHandler.worldY + offsetY;
		this.w = blockWidth;
		this.h = blockHeight;
		this.blockType = blockType;
		if(blockType != Block.TYPE_EMPTY) {
			if(blockType != Block.TYPE_DIRT) {
				isResource = true;
			}
			isHidden = true;
		}
		else {
			condition = 0;
		}
	}

	public void update() {
		x = game.ps.levelHandler.worldX + offsetX;
		y = game.ps.levelHandler.worldY + offsetY;

		// Check if contains playerCursor or not.
		selected = (containsEntity(game.ps.levelHandler.player.playerCursor.x, game.ps.levelHandler.player.playerCursor.y, game.ps.levelHandler.player.playerCursor.w, game.ps.levelHandler.player.playerCursor.h));
	}

	public void render(Graphics2D g) {
		if((this.x > (0 - this.w)) && ((this.x + this.w) < (game.width + this.w))) {
			if((this.y > (0 - this.h)) && ((this.y + this.h) < (game.height + this.h))) {
				if(!isHidden) {
					if(blockType == Block.TYPE_EMPTY) {
						g.drawImage(game.res.block_dirt_dark.getImage(), (int)x, (int)y, null);
					}
					if(blockType == Block.TYPE_DIRT) {
						g.drawImage(game.res.block_dirt_dark.getImage(), (int)x, (int)y, null);
					}
					if(blockType == Block.TYPE_IRON) {
						g.drawImage(game.res.block_iron.getImage(), (int)x, (int)y, null);
					}
					if(blockType == Block.TYPE_COPPER) {
						g.drawImage(game.res.block_copper.getImage(), (int)x, (int)y, null);
					}
					if(blockType == Block.TYPE_COAL) {
						g.drawImage(game.res.block_coal.getImage(), (int)x, (int)y, null);
					}
				}
				else {
					// its hidden.. just draw dirt
					g.drawImage(game.res.block_dirt.getImage(), (int)x, (int)y, null);

					if(condition < 100 && condition > 67) {
						g.drawImage(game.res.block_crack1.getImage(), (int)x, (int)y, null);
					}
					if(condition < 67 && condition > 35) {
						g.drawImage(game.res.block_crack2.getImage(), (int)x, (int)y, null);
					}
					if(condition <= 35) {
						g.drawImage(game.res.block_crack3.getImage(), (int)x, (int)y, null);
					}
				}

				// Selected
				if(selected && !game.ps.levelHandler.player.renderPlacingCursor) {
					if(condition <= 0 && game.ps.levelHandler.player.selectedAction == game.ps.levelHandler.player.DIRT) {
						g.drawImage(game.res.block_dirt.getImage(), (int)x, (int)y, null);
					}

					g.setColor(new Color(255, 255, 255, 50));
					g.drawRect((int)x, (int)y, (int)w - 1, (int)h - 1);
				}

				// Text...
				if(drawInfo) {
					g.setColor(Color.white);

					// condition
					g.drawString(String.valueOf(condition), (int)(x + w / 3), (int)(y + h / 3));

					// isHidden
					if(isHidden) {
						g.drawString("hidden", (int)(x + w / 3), (int)(y + h / 3) + 15);
					}
					else {
						g.drawString("not hidden", (int)(x + w / 3), (int)(y + h / 3) + 15);
					}

					// type
					g.drawString(String.valueOf(blockType), (int)(x + w / 3), (int)(y + h / 3) + 30);
				}
			}
		}
	}

	public boolean containsEntity(int ex, int ey, int ew, int eh) {
		int e_center_x = ex + (ew / 2);
		int e_center_y = ey + (eh / 2);

		int block_x = (int)this.x;
		int block_y = (int)this.y;
		int block_w = (int)this.w;
		int block_h = (int)this.h;

		return (e_center_x >= block_x && e_center_x <= block_x + block_w) && (e_center_y >= block_y && e_center_y <= block_y + block_h);
	}
}
