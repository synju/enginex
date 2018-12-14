package megamoney;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import enginex.GameObject;

@SuppressWarnings("serial")
public class Player extends GameObject {
	Game										game;

	ArrayList<Transaction>	assets				= new ArrayList<>();
	ArrayList<Transaction>	liabilities		= new ArrayList<>();
	ArrayList<Transaction>	expenseList		= new ArrayList<>();

	double									startingMoney	= 0.00;
	double									currentMoney	= 0.00;
	double									expenses			= 0.00;
	double									passiveIncome	= 0.00;
	double									jobIncome			= 18750.00;
	double									cashflow			= 0.00;

	int											month					= 1;

	static final int				LINESPACE			= 15;
	static final int				LEFTPADDING		= 5;

	public Player(Game game) {
		super(game);
		this.game = game;
	}

	public void step() {
		jobIncome();
		passiveIncome();
		expenses();
	}

	public void turn() {

	}

	public void jobIncome() {
		currentMoney += jobIncome;
	}

	public void passiveIncome() {
		double totalIncome = 0;

		for(Transaction income:assets)
			totalIncome += income.value;

		currentMoney += totalIncome;
	}

	public void expenses() {
		double totalExpenses = 0;
		for(Transaction expense:expenseList)
			totalExpenses += expense.value;

		currentMoney -= totalExpenses;
	}

	public void render(Graphics2D g) {
		Util.drawText(LEFTPADDING, LINESPACE * 1, "Starting Money: R" + Util.format(startingMoney), g);
		Util.drawText(LEFTPADDING, LINESPACE * 2, "Available Money: R" + Util.format(currentMoney), g);
		Util.drawText(LEFTPADDING, LINESPACE * 3, "CashFlow: R" + Util.format(expenses), g);
		Util.drawText(LEFTPADDING, LINESPACE * 4, "Expenses: R" + Util.format(passiveIncome), g);
		Util.drawText(LEFTPADDING, LINESPACE * 5, "Passive Income: R" + Util.format(jobIncome), g);
		Util.drawText(LEFTPADDING, LINESPACE * 6, "Active Income: R" + Util.format(cashflow), g);
		Util.drawText(LEFTPADDING, LINESPACE * 7, "Month: " + month, g);
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			step();
		}
	}
}
