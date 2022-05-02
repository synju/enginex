package megamoney;

import enginex.Button;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class JobPanel extends ActionPanel {
	Game game;

	// Images
	Image jobImage = new ImageIcon("res/megamoney/job_actionPanel.png").getImage();

	// Buttons
	Button okButton; // Ok Button

	// Properties
	String description = "";
	String description2 = "";

	public JobPanel(Game game, int width, int height) {
		super(game, width, height);
		this.game = game;

		okButton = new Button(game, (x + 130), ((y + h) - (55+15)), 201, 55, "res/megamoney/okButton.png", "res/megamoney/okButton.png");
		okButton.setOffsets(2,2);
	}

	public void generate() {
		int result;

		// Don't have a Job, so you'll get a Job
		if(!game.ps.p.hasJob) {
			// 0 Get Job
			result = 4;
		}
		else if(game.ps.p.availableMoney < 20000) {
			// 1 Increase
			// 2 Bonus
			result = Util.generateRandom(1, 2);
		}
		else {
			// 1 Increase
			// 2 Bonus
			// 3 Decrease
			// 4 Get Job
			result = Util.generateRandom(0, 3);
		}

		if(result == 0) {
			description = "You Lost Your Job!";
			game.ps.p.salaryIncome = 0;
			game.ps.p.hasJob = false;
			description2 = "";
		}
		if(result == 1) {
			int increase = Util.generateRandom(2,5);
			description = "Your Salary Got Increased By " + increase + "%";
			game.ps.p.salaryIncome += ((game.ps.p.salaryIncome / 100) * increase);
			description2 = "New Salary: R" + Math.round(game.ps.p.salaryIncome);
		}
		if(result == 2) {
			int bonus = Util.generateRandom(2,5);
			int bonusAmount = (int)((game.ps.p.salaryIncome / 100) * bonus);
			game.ps.p.availableMoney += bonusAmount;
			description = "You Received a Bonus of R" + Math.round(bonusAmount) + "!";
			description2 = "";
		}
		if(result == 3) {
			int decrease = Util.generateRandom(2,5);
			description = "Your Salary Got Cut By " + decrease + "%";
			game.ps.p.salaryIncome -= ((game.ps.p.salaryIncome / 100) * decrease);
			description2 = "New Salary: R" + Math.round(game.ps.p.salaryIncome);
		}
		if(result == 4) {
			description = "You Found a Job!";
			game.ps.p.salaryIncome = game.ps.p.fixedExpenses + Util.generateRandom(100,200);
			game.ps.p.hasJob = true;
			description2 = "New Salary: R" + Math.round(game.ps.p.salaryIncome);
		}

		// Set canLandOnJob to false
		game.ps.p.canLandOnJob = false;

		// Recalculate Cashflow
		game.ps.p.calculateCashflow();
	}

	public void update() {}

	public void render(Graphics2D g) {
		if(this.visible) {
			// BG
			g.setColor(new Color(0f,0f,0f,0.8f));
			g.fillRect(0,0,800,600);

			// Action Panel
			g.drawImage(jobImage, x, y, null);

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
