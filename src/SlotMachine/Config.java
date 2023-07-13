package SlotMachine;

import java.awt.*;

public class Config {
	// Game Config
	public static boolean simulationEnabled   = false; // Simulation Mode on or off, for balancing reel symbol pool to get correct RTP Percentage
	public static boolean splashScreenEnabled = true; // Splashscreen on or off.
	public static boolean renderReelsEnabled  = true; // Toggle for whether or not to render reels.
	public static boolean stopSpinEnabled     = true; // Allow player to manually stop a spin.
	public static int     spinTime            = 80; // Time it takes for a spin to run.
	public static int     quickSpinTime       = 10; // Time it takes for a quickspin to run.
	public static boolean startupMusicEnabled = true; // Toggle for whether music starts with game or not.
	public static boolean volumeEnabled       = true; // Toggle for whether volume is enabled or not.

	// Game Window...
	public static boolean fullscreen = (simulationEnabled) ? false : true;
	public static boolean sizeable   = false;
	public static int     width      = (fullscreen) ? (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth() : 300;
	public static int     height     = (fullscreen) ? (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight() : 200;

	// Controller... Disabled... Further Dev Needed...
	public static boolean controllerEnabled = true;
}
