package flowershop;

import java.io.Serializable;
import java.util.ArrayList;

public class ProfileData implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public String name;
	public int progress;
	public ArrayList<Flower> flowers;
	
	ProfileData(String name, int progress, ArrayList<Flower> flowers) {
		this.name = name;
		this.progress = progress;
		this.flowers = flowers;
	}
}
