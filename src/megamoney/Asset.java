package megamoney;

public class Asset {
	Game game;
	Player player;

	String description = "";

	double cost = 0.00;
	double down_payment = 0.00;
	double interest = 0.00;
	double credit = 0.00;
	double bank_payment = 0.00;
	double income = 0.00;
	double cash_flow = 0.00;

	public Asset(Game game) {
		this.game = game;

		cost 					= Util.generateRandom(3000,30000);
		down_payment 	= (cost / 100) * Util.generateRandom(15,40);
		interest 			= ((cost - down_payment) / 100) * 10;
		credit 				= (cost - down_payment) + interest;
		bank_payment 	= (credit / 100) / 2;
		income 				= (cost / 100) + (((cost / 100) / 100) * Util.generateRandom(2,11));
		cash_flow 		= income - bank_payment;
	}

	public void printDetails() {
		System.out.println("cost: " + Math.round(cost));
		System.out.println("down payment: " + Math.round(down_payment));
		System.out.println("credit: " + Math.round(credit));
		System.out.println("bank payment: " + Math.round(bank_payment));
		System.out.println("income: " + Math.round(income));
		System.out.println("cash flow: " + Math.round(cash_flow));
	}
}
