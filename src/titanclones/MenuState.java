package titanclones;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import enginex.State;
import enginex.Util;

public class MenuState extends State {
	TitanClones	game;
	boolean		initialized	= false;

	int selection = 0;

	public MenuState(TitanClones game) {
		super(game);
		this.game = game;
	}

	public void render(Graphics2D g) {
		int wspace = 80;
		int hspace = 80;
		Util.drawText("TITANCLONES", 50, 100, 60, g);
		Util.drawText("PLAY", wspace + 50, hspace + 100, 25, g);
		Util.drawText("QUIT", wspace + 50, hspace + 140, 25, g);

		g.setColor(Color.RED);
		if(selection == 0)
			g.fillRect(wspace + 30, hspace + 86, 10, 10);
		else
			g.fillRect(wspace + 30, hspace + 125, 10, 10);
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			game.exit();
		}

		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(selection == 0)
				game.stateMachine.setState(game.PLAY);
			else
				game.exit();
		}

		if(e.getKeyCode() == KeyEvent.VK_UP) {
			if(selection == 0)
				selection = 1;
			else
				selection = 0;
		}

		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			if(selection == 0)
				selection = 1;
			else
				selection = 0;
		}
	}
}
