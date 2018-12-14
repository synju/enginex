package megamoney;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class Util {
	static void drawText(String text, int x, int y, int size, Graphics2D g) {
		g.setFont(new Font("Arial", Font.BOLD, size));
		g.setColor(Color.WHITE);
		g.drawString(text, x, y);
	}
	
	static void drawText(int x, int y, String text, int size, Graphics2D g) {
		g.setFont(new Font("Arial", Font.BOLD, size));
		g.setColor(Color.WHITE);
		g.drawString(text, x, y);
	}
	
	static void drawText(int x, int y, String text, Graphics2D g) {
		g.setFont(new Font("Arial", Font.PLAIN, 12));
		g.setColor(Color.WHITE);
		g.drawString(text, x, y);
	}

	static String format(double v) {
		return String.format("%.2f", v);
	}

	static String format(int v) {
		return String.format("%.2f", v);
	}

	static String format(float v) {
		return String.format("%.2f", v);
	}
}
