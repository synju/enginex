package Tamagotchi;

import java.awt.*;

public class Config {
	// Game Config
	public static boolean splashScreenEnabled = false; // Splashscreen on or off.

	// Game Window...
	public static boolean fullscreen = false;
	public static boolean sizeable   = false;
	public static int     width      = (fullscreen) ? (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth() : 300;
	public static int     height     = (fullscreen) ? (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight() : 200;

	// Controller... Disabled... Further Dev Needed...
	public static boolean controllerEnabled = true;
}
