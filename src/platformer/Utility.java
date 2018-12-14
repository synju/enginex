package platformer;

import java.util.Random;

public class Utility {
	public static int random(int min, int max) {
		Random random = new Random();
		return random.nextInt(max) + min;
	}
}
