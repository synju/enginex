package enginex.core;

import java.util.Random;

public class Util {
	public static int getRandom(int min, int max) {
		Random random = new Random();
		return random.nextInt(max - min + 1) + min;
	}
}
