package megamoney;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import enginex.GameObject;

@SuppressWarnings("serial")
public class Player extends GameObject {
	Game										game;
	int											month					= 1;

	ArrayList<Transaction>	assets				= new ArrayList<>();
	ArrayList<Transaction>	liabilities		= new ArrayList<>();
	ArrayList<Transaction>	expenseList		= new ArrayList<>();

	double									startingMoney	= 0.00;
	double 									availableMoney = 0.00;
	double 									assetIncome 	= 0.00;
	double 									salaryIncome 	= 0.00;
	double 									totalIncome 	= 0.00;
	double 									fixedExpenses = 0.00;
	double 									liquidExpenses = 0.00;
	double 									totalExpenses = 0.00;
	double									cashflow			= 0.00;

	static final int				LINESPACE			= 15;
	static final int				LEFTPADDING		= 5;

	public Player(Game game) {
		super(game);
		this.game = game;

		salaryIncome = Util.generateRandom(2000,9000);
	}

	public void step() {
		salaryIncome();
		businessIncome();
		expenses();
	}

	public void salaryIncome() {
		availableMoney += salaryIncome;
	}

	public void businessIncome() {
		double totalIncome = 0;

		for(Transaction income:assets)
			totalIncome += income.value;

		availableMoney += totalIncome;
	}

	public void expenses() {
		double totalExpenses = 0;
		for(Transaction expense:expenseList)
			totalExpenses += expense.value;

		availableMoney -= totalExpenses;
	}

	public void render(Graphics2D g) {
		Util.drawText(LEFTPADDING, LINESPACE * 1, "Salary Income: R" + Util.format(salaryIncome), g);
		Util.drawText(LEFTPADDING, LINESPACE * 2, "Asset Income: R" + Util.format(assetIncome), g);
		Util.drawText(LEFTPADDING, LINESPACE * 3, "Total Income: R" + Util.format(totalIncome), g);
		Util.drawText(LEFTPADDING, LINESPACE * 4, "---", g);
		Util.drawText(LEFTPADDING, LINESPACE * 5, "Fixed Expenses: R" + Util.format(fixedExpenses), g);
		Util.drawText(LEFTPADDING, LINESPACE * 6, "Liquid Expenses: R" + Util.format(liquidExpenses), g);
		Util.drawText(LEFTPADDING, LINESPACE * 7, "Total Expenses: R" + Util.format(totalExpenses), g);
		Util.drawText(LEFTPADDING, LINESPACE * 8, "---", g);
		Util.drawText(LEFTPADDING, LINESPACE * 9, "CashFlow: R" + Util.format(totalIncome - totalExpenses), g);
		Util.drawText(LEFTPADDING, LINESPACE * 10, "Available Money: R" + Util.format(availableMoney), g);
		Util.drawText(LEFTPADDING, LINESPACE * 11, "---", g);
		Util.drawText(LEFTPADDING, LINESPACE * 12, "Month: " + month, g);
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			step();
		}
	}
}
