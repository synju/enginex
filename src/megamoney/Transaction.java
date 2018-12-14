package megamoney;

public class Transaction {
	public String name;
	public String description;
	public double value;
	
	public Transaction(String name, String description, double value) {
		this.name = name;
		this.description = description;
		this.value = value;
	}
}
