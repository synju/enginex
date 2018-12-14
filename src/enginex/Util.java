package enginex;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class Util {
	public static void drawText(String text, int x, int y, int size, Graphics2D g) {
		g.setFont(new Font("Arial", Font.BOLD, size));
		g.setColor(Color.WHITE);
		g.drawString(text, x, y);
	}

	public static void drawText(int x, int y, String text, int size, Graphics2D g) {
		g.setFont(new Font("Arial", Font.BOLD, size));
		g.setColor(Color.WHITE);
		g.drawString(text, x, y);
	}
	
	public static void drawText(int x, int y, String text, int size, Color color, Graphics2D g) {
		g.setFont(new Font("Arial", Font.BOLD, size));
		g.setColor(color);
		g.drawString(text, x, y);
	}

	public static void drawText(int x, int y, String text, Graphics2D g) {
		g.setFont(new Font("Arial", Font.PLAIN, 12));
		g.setColor(Color.WHITE);
		g.drawString(text, x, y);
	}

	public static String format(double v) {
		return String.format("%.2f", v);
	}

	public static String format(int v) {
		return String.format("%.2f", v);
	}

	public static String format(float v) {
		return String.format("%.2f", v);
	}
}
