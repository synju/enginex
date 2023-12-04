package The_Shroud;

import java.awt.*;

public class Config {
	public static String title = "The Shroud";

	public static boolean fullscreen = false;
	public static boolean sizeable   = false;

	public static int width  = (fullscreen) ? (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth() : 640;
	public static int height = (fullscreen) ? (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight() : 480;

	// Controller
	public static boolean controllerEnabled = true;
}