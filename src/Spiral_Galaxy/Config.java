package Spiral_Galaxy;

import java.awt.*;

public class Config {
	public static String title = "Spiral Galaxy";

	public static boolean fullscreen = true;
	public static boolean sizeable   = false;

	public static int width  = (fullscreen) ? (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth() : 700;
	public static int height = (fullscreen) ? (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight() : 700;

	// Controller
	public static boolean controllerEnabled = true;
}
