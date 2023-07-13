package Complete.megamoney;

import EngineX.Button;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class GiftPanel extends ActionPanel {
	Game game;

	// Images
	Image actionPanelImage = new ImageIcon("res/megamoney/gift_actionPanel.png").getImage();

	// Buttons
	Button okButton; // OK Button

	// Properties
	String description = "";
	String description2 = "";

	public GiftPanel(Game game, int width, int height) {
		super(game, width, height);
		this.game = game;

		okButton = new Button(game, (x + 130), ((y + h) - (55+15)), 201, 55, "res/megamoney/okButton.png", "res/megamoney/okButton.png");
		okButton.setOffsets(2,2);
	}

	public void generate() {
		description = "Someone sent you a Gift!";
		int gift = Util.generateRandom(100,200);
		game.ps.p.availableMoney += gift;
		description2 = "R" + Math.round(gift) + ".00";
	}

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
			int xOffset = 40;
			int yOffset = 92;
			Util.drawText(x + xOffset, (y + yOffset) + lineSpace * 1, description, g);
			Util.drawText(x + xOffset, (y + yOffset) + lineSpace * 2, description2, g);

			// Buttons
			okButton.render(g);
		}
	}

	public void mousePressed(MouseEvent e) {
		if(okButton.containsMouse()) {
			okButton.toggleOffset(true);
		}
	}

	public void mouseReleased(MouseEvent e) {
		if(okButton.containsMouse()) {
			okButton.toggleOffset(false);

			closeActionPanel();
		}
	}
}
