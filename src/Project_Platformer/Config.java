package Project_Platformer;

import java.awt.*;

public class Config {
	// Game Window... Decided to make it 42*32px Width by 21*32px Height
	public static boolean fullscreen = false;
	public static boolean sizeable = false;
	public static int width = (fullscreen) ? (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth() : 42 * 32;
	public static int height = (fullscreen) ? (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight(): 21 * 32;

	// Controller... Disabled... Further Dev Needed...
	public static boolean controllerEnabled = false;
}
