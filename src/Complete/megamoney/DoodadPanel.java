package Complete.megamoney;

import EngineX.Button;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class DoodadPanel extends ActionPanel {
	Game game;

	// Images
	Image doodadImage = new ImageIcon("res/megamoney/doodad_actionPanel.png").getImage();

	// Buttons
	Button amButton; // Available Money Button
	Button ccButton; // Credit Card Button

	// Properties
	String description = "";
	double cost = 0.00;

	public DoodadPanel(Game game, int width, int height) {
		super(game, width, height);
		this.game = game;

		amButton = new Button(game, (x + 15), ((y + h) - (55+15)), 201, 55, "res/megamoney/useAvailableMoney.png", "res/megamoney/useAvailableMoney.png");
		amButton.setOffsets(2,2);
		ccButton = new Button(game, ((x + w) - (201 + 15)), ((y + h) - (55+15)), 201, 55, "res/megamoney/useCreditCard.png", "res/megamoney/useCreditCard.png");
		ccButton.setOffsets(2,2);
	}

	public void generate() {
		int result = Util.generateRandom(0,5);
		if(result == 0) {
			description = "Lunch with Friends!";
			cost = Util.generateRandom(100,500);
		}
		if(result == 1) {
			description = "Vehicle Repairs!";
			cost = Util.generateRandom(100,500);
		}
		if(result == 2) {
			description = "Home Renovations!";
			cost = Util.generateRandom(100,500);
		}
		if(result == 3) {
			description = "Fast Food Takeouts!";
			cost = Util.generateRandom(60,300);
		}
		if(result == 4) {
			description = "Leisure Day!";
			cost = Util.generateRandom(50,500);
		}
		if(result == 5) {
			description = "Getting Some Snacks!";
			cost = Util.generateRandom(20,100);
		}
	}

	public void update() {}

	public void useAvailableMoney() {
		game.ps.p.availableMoney -= cost;
	}

	public void useCreditCard() {
		game.ps.p.currentCredit += cost;
		game.ps.p.calculateCashflow();
	}

	public void render(Graphics2D g) {
		if(this.visible) {
			// BG
			g.setColor(new Color(0f,0f,0f,0.8f));
			g.fillRect(0,0,800,600);

			// Action Panel
			g.drawImage(doodadImage, x, y, null);

			// Draw Properties
			int lineSpace = 15;
			int xOffset = 40;
			int yOffset = 92;
			Util.drawText(x + xOffset, (y + yOffset) + lineSpace * 1, description, g);
			Util.drawText(x + xOffset, (y + yOffset) + lineSpace * 2, "Total Cost: R" + Util.format(cost), g);

			// Buttons
			amButton.render(g);

			ccButton.render(g);
		}
	}

	public void mousePressed(MouseEvent e) {
		if(amButton.containsMouse()) {
			amButton.toggleOffset(true);
		}

		if(ccButton.containsMouse()) {
			ccButton.toggleOffset(true);
		}
	}

	public void mouseReleased(MouseEvent e) {
		if(amButton.containsMouse()) {
			amButton.toggleOffset(false);

			useAvailableMoney();
			closeActionPanel();
		}

		if(ccButton.containsMouse()) {
			ccButton.toggleOffset(false);

			useCreditCard();
			closeActionPanel();
		}
	}
}
