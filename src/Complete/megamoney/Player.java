package Complete.megamoney;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import EngineX.Button;
import EngineX.GameObject;

public class Player extends GameObject {
	Game										game;
	int											month					= 1;

	ArrayList<Asset> assetList 								= new ArrayList<>();
	ArrayList<FixedExpense> fixedExpenseList 	= new ArrayList<>();

	// Job
	boolean hasJob = true;
	boolean huntForJobs = true;
	boolean canLandOnJob = true;

	double 									salaryIncome 		= 0.00;
	double 									assetIncome 		= 0.00;
	double 									totalIncome 		= 0.00;
	double 									fixedExpenses 	= 0.00;
	double 									liquidExpenses 	= 0.00;
	double 									totalExpenses 	= 0.00;
	double 									currentCredit 	= 0.00;
	double									cashflow				= 0.00;
	double 									availableMoney 	= 0.00;

	static final int				LINESPACE			= 15;
	static final int				LEFTPADDING		= 5;

	Spinner spinner;

	Button spinButton;
	boolean spinButtonEnabled = true;

	// ActionPanel Config
	boolean actionPanelActive = false;
	ActionPanel currentActionPanel;

	// Panels
	PaymentNotificationPanel 	paymentPanel;
	DoodadPanel 							doodadPanel;
	JobPanel 									jobPanel;
	GiftPanel 								giftPanel;
	CharityPanel 							charityPanel;
	OpportunityPanel 					opportunityPanel;

	int spinCount = 0;

	public Player(Game game) {
		super(game);
		this.game = game;

		// Setup Player
		setupPlayer();

		// Spinner & Spin Button
		spinner = new Spinner(game);
		spinner.setLocation((game.window.getWidth()/2)-(spinner.getWidth()/2),150);
		spinButton = new Button(game, 400-78, 450, 176, 76, "res/Complete.megamoney/spin_button.png", "res/Complete.megamoney/spin_button.png");
		spinButton.setOffsets(2,2);

		// Action Panels
		paymentPanel 			= new PaymentNotificationPanel(this.game, 442,415);
		doodadPanel 			= new DoodadPanel(this.game, 442,415);
		jobPanel 					= new JobPanel(this.game, 442,415);
		giftPanel 				= new GiftPanel(this.game, 442,415);
		charityPanel 			= new CharityPanel(this.game, 442,415);
		opportunityPanel 	= new OpportunityPanel(this.game, 442,415);

		// Current Panel
		currentActionPanel = null;
	}

	public void setupPlayer() {
		generateFixedExpenses();
		salaryIncome = fixedExpenses + Util.generateRandom(100,200);

		calculateCashflow();

		availableMoney = Util.generateRandom(500,2000);
	}

	public void calculateCashflow() {
		totalIncome = salaryIncome + getAssetIncome();
		totalExpenses = fixedExpenses + getLiquidExpenses() + getCreditExpense();
		cashflow = totalIncome - totalExpenses;
	}

	public double getLiquidExpenses() {
		liquidExpenses = 0;

		for(Asset a:assetList) {
			if(a.credit == 0) {
				liquidExpenses += 0;
			}
			else {
				liquidExpenses += a.bank_payment;
			}
		}

		return liquidExpenses;
	}

	public double getAssetIncome() {
		assetIncome = 0;

		for(Asset a:assetList) {
			if(a.credit == 0) {
				assetIncome += a.income;
			}
			else {
				assetIncome += a.cash_flow;
			}
		}

		return assetIncome;
	}

	public void generateFixedExpenses() {
		fixedExpenseList = new ArrayList<>();

		fixedExpenseList.add(new FixedExpense("Transport",			Util.generateRandom(750,	2000)));
		fixedExpenseList.add(new FixedExpense("Family", 				Util.generateRandom(300,	1250)));
		fixedExpenseList.add(new FixedExpense("Medical", 				Util.generateRandom(0,		2000)));
		fixedExpenseList.add(new FixedExpense("Utility", 				Util.generateRandom(500,	3500)));
		fixedExpenseList.add(new FixedExpense("Food", 					Util.generateRandom(500,	3500)));
		fixedExpenseList.add(new FixedExpense("Shelter", 				Util.generateRandom(1000,	5000)));
		fixedExpenseList.add(new FixedExpense("Entertainment", 	Util.generateRandom(500,	1500)));
		fixedExpenseList.add(new FixedExpense("Other", 					Util.generateRandom(300,	1000)));

		for(FixedExpense f : fixedExpenseList) {
			fixedExpenses += f.expense;
		}
	}

	public void step() {
		if(!actionPanelActive) {
			if(spinCount >= 4 && availableMoney > 0) {
				expenses();

				totalIncome = getSalaryIncome() + getAssetIncome();
				cashflow = totalIncome - totalExpenses;

				// Reset Can Land on Job
				canLandOnJob = true;

				// Reset Spin Count and Increment Month
				spinCount = 0;
				month++;

				generateActionPanel(ActionPanel.PAYMENT);

				// Check if able to continue...
				inNegativeCheck();
			}
		}
	}

	public void update() {
		spinner.update();
	}

	public double getSalaryIncome() {
		if(!hasJob) salaryIncome = 0;

		return salaryIncome;
	}

