package zombieapocalypse;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;

import enginex.EngineX;
import enginex.GameObject;

@SuppressWarnings("serial")
public class Blood extends GameObject {
	int		v			= 0;
	int		lifeTime	= 1000;
	float	alpha		= 1f;

	Blood(EngineX game, int x, int y) {
		super(game);
		this.x = x;
		this.y = y;
		v = (int)(Math.random() * 50);
	}

	public void update() {
		lifeTime--;

		if(lifeTime < 1000 && lifeTime > 900) {
			alpha = 1.0f;
		}

		if(lifeTime < 900 && lifeTime > 800) {
			alpha = 0.9f;
		}

		if(lifeTime < 800 && lifeTime > 700) {
			alpha = 0.8f;
		}

		if(lifeTime < 700 && lifeTime > 600) {
			alpha = 0.7f;
		}

		if(lifeTime < 600 && lifeTime > 500) {
			alpha = 0.6f;
		}

		if(lifeTime < 500 && lifeTime > 400) {
			alpha = 0.5f;
		}

		if(lifeTime < 400 && lifeTime > 300) {
			alpha = 0.4f;
		}

		if(lifeTime < 300 && lifeTime > 200) {
			alpha = 0.3f;
		}

		if(lifeTime < 200 && lifeTime > 100) {
			alpha = 0.2f;
		}

		if(lifeTime < 100 && lifeTime > 0) {
			alpha = 0.1f;
		}

		if(lifeTime <= 0) {
			alpha = 0.0f;
			this.setDisposable(true);
		}
	}

	public void render(Graphics2D g) {
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		if(v < 25) {
			g.drawImage(((PlayState)getCurrentState()).bloodImage0, (int)x - 2, (int)y - 2, null);
		}
		else {
			g.drawImage(((PlayState)getCurrentState()).bloodImage1, (int)x - 2, (int)y - 2, null);
		}
	}

}
