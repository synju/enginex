package Complete.megamoney;

import EngineX.Button;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class CharityPanel extends ActionPanel {
	Game game;

	// Images
	Image actionPanelImage = new ImageIcon("res/Complete.megamoney/charity_actionPanel.png").getImage();

	// Buttons
	Button donateButton; 	// Donate Button
	Button passButton; 		// Pass Button
	Button plusButton; 		// Plus Button
	Button minusButton; 	// Minus Button

	// Properties
	int amount = 10;

	public CharityPanel(Game game, int width, int height) {
		super(game, width, height);
		this.game = game;

		donateButton = new Button(game, (x + 15), ((y + h) - (55+15)), 201, 55, "res/Complete.megamoney/donateButton.png", "res/Complete.megamoney/donateButton.png");
		donateButton.setOffsets(2,2);

		passButton = new Button(game, ((x + w) - (201 + 15)), ((y + h) - (55+15)), 201, 55, "res/Complete.megamoney/passButton.png", "res/Complete.megamoney/passButton.png");
		passButton.setOffsets(2,2);

		plusButton = new Button(game, x+w-108, y+100, 58, 55, "res/Complete.megamoney/plusButton.png", "res/Complete.megamoney/plusButton.png");
		plusButton.setOffsets(2,2);

		minusButton = new Button(game, x+50, y+100, 58, 55, "res/Complete.megamoney/minusButton.png", "res/Complete.megamoney/minusButton.png");
		minusButton.setOffsets(2,2);
	}

	public void generate() {}

	public void update() {}

	public void render(Graphics2D g) {
		if(this.visible) {
			// BG
			g.setColor(new Color(0f,0f,0f,0.8f));
			g.fillRect(0,0,800,600);

			// Action Panel
			g.drawImage(actionPanelImage, x, y, null);

			// Draw Properties
			int lineSpace = 15;
			int xOffset = 183;
			int yOffset = 112;
			Util.drawText(x + xOffset, (y + yOffset) + lineSpace * 1, "Donate: " + amount + "%", g);

			// Buttons
			donateButton.render(g);
			passButton.render(g);
			plusButton.render(g);
			minusButton.render(g);
		}
	}

	public void mousePressed(MouseEvent e) {
		if(donateButton.containsMouse()) {
			donateButton.toggleOffset(true);
		}

		if(passButton.containsMouse()) {
			passButton.toggleOffset(true);
		}

		if(plusButton.containsMouse()) {
			plusButton.toggleOffset(true);
		}

		if(minusButton.containsMouse()) {
			minusButton.toggleOffset(true);
		}
	}

	public void mouseReleased(MouseEvent e) {
		if(donateButton.containsMouse()) {
			donateButton.toggleOffset(false);

			// Donate Percentage
			game.ps.p.availableMoney -= (game.ps.p.availableMoney / 100) * amount;

			closeActionPanel();
		}

		if(passButton.containsMouse()) {
			passButton.toggleOffset(false);

			// Donate Nothing...

			closeActionPanel();
		}

		if(plusButton.containsMouse()) {
			plusButton.toggleOffset(false);
			if(amount < 100) {
				amount++;
			}
		}

		if(minusButton.containsMouse()) {
			minusButton.toggleOffset(false);

			if(amount > 0) {
				amount--;
			}
		}
	}
}
