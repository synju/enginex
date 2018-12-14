package platformer;

import java.awt.Rectangle;

public class Phys {
	public static boolean collision(Rectangle a, Rectangle b) {
		if(a.intersects(b))
			return true;
		
		return false;
	}
}
