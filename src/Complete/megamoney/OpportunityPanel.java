package Complete.megamoney;

import EngineX.Button;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class OpportunityPanel extends ActionPanel {
	Game game;

	// Images
	Image actionPanelImage = new ImageIcon("res/Complete.megamoney/opportunityActionPanel.png").getImage();

	// Buttons
	Button purchaseButton; // Purchase Button
	Button passButton; // Pass Button

	// Properties
	String description = "";

	// Asset
	Asset asset;

	public OpportunityPanel(Game game, int width, int height) {
		super(game, width, height);
		this.game = game;

		purchaseButton = new Button(game, (x + 15), ((y + h) - (55+15)), 201, 55, "res/Complete.megamoney/purchase_Button.png", "res/Complete.megamoney/purchase_Button.png");
		purchaseButton.setOffsets(2,2);

		passButton = new Button(game, ((x + w) - (201 + 15)), ((y + h) - (55+15)), 201, 55, "res/Complete.megamoney/passButton.png", "res/Complete.megamoney/passButton.png");
		passButton.setOffsets(2,2);
	}

	public void generate() {
		asset = new Asset(this.game);
	}

	public void update() {}

	public void purchase() {
		game.ps.p.assetList.add(asset);
		game.ps.p.availableMoney -= asset.down_payment;
		game.ps.p.calculateCashflow();
	}

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
			Util.drawText(x + xOffset, (y + yOffset) + lineSpace * 1, "Cost: R" + Util.format(asset.cost), g);
			Util.drawText(x + xOffset, (y + yOffset) + lineSpace * 2, "Down Payment: R" + Util.format(asset.down_payment), g);
			Util.drawText(x + xOffset, (y + yOffset) + lineSpace * 3, "Credit: R" + Util.format(asset.credit), g);
			Util.drawText(x + xOffset, (y + yOffset) + lineSpace * 4, "Bank Payment: R" + Util.format(asset.bank_payment), g);
			Util.drawText(x + xOffset, (y + yOffset) + lineSpace * 5, "Income: R" + Util.format(asset.income), g);
			Util.drawText(x + xOffset, (y + yOffset) + lineSpace * 6, "Cash Flow: R" + Util.format(asset.cash_flow), g);

			// Buttons
			purchaseButton.render(g);
			passButton.render(g);
		}
	}

	public void mousePressed(MouseEvent e) {
		if(purchaseButton.containsMouse()) {
			purchaseButton.toggleOffset(true);
		}

		if(passButton.containsMouse()) {
			passButton.toggleOffset(true);
		}
	}

	public void mouseReleased(MouseEvent e) {
		if(purchaseButton.containsMouse()) {
			purchaseButton.toggleOffset(false);

			purchase();
			closeActionPanel();
		}

		if(passButton.containsMouse()) {
			passButton.toggleOffset(false);

			closeActionPanel();
		}
	}
}
