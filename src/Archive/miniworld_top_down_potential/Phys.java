package Archive.miniworld_top_down_potential;

import java.awt.Rectangle;

public class Phys {
	public static boolean collision(Rectangle a, Rectangle b) {
		if(a.intersects(b))
			return true;
		
		return false;
	}
}
