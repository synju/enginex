package GrappleMines;

import java.awt.*;

public class Config {
	public static String title = "Grapple Mines";

	public static boolean fullscreen = false;
	public static boolean sizeable   = false;

	public static int width  = (fullscreen) ? (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth() : 260;
	public static int height = (fullscreen) ? (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight() : 480;

	// Controller
	public static boolean controllerEnabled = true;
}
