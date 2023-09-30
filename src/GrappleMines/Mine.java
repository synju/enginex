package GrappleMines;

import java.awt.*;

public class Mine extends Collidable {
	boolean containsMouse = false;
	boolean isLive = false;

	Mine(Game game, int offsetX, int offsetY, int width, int height) {
		super(game, offsetX, offsetY, width, height);
	}

	void postInit() {
		// Check if initialized
		if(initialized)
			return;

		// Initialization...

		// Complete initialization
		initialized = true;
	}

	public void update() {
		// This is Run Only Once...
		postInit();

		x = game.ps.worldX + offsetX;
		y = game.ps.worldY + offsetY;

		containsMouse();
	}

	public void containsMouse() {
		// Strict Mines...
//		Rectangle rectA = new Rectangle(x, y, w, h);
//		Rectangle rectB = new Rectangle(game.ps.mousePoint.x+game.ps.worldX, game.ps.mousePoint.y+game.ps.worldY, 1, 1);

		// Clickable Area Around Mine is Much Bigger
		Rectangle rectA = new Rectangle(x-w, y-h, w*3, h*3);
		Rectangle rectB = new Rectangle(game.ps.mousePoint.x+game.ps.worldX, game.ps.mousePoint.y+game.ps.worldY, 1, 1);

		// Update containsMouse boolean...
		containsMouse = rectA.contains(rectB);
	}

	public void render(Graphics2D g) {
		if(containsMouse && !game.ps.menuDisplay.visible && !game.ps.gameOverDisplay.visible) {
			g.setColor(new Color(1,0,0,0.08f));
			g.fillOval(x-w,y-h,w*3,h*3);
		}

		if(isLive) {
			g.setColor(Color.yellow);
		}
		else {
			g.setColor(Color.red);
		}


		g.fillOval(x,y,w,h);
	}
}
