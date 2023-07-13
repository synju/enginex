package EngineX;

import com.badlogic.gdx.math.Vector2;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

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

	public static ArrayList<String> readText(String path) {
		ArrayList<String> lines = new ArrayList<>();

		try {
			File fp = new File(path);
			FileReader fr = new FileReader(fp);
			BufferedReader br = new BufferedReader(fr);
			String line;
			while((line = br.readLine()) != null) {
				lines.add(line);
			}
			fr.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		return lines;
	}

	// Get lines in a file
	public static int linesInFile(String path) {
		try {
			return (int)Files.lines(Paths.get(path)).count(); // Get lines and convert to integer
		}
		catch(Exception e) {
			e.printStackTrace(); // Print error if file does not exist.
		}
		return -1; // Return -1, if file does not exist.
	}

	// Get Random Number in Range
	public static int getRandomNumberInRange(int min, int max) {
		if(min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

	// Get Distance using 2 Vector2's
	public static float getDistance(Vector2 a, Vector2 b) {
		return (float) (Math.sqrt((b.y - a.y) * (b.y - a.y) + (b.x - a.x) * (b.x - a.x)));
	}
}
