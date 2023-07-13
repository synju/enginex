package Archive.miniworld_top_down_potential;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import EngineX.Animation;
import EngineX.GameObject;

@SuppressWarnings("serial")
public class Tile extends GameObject {
	Game game;
	
	Animation				tileAnimation;
	Animation				currentAnimation;

	public Tile(Game game, int x, int y, Animation animation) {
		super(game);
		this.game = game;
		
		this.x = x;
		this.y = y;
		
		this.currentAnimation = animation;
	}
	
	public void update() {
		currentAnimation.update();
	}
	
	public void render(Graphics2D g) {
		AffineTransform at = new AffineTransform();
		at.scale(game.scale, game.scale);
		at.translate((x/game.scale), (y/game.scale));
		g.drawImage(currentAnimation.getSprite(), at, null);
	}
}	
