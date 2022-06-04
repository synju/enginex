package Complete.megamoney;

import EngineX.GameObject;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class ActionPanel extends GameObject {
	Game game;

	public static int DOODAD 			= 1001;
	public static int JOB 				= 1002;
	public static int OPPORTUNITY = 1003;
	public static int CHARITY 		= 1004;
	public static int GIFT 				= 1005;
	public static int PAYMENT 		= 1006;

	int x = 0;
	int y = 0;
	int w = 0;
	int h = 0;

	boolean visible = false;

	public ActionPanel(Game game, int width, int height) {
		super(game);
		this.game = game;

		this.w = width;
		this.h = height;
		this.x = (game.getWidth()/2) - width /2;
		this.y = 100;
	}

	public void update() {}

	public void render(Graphics2D g) {}

	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getWidth() {
		return w;
	}

	public int getHeight() {
		return h;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public void closeActionPanel() {
		game.ps.p.actionPanelActive = false;
		game.ps.p.currentActionPanel.setVisible(false);
		game.ps.p.spinButtonEnabled = true;
		game.ps.p.step();
	}

	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_C) {
			if(visible) {
				closeActionPanel();
			}
		}
	}
}
