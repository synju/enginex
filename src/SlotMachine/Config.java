package SlotMachine;

public class Config {
	// General
	public static String  gameName            = "Slot Machine"; // Simulation Mode on or off, for balancing reel symbol pool to get correct RTP Percentage
	public static boolean splashScreenEnabled = false; // Splashscreen on or off.
	public static boolean renderReelsEnabled  = true; // Toggle for whether to render reels.
	public static boolean stopSpinEnabled     = true; // Allow player to manually stop a spin.

	// Timers
	public static int     minSpinTime         = 30; // Time it takes for a spin to run.
	public static int     maxSpinTime         = 120; // Time it takes for a spin to run.
	public static int     minQuickSpinTime    = 10; // Time it takes for a quickspin to run.
	public static int     maxQuickSpinTime    = 30; // Time it takes for a quickspin to run.
	public static int     reelStopSpinTime    		= 30; // Time it takes for a quickspin to run.
	public static int     reelQuickStopSpinTime   = 10; // Time it takes for a quickspin to run.


	// Sound
	public static boolean startupMusicEnabled = false; // Toggle for whether music starts with game or not.
	public static boolean volumeEnabled       = false; // Toggle for whether volume is enabled or not.
	public static boolean continuousInput     = false; // Toggle for whether continuous input is enabled or not.
	public static int     winLimit            = 10000; // Toggle for whether continuous input is enabled or not.

	// Simulation
	public static boolean simulationEnabled = false; // Simulation Mode on or off, for balancing reel symbol pool to get correct RTP Percentage
	//public static int     simSpinCount      = 5000000;
	public static int     simSpinCount      = 5000000;
	public static int     simBetAmount      = 1;

	// Game Window
	public static boolean sizeable   = false;
	//	public static boolean fullscreen = true;
	public static boolean fullscreen = (simulationEnabled) ? false : true;
	public static int     width      = (simulationEnabled) ? 320 : 1360;
	public static int     height     = (simulationEnabled) ? 240 : 768;

	// Dynamic Fullscreen Dimensions
	//	public static int width = (fullscreen) ? (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() : 300;
	//	public static int height = (fullscreen) ? (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() : 200;

	// Controller... Disabled... Further Dev Needed...
	public static boolean controllerEnabled = true;
}