	public void assetIncome() {
		assetIncome = 0;

		for(Asset a:assetList) {
			if(a.credit == 0) {
				assetIncome += a.cash_flow;
			}
			else {
				assetIncome += a.income;
			}
		}

		availableMoney += assetIncome;
	}

	public void expenses() {
		// Calculate Liquid Expenses
		liquidExpenses = 0;
		for(Asset a:assetList) {
			if((a.credit - a.bank_payment) > 0) {
				a.credit -= a.bank_payment;
			}
			else {
				a.credit = 0;
				a.bank_payment = 0;
			}

			liquidExpenses += a.bank_payment;
		}

		totalExpenses = fixedExpenses + liquidExpenses + getCreditExpense();
		availableMoney -= totalExpenses;
	}

	public double getCreditExpense() {
		return (currentCredit / 100) * 10;
	}

	public void inNegativeCheck() {
		if(availableMoney < (cashflow - (cashflow * 2))) {
			spinButtonEnabled = false;
			System.out.println("Unable to continue");
		}
	}

	public void render(Graphics2D g) {
		// Spinner (Always First)
		spinner.render(g);

		Util.drawText(LEFTPADDING, LINESPACE * 1, "Salary Income: R" + Util.format(salaryIncome), g);
		Util.drawText(LEFTPADDING, LINESPACE * 2, "Asset Income: R" + Util.format(assetIncome), g);
		Util.drawText(LEFTPADDING, LINESPACE * 3, "Total Income: R" + Util.format(totalIncome), g);
		Util.drawText(LEFTPADDING, LINESPACE * 4, "---", g);
		Util.drawText(LEFTPADDING, LINESPACE * 5, "Fixed Expenses: R" + Util.format(fixedExpenses), g);
		Util.drawText(LEFTPADDING, LINESPACE * 6, "Liquid Expenses: R" + Util.format(liquidExpenses), g);
		Util.drawText(LEFTPADDING, LINESPACE * 7, "Credit Expenses: R" + Util.format(getCreditExpense()), g);
		Util.drawText(LEFTPADDING, LINESPACE * 8, "Total Expenses: R" + Util.format(totalExpenses), g);
		Util.drawText(LEFTPADDING, LINESPACE * 9, "---", g);

		if(cashflow >= 0) {
			Util.drawText(LEFTPADDING, LINESPACE * 10, "CashFlow: R" + Util.format(cashflow), g);
		}
		else {
			Util.drawText(LEFTPADDING, LINESPACE * 10, "CashFlow: -R" + Util.format(cashflow-(cashflow*2)), g);
		}

		Util.drawText(LEFTPADDING, LINESPACE * 11, "Available Money: R" + Util.format(availableMoney), g);
		Util.drawText(LEFTPADDING, LINESPACE * 12, "---", g);
		Util.drawText(LEFTPADDING, LINESPACE * 13, "Month: " + month, g);

		spinButton.render(g);

		if(currentActionPanel!=null) currentActionPanel.render(g);
	}

	public void generateActionPanel(int type) {
		// Payment
		if(type == ActionPanel.PAYMENT) {
			paymentPanel.generate();
			currentActionPanel = paymentPanel;
		}

		// Doodad
		if(type == ActionPanel.DOODAD) {
			doodadPanel.generate();
			currentActionPanel = doodadPanel;
		}

		// Job
		if(type == ActionPanel.JOB) {
			jobPanel.generate();
			currentActionPanel = jobPanel;
		}

		// Gift
		if(type == ActionPanel.GIFT) {
			giftPanel.generate();
			currentActionPanel = giftPanel;
		}

		// Charity
		if(type == ActionPanel.CHARITY) {
			charityPanel.generate();
			currentActionPanel = charityPanel;
		}

		// Opportunity
		if(type == ActionPanel.OPPORTUNITY) {
			opportunityPanel.generate();
			currentActionPanel = opportunityPanel;
		}

		setActionPanelActive(true);
		currentActionPanel.setVisible(true);
	}

	public void setActionPanelActive(boolean v) {
		this.actionPanelActive = v;
	}

	public void spin() {
		// Increment spinCount
		spinCount++;

		if(availableMoney > 0) {
			// Spinner..
			spinner.start();
		}
	}

	public void keyPressed(KeyEvent e) {
//		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
//			// Do nothing..
//		}

		// Temporary
		if(actionPanelActive) {
			if(currentActionPanel != null) {
				currentActionPanel.keyPressed(e);
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			spin();
		}
	}

	public void mousePressed(MouseEvent e) {
		if(actionPanelActive) {
			if(currentActionPanel != null) {
				currentActionPanel.mousePressed(e);
			}
		}
		else {
			if(!spinner.spinning && availableMoney > 0) {
				if(spinButton.containsMouse()) {
					if(spinButtonEnabled) {
						spinButton.toggleOffset(true);
					}
				}
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		if(actionPanelActive) {
			if(currentActionPanel != null) {
				currentActionPanel.mouseReleased(e);
			}
		}
		else {
			if(!spinner.spinning) {
				if(spinButton.containsMouse()) {
					if(spinButtonEnabled) {
						spinButton.toggleOffset(false);
						spin();
					}
				}
			}
		}
	}
}
