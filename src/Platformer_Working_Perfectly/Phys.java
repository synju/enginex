package Platformer_Working_Perfectly;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Line2D;

public class Phys {
	public static boolean collision(Rectangle a, Rectangle b) {
		return (a.intersects(b));
	}

//	if(Phys.findPoints(area, line, 10)) System.out.println("Collision");
	public static boolean lineCollision(Area area1, Line2D line1, int depth) {
		Point p1 = new Point((int)(line1.getX2() + line1.getX1()) / 2, (int)(line1.getY2() + line1.getY1()) / 2);

		if(depth == 0)
			return false;

		if(area1.contains(p1))
			return true;
		else
			return lineCollision(area1, new Line2D.Double(p1, line1.getP2()), depth - 1) || lineCollision(area1, new Line2D.Double(line1.getP1(), p1), depth - 1);
	}
}
