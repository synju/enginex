package SlotMachine;

import EngineX.Button;
import com.studiohartman.jamepad.ControllerState;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

public class SlotMachine {
	Game    game;
	boolean initialized = false;
	boolean spinning    = false;

	double   winLimit         = Config.winLimit;
	double   winLimitDisplay  = winLimit;
	double   originalWinLimit = winLimit;
	double   credit           = 0.00;
	double   creditDisplay    = credit;
	double[] betAmounts       = {0.01, 0.05, 0.20, 0.40, 0.60, 0.80, 1, 1.60, 2, 4, 6, 8, 12, 18, 20, 40, 60, 80, 100};
	int      currentBetIndex  = 0;
	double   betAmount        = betAmounts[0];
	String[] spinResult;

	// Symbols
	public static final int LEMON  = 0; // 0.25,0.50,2.50
	public static final int CHERRY = 1; // 0.25,0.50,2.50
	public static final int ORANGE = 2; // 0.25,0.50,2.50
	public static final int PLUM   = 3; // 0.25,0.50,2.50
	public static final int PEACH  = 4; // 0.50,2.00,10.00
	public static final int MELON  = 5; // 1.00,5.00,25.00
	public static final int GRAPES = 6; // 1.00,5.00,25.00
	public static final int SEVEN  = 7; // 0.25,2.00,10.00,100.00

	// Multipliers
	public static final double M_LEMON3  = 0.25;
	public static final double M_LEMON4  = 0.50;
	public static final double M_LEMON5  = 2.50;
	public static final double M_CHERRY3 = 0.25;
	public static final double M_CHERRY4 = 0.50;
	public static final double M_CHERRY5 = 2.50;
	public static final double M_ORANGE3 = 0.25;
	public static final double M_ORANGE4 = 0.50;
	public static final double M_ORANGE5 = 2.50;
	public static final double M_PLUM3   = 0.25;
	public static final double M_PLUM4   = 0.50;
	public static final double M_PLUM5   = 2.50;
	public static final double M_PEACH3  = 0.50;
	public static final double M_PEACH4  = 2;
	public static final double M_PEACH5  = 10;
	public static final double M_MELON3  = 1;
	public static final double M_MELON4  = 5;
	public static final double M_MELON5  = 25;
	public static final double M_GRAPES3 = 1;
	public static final double M_GRAPES4 = 5;
	public static final double M_GRAPES5 = 25;
	public static final double M_SEVEN2  = 0.25;
	public static final double M_SEVEN3  = 2;
	public static final double M_SEVEN4  = 10;
	public static final double M_SEVEN5  = 100;

	// Simulation Variables
	int simSpinCount = 5000000;
	int simBetAmount = 1;

	// 100.19%
	//	int lemonCount  = 5;
	//	int cherryCount = 3;
	//	int orangeCount = 3;
	//	int plumCount   = 3;
	//	int peachCount  = 5;
	//	int melonCount  = 5;
	//	int grapesCount = 4;
	//	int sevensCount = 1;

	// 99.13%
	//	int lemonCount  = 4;
	//	int cherryCount = 4;
	//	int orangeCount = 3;
	//	int plumCount   = 3;
	//	int peachCount  = 4;
	//	int melonCount  = 5;
	//	int grapesCount = 4;
	//	int sevensCount = 1;

	// 97.6%
	//	int lemonCount  = 6;
	//	int cherryCount = 3;
	//	int orangeCount = 3;
	//	int plumCount   = 3;
	//	int peachCount  = 5;
	//	int melonCount  = 5;
	//	int grapesCount = 5;
	//	int sevensCount = 2;

	// 97.35% <--- Good one.
	int lemonCount  = 12;
	int cherryCount = 10;
	int orangeCount = 8;
	int plumCount   = 4;
	int peachCount  = 3;
	int melonCount  = 1;
	int grapesCount = 1;
	int sevensCount = 1;

	// 96.07%
	//	int lemonCount 	= 2;
	//	int cherryCount = 2;
	//	int orangeCount = 1;
	//	int plumCount 	= 1;
	//	int peachCount 	= 2;
	//	int melonCount 	= 2;
	//	int grapesCount = 2;
	//	int sevensCount = 1;

	// 94.5%
	//	int lemonCount  = 6;
	//	int cherryCount = 4;
	//	int orangeCount = 3;
	//	int plumCount   = 2;
	//	int peachCount  = 4;
	//	int melonCount  = 5;
	//	int grapesCount = 3;
	//	int sevensCount = 1;

	// 90%
	//	int lemonCount  = 6;
	//	int cherryCount = 4;
	//	int orangeCount = 3;
	//	int plumCount   = 3;
	//	int peachCount  = 6;
	//	int melonCount  = 5;
	//	int grapesCount = 4;
	//	int sevensCount = 1;

	// 85%
	//		int lemonCount = 7;
	//		int cherryCount = 5;
	//		int orangeCount = 3;
	//		int plumCount = 2;
	//		int peachCount = 5;
	//		int melonCount = 5;
	//		int grapesCount = 4;
	//		int sevensCount = 1;

	// Initialize rows
	int ROW1 = 0;
	int ROW2 = 1;
	int ROW3 = 2;
	int ROW4 = 3;

	// Initialize Line Count and Lines
	int                lineCount    = 40;
	int[][]            lines        = new int[lineCount][5];
	ArrayList<Integer> linesWonList = new ArrayList<>();
	int                linesWon     = 0;

	// Timers
	int spinCountDown = 0;

	// Other Components
	ReelManager reelManager;

	// Reel
	ArrayList reel;

	// Text Components
	boolean win_counting_up         = false;
	int     win_portion_divider     = 100;
	String  win_last_win_text       = "LAST WIN";
	double  win_last_win_amount     = 0;
	double  new_win_last_win_amount = 0;

	// Buttons
	Button spinButton;
	Button quickSpinButton;
	Button increaseBetButton;
	Button decreaseBetButton;
	Button volumeButton;
	Button infoButton;
	Button musicButton;
	Button autoSpinButton;

	// Autospin Booleans
	boolean autoSpinEnabled = false;

	// QuickSpin Booleans
	boolean quickSpinEnabled = false;

	// Volume Booleans
	boolean volumeEnabled = Config.volumeEnabled;
	boolean musicEnabled  = Config.startupMusicEnabled;

	// Formatting Numbers (Thousands + 2 Decimal Places)
	DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");

	// -------------------------------------- Keyboard Stuff Start
	// Keyboard
	boolean w_key = false;
	boolean s_key = false;
	boolean d_key = false;
	boolean a_key = false, a_key_just_pressed = false;
	boolean q_key = false, q_key_just_pressed = false;
	boolean up_key    = false;
	boolean down_key  = false;
	boolean left_key  = false;
	boolean right_key = false;
	boolean shift_key = false, shift_key_just_pressed = false;
	boolean ctrl_key = false, ctrl_key_just_pressed = false;
	boolean alt_key = false, alt_key_just_pressed = false;
	boolean space_key = false, space_key_just_pressed = false;
	boolean r_key = false, r_key_just_pressed = false;
	boolean f1_key = false, f1_key_just_pressed = false;
	boolean f2_key = false, f2_key_just_pressed = false;
	boolean f3_key = false, f3_key_just_pressed = false;
	boolean f4_key = false, f4_key_just_pressed = false;
	boolean f5_key = false, f5_key_just_pressed = false;
	boolean f6_key = false, f6_key_just_pressed = false;
	boolean f7_key = false, f7_key_just_pressed = false;
	boolean f8_key = false, f8_key_just_pressed = false;
	boolean f9_key = false, f9_key_just_pressed = false;
	boolean f10_key = false, f10_key_just_pressed = false;
	boolean f11_key = false, f11_key_just_pressed = false;
	boolean f12_key = false, f12_key_just_pressed = false;
	// -------------------------------------- Keyboard Stuff End

	// -------------------------------------- Controller Stuff Start
	ControllerState controller;
	boolean         start_btn         = false;
	boolean         back_btn          = false;
	boolean         dpadUp_btn        = false;
	boolean         dpadDown_btn      = false;
	boolean         dpadLeft_btn      = false;
	boolean         dpadRight_btn     = false;
	boolean         a_btn             = false;
	boolean         b_btn             = false;
	boolean         x_btn             = false;
	boolean         y_btn             = false;
	boolean         lb_btn            = false;
	boolean         leftTrigger_btn   = false;
	boolean         rb_btn            = false;
	boolean         rightTrigger_btn  = false;
	boolean         leftStick_btn     = false; // left stick
	boolean         leftStickY_up     = false;
	boolean         leftStickY_down   = false;
	boolean         leftStickX_left   = false;
	boolean         leftStickX_right  = false;
	boolean         rightStick_btn    = false; // right stick
	boolean         rightStickY_up    = false;
	boolean         rightStickY_down  = false;
	boolean         rightStickX_left  = false;
	boolean         rightStickX_right = false;

	// Controller Just Pressed
	boolean start_btn_just_pressed      = false;
	boolean back_btn_just_pressed       = false;
	boolean dpadUp_btn_just_pressed     = false;
	boolean dpadDown_btn_just_pressed   = false;
	boolean dpadLeft_btn_just_pressed   = false;
	boolean dpadRight_btn_just_pressed  = false;
	boolean a_btn_just_pressed          = false;
	boolean b_btn_just_pressed          = false;
	boolean x_btn_just_pressed          = false;
	boolean y_btn_just_pressed          = false;
	boolean lb_btn_just_pressed         = false;
	boolean rb_btn_just_pressed         = false;
	boolean leftStick_btn_just_pressed  = false;
	boolean rightStick_btn_just_pressed = false;
	// -------------------------------------- Controller Stuff End

	SlotMachine(Game game) {
		this.game = game;
	}

	public void initialize() {
		if(initialized)
			return;

		// Generate Reel
		generateReel();

		// Initialize lines .. [lines][positions][row,position]
		initializeLines();

		if(Config.simulationEnabled) {
			initialized = true;
			return;
		}

		// Start Game Music
		if(volumeEnabled) {
			if(musicEnabled) {
				game.res.themesong.getSound().play(1f, 1f, true);
			}
		}

		// Other
		reelManager = new ReelManager(game, this);

		// Display Stuff
		autoSpinButton = new Button(game, 1151, 184, 86, 86, game.res.autoSpinOnButton.getPath(), game.res.autoSpinOnButton.getPath());
		if(!autoSpinEnabled) autoSpinButton.setImages(game.res.autoSpinOffButton.getPath(), game.res.autoSpinOffButton.getPath());
		autoSpinButton.setOffsets(2, 2);

		// Spin Button
		spinButton = new Button(game, 1096, 283, 201, 201, game.res.spinButtonReady.getPath(), game.res.spinButtonReady.getPath());
		spinButton.setOffsets(2, 2);

		// Quickspin Button
		quickSpinButton = new Button(game, 1151, 500, 86, 86, game.res.quickSpinButtonOn.getPath(), game.res.quickSpinButtonOn.getPath());
		if(!quickSpinEnabled) quickSpinButton.setImages(game.res.quickSpinButtonOff.getPath(), game.res.quickSpinButtonOff.getPath());
		quickSpinButton.setOffsets(2, 2);

		// Increase Decrease Bet Buttons
		increaseBetButton = new Button(game, 902, 696, 58, 57, game.res.increaseBetButton.getPath(), game.res.increaseBetButton.getPath());
		increaseBetButton.setOffsets(2, 2);
		decreaseBetButton = new Button(game, 838, 696, 57, 56, game.res.decreaseBetButton.getPath(), game.res.decreaseBetButton.getPath());
		decreaseBetButton.setOffsets(2, 2);

		// optionsOffset - For Buttons
		int optionsOffset = 35;

		// Music Button
		musicButton = new Button(game, 1058 + optionsOffset, 640, 57, 57, game.res.musicOnButton.getPath(), game.res.musicOnButton.getPath());
		if(!musicEnabled) musicButton.setImages(game.res.musicOffButton.getPath(), game.res.musicOffButton.getPath());
		musicButton.setOffsets(2, 2);

		// Volume Button
		volumeButton = new Button(game, 1132 + optionsOffset, 640, 58, 58, game.res.volumeOnButton.getPath(), game.res.volumeOnButton.getPath());
		if(!volumeEnabled) volumeButton.setImages(game.res.volumeOffButton.getPath(), game.res.volumeOffButton.getPath());
		volumeButton.setOffsets(2, 2);

		// Info Button
		infoButton = new Button(game, 1206 + optionsOffset, 640, 57, 57, game.res.infoButton.getPath(), game.res.infoButton.getPath());
		infoButton.setOffsets(2, 2);

		// Finished Initialization
		initialized = true;
	}

	public void generateReel() {
		ArrayList reel = new ArrayList();
		for(int i = 0; i < lemonCount; i++)
			reel.add(LEMON);
		for(int i = 0; i < cherryCount; i++)
			reel.add(CHERRY);
		for(int i = 0; i < orangeCount; i++)
			reel.add(ORANGE);
		for(int i = 0; i < plumCount; i++)
			reel.add(PLUM);
		for(int i = 0; i < peachCount; i++)
			reel.add(PEACH);
		for(int i = 0; i < melonCount; i++)
			reel.add(MELON);
		for(int i = 0; i < grapesCount; i++)
			reel.add(GRAPES);
		for(int i = 0; i < sevensCount; i++)
			reel.add(SEVEN);

		//Collections.shuffle(reel); // unseeded
		Collections.shuffle(reel, new Random(0)); // seeded

		this.reel = reel;

		// Print Reel
		printReel();
	}

	public void printReel() {
		for(int k = 0; k < this.reel.size(); k++) {
			Integer element = (Integer)this.reel.get(k);
			System.out.println("Index: " + k + ", Value: " + element);
		}
	}

	// Core Functions
	public String[] spin() {
		String[] rows = new String[4];

		Random rand      = new Random();
		int    randomIndex;
		int    reelCount = 5;

		// ------------- New Method Start
		/*
					 0 1 2 3 4
				0 [0,0,0,0,0]
				1 [1,1,1,1,1]
				2 [2,2,2,2,2]
				3 [3,3,3,3,3]
		*/
		String row0 = "";
		String row1 = "";
		String row2 = "";
		String row3 = "";
		for(int i = 0; i < reelCount; i++) {
			randomIndex = rand.nextInt(this.reel.size());
			row0 += String.valueOf(this.reel.get(randomIndex));
			row1 += String.valueOf(this.reel.get((randomIndex + 1) % this.reel.size()));
			row2 += String.valueOf(this.reel.get((randomIndex + 2) % this.reel.size()));
			row3 += String.valueOf(this.reel.get((randomIndex + 3) % this.reel.size()));
		}
		rows[0] = row0;
		rows[1] = row1;
		rows[2] = row2;
		rows[3] = row3;

		return rows;
	}

	public void playerSpin() {
		// Handle winLimit
		if(winLimit == 0) {
			return;
		}

		// Display Stuff
		win_last_win_text = "LAST WIN";
		if(new_win_last_win_amount > 0) {
			win_last_win_amount = new_win_last_win_amount;
		}

		// Work Stuff...
		linesWonList = new ArrayList<>();
		linesWon = 0;
		if((credit - betAmount) < 0) {
			System.out.println("Not Enough Credit");
			return;
		}

		// Update Win Limit
		winLimit += betAmount;
		winLimitDisplay = winLimit;

		// Sounds
		if(volumeEnabled) {
			game.res.spinSound.getSound().play(1f, 1f, true);
		}

		// Update Credit Display
		creditDisplay = credit;
		// Stop coin sounds...
		if(game.res.coinDropSoundLong.getSound().isPlaying()) game.res.coinDropSoundLong.getSound().stop();

		// Deduct from credit
		credit -= betAmount;

		// Set Actual Bet Amount
		double actualBetAmount = betAmount;

		// Initialize Total Win
		double totalWin = 0;

		// Get Spin Result
		String result[] = spin();
		spinResult = result;

		// Initialize Symbol
		int symbol;

		// Initialize multipliers
		double multiplier1;
		double multiplier2;
		double multiplier3;
		double multiplier4;

		// Initialize Positions
		int[] position1;
		int[] position2;
		int[] position3;
		int[] position4;
		int[] position5;

		// Check Lines and add winnings to totalWin
		for(int lineIndex = 0; lineIndex < lines.length; lineIndex++) {
			// Positions Line 1 .. [row]
			position1 = new int[]{lines[lineIndex][0]};
			position2 = new int[]{lines[lineIndex][1]};
			position3 = new int[]{lines[lineIndex][2]};
			position4 = new int[]{lines[lineIndex][3]};
			position5 = new int[]{lines[lineIndex][4]};

			// Lemon
			symbol = LEMON;
			multiplier1 = M_LEMON3;
			multiplier2 = M_LEMON4;
			multiplier3 = M_LEMON5;
			if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) == symbol && Character.getNumericValue(result[position5[0]].charAt(4)) == symbol) {
				// $$$$$
				totalWin += actualBetAmount * multiplier3;
				linesWon++;
				linesWonList.add(lineIndex);
			}
			else if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) == symbol && Character.getNumericValue(result[position5[0]].charAt(4)) != symbol) {
				// $$$$#
				totalWin += actualBetAmount * multiplier2;
				linesWon++;
				linesWonList.add(lineIndex);
			}
			else if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) != symbol && Character.getNumericValue(result[position5[0]].charAt(4)) == symbol) {
				// $$$#$
				totalWin += actualBetAmount * multiplier1;
				linesWon++;
				linesWonList.add(lineIndex);
			}
			else if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) != symbol && Character.getNumericValue(result[position5[0]].charAt(4)) != symbol) {
				// $$$##
				totalWin += actualBetAmount * multiplier1;
				linesWon++;
				linesWonList.add(lineIndex);
			}

			// Cherry
			symbol = CHERRY;
			multiplier1 = M_CHERRY3;
			multiplier2 = M_CHERRY4;
			multiplier3 = M_CHERRY5;
			if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) == symbol && Character.getNumericValue(result[position5[0]].charAt(4)) == symbol) {
				// $$$$$
				totalWin += actualBetAmount * multiplier3;
				linesWon++;
				linesWonList.add(lineIndex);
			}
			else if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) == symbol && Character.getNumericValue(result[position5[0]].charAt(4)) != symbol) {
				// $$$$#
				totalWin += actualBetAmount * multiplier2;
				linesWon++;
				linesWonList.add(lineIndex);
			}
			else if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) != symbol && Character.getNumericValue(result[position5[0]].charAt(4)) == symbol) {
				// $$$#$
				totalWin += actualBetAmount * multiplier1;
				linesWon++;
				linesWonList.add(lineIndex);
			}
			else if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) != symbol && Character.getNumericValue(result[position5[0]].charAt(4)) != symbol) {
				// $$$##
				totalWin += actualBetAmount * multiplier1;
				linesWon++;
				linesWonList.add(lineIndex);
			}

			// Orange
			symbol = ORANGE;
			multiplier1 = M_ORANGE3;
			multiplier2 = M_ORANGE4;
			multiplier3 = M_ORANGE5;
			if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) == symbol && Character.getNumericValue(result[position5[0]].charAt(4)) == symbol) {
				// $$$$$
				totalWin += actualBetAmount * multiplier3;
				linesWon++;
				linesWonList.add(lineIndex);
			}
			else if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) == symbol && Character.getNumericValue(result[position5[0]].charAt(4)) != symbol) {
				// $$$$#
				totalWin += actualBetAmount * multiplier2;
				linesWon++;
				linesWonList.add(lineIndex);
			}
			else if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) != symbol && Character.getNumericValue(result[position5[0]].charAt(4)) == symbol) {
				// $$$#$
				totalWin += actualBetAmount * multiplier1;
				linesWon++;
				linesWonList.add(lineIndex);
			}
			else if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) != symbol && Character.getNumericValue(result[position5[0]].charAt(4)) != symbol) {
				// $$$##
				totalWin += actualBetAmount * multiplier1;
				linesWon++;
				linesWonList.add(lineIndex);
			}

			// Plum
			symbol = PLUM;
			multiplier1 = M_PLUM3;
			multiplier2 = M_PLUM4;
			multiplier3 = M_PLUM5;
			if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) == symbol && Character.getNumericValue(result[position5[0]].charAt(4)) == symbol) {
				// $$$$$
				totalWin += actualBetAmount * multiplier3;
				linesWon++;
				linesWonList.add(lineIndex);
			}
			else if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) == symbol && Character.getNumericValue(result[position5[0]].charAt(4)) != symbol) {
				// $$$$#
				totalWin += actualBetAmount * multiplier2;
				linesWon++;
				linesWonList.add(lineIndex);
			}
			else if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) != symbol && Character.getNumericValue(result[position5[0]].charAt(4)) == symbol) {
				// $$$#$
				totalWin += actualBetAmount * multiplier1;
				linesWon++;
				linesWonList.add(lineIndex);
			}
			else if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) != symbol && Character.getNumericValue(result[position5[0]].charAt(4)) != symbol) {
				// $$$##
				totalWin += actualBetAmount * multiplier1;
				linesWon++;
				linesWonList.add(lineIndex);
			}

			// PEACH
			symbol = PEACH;
			multiplier1 = M_PEACH3;
			multiplier2 = M_PEACH4;
			multiplier3 = M_PEACH5;
			if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) == symbol && Character.getNumericValue(result[position5[0]].charAt(4)) == symbol) {
				// $$$$$
				totalWin += actualBetAmount * multiplier3;
				linesWon++;
				linesWonList.add(lineIndex);
			}
			else if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) == symbol && Character.getNumericValue(result[position5[0]].charAt(4)) != symbol) {
				// $$$$#
				totalWin += actualBetAmount * multiplier2;
				linesWon++;
				linesWonList.add(lineIndex);
			}
			else if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) != symbol && Character.getNumericValue(result[position5[0]].charAt(4)) == symbol) {
				// $$$#$
				totalWin += actualBetAmount * multiplier1;
				linesWon++;
				linesWonList.add(lineIndex);
			}
			else if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) != symbol && Character.getNumericValue(result[position5[0]].charAt(4)) != symbol) {
				// $$$##
				totalWin += actualBetAmount * multiplier1;
				linesWon++;
				linesWonList.add(lineIndex);
			}

			// MELON
			symbol = MELON;
			multiplier1 = M_MELON3;
			multiplier2 = M_MELON4;
			multiplier3 = M_MELON5;
			if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) == symbol && Character.getNumericValue(result[position5[0]].charAt(4)) == symbol) {
				// $$$$$
				totalWin += actualBetAmount * multiplier3;
				linesWon++;
				linesWonList.add(lineIndex);
			}
			else if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) == symbol && Character.getNumericValue(result[position5[0]].charAt(4)) != symbol) {
				// $$$$#
				totalWin += actualBetAmount * multiplier2;
				linesWon++;
				linesWonList.add(lineIndex);
			}
			else if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) != symbol && Character.getNumericValue(result[position5[0]].charAt(4)) == symbol) {
				// $$$#$
				totalWin += actualBetAmount * multiplier1;
				linesWon++;
				linesWonList.add(lineIndex);
			}
			else if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) != symbol && Character.getNumericValue(result[position5[0]].charAt(4)) != symbol) {
				// $$$##
				totalWin += actualBetAmount * multiplier1;
				linesWon++;
				linesWonList.add(lineIndex);
			}

			// GRAPES
			symbol = GRAPES;
			multiplier1 = M_GRAPES3;
			multiplier2 = M_GRAPES4;
			multiplier3 = M_GRAPES5;
			if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) == symbol && Character.getNumericValue(result[position5[0]].charAt(4)) == symbol) {
				// $$$$$
				totalWin += actualBetAmount * multiplier3;
				linesWon++;
				linesWonList.add(lineIndex);
			}
			else if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) == symbol && Character.getNumericValue(result[position5[0]].charAt(4)) != symbol) {
				// $$$$#
				totalWin += actualBetAmount * multiplier2;
				linesWon++;
				linesWonList.add(lineIndex);
			}
			else if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) != symbol && Character.getNumericValue(result[position5[0]].charAt(4)) == symbol) {
				// $$$#$
				totalWin += actualBetAmount * multiplier1;
				linesWon++;
				linesWonList.add(lineIndex);
			}
			else if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) != symbol && Character.getNumericValue(result[position5[0]].charAt(4)) != symbol) {
				// $$$##
				totalWin += actualBetAmount * multiplier1;
				linesWon++;
				linesWonList.add(lineIndex);
			}

			// SEVEN
			symbol = SEVEN;
			multiplier1 = M_SEVEN2;
			multiplier2 = M_SEVEN3;
			multiplier3 = M_SEVEN4;
			multiplier4 = M_SEVEN5;
			if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) == symbol && Character.getNumericValue(result[position5[0]].charAt(4)) == symbol) {
				// $$$$$
				totalWin += actualBetAmount * multiplier4;
				linesWon++;
				linesWonList.add(lineIndex);
			}
			else if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) == symbol && Character.getNumericValue(result[position5[0]].charAt(4)) != symbol) {
				// $$$$#
				totalWin += actualBetAmount * multiplier3;
				linesWon++;
				linesWonList.add(lineIndex);
			}
			else if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) != symbol && Character.getNumericValue(result[position5[0]].charAt(4)) == symbol) {
				// $$$#$
				totalWin += actualBetAmount * multiplier2;
				linesWon++;
				linesWonList.add(lineIndex);
			}
			else if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) != symbol && Character.getNumericValue(result[position5[0]].charAt(4)) != symbol) {
				// $$$##
				totalWin += actualBetAmount * multiplier2;
				linesWon++;
				linesWonList.add(lineIndex);
			}
			else if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) != symbol && Character.getNumericValue(result[position4[0]].charAt(3)) != symbol && Character.getNumericValue(result[position5[0]].charAt(4)) == symbol) {
				// $$##$
				totalWin += actualBetAmount * multiplier1;
				linesWon++;
				linesWonList.add(lineIndex);
			}
			else if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) != symbol && Character.getNumericValue(result[position4[0]].charAt(3)) == symbol && Character.getNumericValue(result[position5[0]].charAt(4)) != symbol) {
				// $$#$#
				totalWin += actualBetAmount * multiplier1;
				linesWon++;
				linesWonList.add(lineIndex);
			}
			else if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) != symbol && Character.getNumericValue(result[position4[0]].charAt(3)) != symbol && Character.getNumericValue(result[position5[0]].charAt(4)) != symbol) {
				// $$###
				totalWin += actualBetAmount * multiplier1;
				linesWon++;
				linesWonList.add(lineIndex);
			}
		}

		// Handle winLimit
		if(totalWin > winLimit) {
			totalWin = winLimit;
		}

		totalWin = Math.round(totalWin * 100.0) / 100.0;
		credit += totalWin;
		credit = Math.round(credit * 100.0) / 100.0;

		displayRows(result);
		if(totalWin > 0) {
			System.out.println("Credit: R" + credit + " .. Total Bet: R" + Math.round(actualBetAmount * 100.0) / 100.0 + " .. You Won: R" + totalWin + " .. Lines Won: " + linesWon);

			// Update Display Stuff
			new_win_last_win_amount = totalWin;
			win_counting_up = true;

			// Update winLimit
			winLimit -= totalWin;
		}
		else {
			System.out.println("Credit: R" + credit + " .. Total Bet: R" + Math.round(actualBetAmount * 100.0) / 100.0);
		}

		// Do Display Work....
		// Start Spinning in Reel Manager
		spinning = true;

		// Set spinCountDown
		if(quickSpinEnabled) {
			int randomNum = new Random().nextInt((Config.maxQuickSpinTime - Config.minQuickSpinTime) + 1);
			spinCountDown = Config.minQuickSpinTime + randomNum;
		}
		else {
			int randomNum = new Random().nextInt((Config.maxSpinTime - Config.minSpinTime) + 1);
			spinCountDown = Config.minSpinTime + randomNum;
		}

		// Update Credit Display
		creditDisplay -= betAmount;
	}

	public void loadCredit(double amount) {
		credit += amount;
		creditDisplay = credit;
		System.out.println("Added R" + amount);
		displayBalanceAndBetAmount();
	}

	public void resetCredit() {
		credit = 0;
		creditDisplay = credit;
	}

	public void displayBalanceAndBetAmount() {
		System.out.println("Credit: R" + credit + " .. Bet Amount: R" + betAmount);
	}

	public void simulate() {
		System.out.println("Running");

		double totalStake = 0;
		double totalWin   = 0;

		// Set Actual Bet Amount
		double actualBetAmount = Config.simBetAmount;
		for(int i = 0; i < Config.simSpinCount; i++) {
			totalStake += simBetAmount;
			String result[] = spin();

			// Initialize Symbol
			int symbol;

			// Initialize multipliers
			double multiplier1;
			double multiplier2;
			double multiplier3;
			double multiplier4;

			// Initialize rows
			int ROW1 = 0;
			int ROW2 = 1;
			int ROW3 = 2;
			int ROW4 = 3;

			// Initialize Positions
			int[] position1;
			int[] position2;
			int[] position3;
			int[] position4;
			int[] position5;

			// Initialize lines .. [lines][positions][row,position]
			// ##### - ROW1
			// ##### - ROW2
			// ##### - ROW3
			// ##### - ROW4
			int[][] lines = new int[40][5];
			lines[0] = new int[]{ROW1, ROW1, ROW1, ROW1, ROW1}; // 1
			lines[1] = new int[]{ROW1, ROW2, ROW2, ROW2, ROW2}; // 2
			lines[2] = new int[]{ROW1, ROW1, ROW1, ROW1, ROW2}; // 3
			lines[3] = new int[]{ROW1, ROW1, ROW1, ROW2, ROW3}; // 4
			lines[4] = new int[]{ROW1, ROW2, ROW3, ROW2, ROW1}; // 5
			lines[5] = new int[]{ROW1, ROW1, ROW2, ROW1, ROW1}; // 6
			lines[6] = new int[]{ROW1, ROW2, ROW2, ROW2, ROW1}; // 7
			lines[7] = new int[]{ROW1, ROW2, ROW3, ROW3, ROW3}; // 8
			lines[8] = new int[]{ROW2, ROW2, ROW2, ROW2, ROW1}; // 9
			lines[9] = new int[]{ROW2, ROW1, ROW1, ROW1, ROW2}; // 10
			lines[10] = new int[]{ROW2, ROW2, ROW1, ROW2, ROW2}; // 11
			lines[11] = new int[]{ROW2, ROW3, ROW3, ROW3, ROW3}; // 12
			lines[12] = new int[]{ROW2, ROW2, ROW2, ROW2, ROW2}; // 13
			lines[13] = new int[]{ROW2, ROW1, ROW1, ROW1, ROW1}; // 14
			lines[14] = new int[]{ROW2, ROW2, ROW2, ROW2, ROW3}; // 15
			lines[15] = new int[]{ROW2, ROW3, ROW4, ROW4, ROW4}; // 16
			lines[16] = new int[]{ROW2, ROW2, ROW3, ROW2, ROW2}; // 17
			lines[17] = new int[]{ROW2, ROW2, ROW2, ROW3, ROW4}; // 18
			lines[18] = new int[]{ROW2, ROW3, ROW4, ROW3, ROW2}; // 19
			lines[19] = new int[]{ROW2, ROW3, ROW3, ROW3, ROW2}; // 20
			lines[20] = new int[]{ROW3, ROW3, ROW3, ROW2, ROW1}; // 21
			lines[21] = new int[]{ROW3, ROW2, ROW2, ROW2, ROW3}; // 22
			lines[22] = new int[]{ROW3, ROW3, ROW2, ROW3, ROW3}; // 23
			lines[23] = new int[]{ROW3, ROW2, ROW1, ROW2, ROW3}; // 24
			lines[24] = new int[]{ROW3, ROW3, ROW3, ROW3, ROW2}; // 25
			lines[25] = new int[]{ROW3, ROW2, ROW1, ROW1, ROW1}; // 26
			lines[26] = new int[]{ROW3, ROW3, ROW3, ROW3, ROW3}; // 27
			lines[27] = new int[]{ROW3, ROW2, ROW2, ROW2, ROW2}; // 28
			lines[28] = new int[]{ROW3, ROW3, ROW4, ROW3, ROW3}; // 29
			lines[29] = new int[]{ROW3, ROW4, ROW4, ROW4, ROW4}; // 30
			lines[30] = new int[]{ROW3, ROW3, ROW3, ROW3, ROW4}; // 31
			lines[31] = new int[]{ROW3, ROW4, ROW4, ROW4, ROW3}; // 32
			lines[32] = new int[]{ROW4, ROW4, ROW3, ROW4, ROW4}; // 33
			lines[33] = new int[]{ROW4, ROW3, ROW3, ROW3, ROW4}; // 34
			lines[34] = new int[]{ROW4, ROW4, ROW4, ROW3, ROW2}; // 35
			lines[35] = new int[]{ROW4, ROW3, ROW2, ROW3, ROW4}; // 36
			lines[36] = new int[]{ROW4, ROW4, ROW4, ROW4, ROW3}; // 37
			lines[37] = new int[]{ROW4, ROW3, ROW3, ROW3, ROW3}; // 38
			lines[38] = new int[]{ROW4, ROW3, ROW2, ROW2, ROW2}; // 39
			lines[39] = new int[]{ROW4, ROW4, ROW4, ROW4, ROW4}; // 40

			for(int lineIndex = 0; lineIndex < lines.length; lineIndex++) {
				// Positions Line 1 .. [row]
				position1 = new int[]{lines[lineIndex][0]};
				position2 = new int[]{lines[lineIndex][1]};
				position3 = new int[]{lines[lineIndex][2]};
				position4 = new int[]{lines[lineIndex][3]};
				position5 = new int[]{lines[lineIndex][4]};

				// Lemon
				symbol = LEMON;
				multiplier1 = M_LEMON3;
				multiplier2 = M_LEMON4;
				multiplier3 = M_LEMON5;

				if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) == symbol && Character.getNumericValue(result[position5[0]].charAt(4)) == symbol) {
					// $$$$$
					totalWin += actualBetAmount * multiplier3;
				}
				else if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) == symbol && Character.getNumericValue(result[position5[0]].charAt(4)) != symbol) {
					// $$$$#
					totalWin += actualBetAmount * multiplier2;
				}
				else if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) != symbol && Character.getNumericValue(result[position5[0]].charAt(4)) == symbol) {
					// $$$#$
					totalWin += actualBetAmount * multiplier1;
				}
				else if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) != symbol && Character.getNumericValue(result[position5[0]].charAt(4)) != symbol) {
					// $$$##
					totalWin += actualBetAmount * multiplier1;
				}

				// Cherry
				symbol = CHERRY;
				multiplier1 = M_CHERRY3;
				multiplier2 = M_CHERRY4;
				multiplier3 = M_CHERRY5;

				if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) == symbol && Character.getNumericValue(result[position5[0]].charAt(4)) == symbol) {
					// $$$$$
					totalWin += actualBetAmount * multiplier3;
				}
				else if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) == symbol && Character.getNumericValue(result[position5[0]].charAt(4)) != symbol) {
					// $$$$#
					totalWin += actualBetAmount * multiplier2;
				}
				else if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) != symbol && Character.getNumericValue(result[position5[0]].charAt(4)) == symbol) {
					// $$$#$
					totalWin += actualBetAmount * multiplier1;
				}
				else if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) != symbol && Character.getNumericValue(result[position5[0]].charAt(4)) != symbol) {
					// $$$##
					totalWin += actualBetAmount * multiplier1;
				}

				// Orange
				symbol = ORANGE;
				multiplier1 = M_ORANGE3;
				multiplier2 = M_ORANGE4;
				multiplier3 = M_ORANGE5;
				if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) == symbol && Character.getNumericValue(result[position5[0]].charAt(4)) == symbol) {
					// $$$$$
					totalWin += actualBetAmount * multiplier3;
				}
				else if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) == symbol && Character.getNumericValue(result[position5[0]].charAt(4)) != symbol) {
					// $$$$#
					totalWin += actualBetAmount * multiplier2;
				}
				else if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) != symbol && Character.getNumericValue(result[position5[0]].charAt(4)) == symbol) {
					// $$$#$
					totalWin += actualBetAmount * multiplier1;
				}
				else if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) != symbol && Character.getNumericValue(result[position5[0]].charAt(4)) != symbol) {
					// $$$##
					totalWin += actualBetAmount * multiplier1;
				}

				// Plum
				symbol = PLUM;
				multiplier1 = M_PLUM3;
				multiplier2 = M_PLUM4;
				multiplier3 = M_PLUM5;
				if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) == symbol && Character.getNumericValue(result[position5[0]].charAt(4)) == symbol) {
					// $$$$$
					totalWin += actualBetAmount * multiplier3;
				}
				else if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) == symbol && Character.getNumericValue(result[position5[0]].charAt(4)) != symbol) {
					// $$$$#
					totalWin += actualBetAmount * multiplier2;
				}
				else if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) != symbol && Character.getNumericValue(result[position5[0]].charAt(4)) == symbol) {
					// $$$#$
					totalWin += actualBetAmount * multiplier1;
				}
				else if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) != symbol && Character.getNumericValue(result[position5[0]].charAt(4)) != symbol) {
					// $$$##
					totalWin += actualBetAmount * multiplier1;
				}

				// PEACH
				symbol = PEACH;
				multiplier1 = M_PEACH3;
				multiplier2 = M_PEACH4;
				multiplier3 = M_PEACH5;
				if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) == symbol && Character.getNumericValue(result[position5[0]].charAt(4)) == symbol) {
					// $$$$$
					totalWin += actualBetAmount * multiplier3;
				}
				else if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) == symbol && Character.getNumericValue(result[position5[0]].charAt(4)) != symbol) {
					// $$$$#
					totalWin += actualBetAmount * multiplier2;
				}
				else if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) != symbol && Character.getNumericValue(result[position5[0]].charAt(4)) == symbol) {
					// $$$#$
					totalWin += actualBetAmount * multiplier1;
				}
				else if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) != symbol && Character.getNumericValue(result[position5[0]].charAt(4)) != symbol) {
					// $$$##
					totalWin += actualBetAmount * multiplier1;
				}

				// MELON
				symbol = MELON;
				multiplier1 = M_MELON3;
				multiplier2 = M_MELON4;
				multiplier3 = M_MELON5;
				if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) == symbol && Character.getNumericValue(result[position5[0]].charAt(4)) == symbol) {
					// $$$$$
					totalWin += actualBetAmount * multiplier3;
				}
				else if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) == symbol && Character.getNumericValue(result[position5[0]].charAt(4)) != symbol) {
					// $$$$#
					totalWin += actualBetAmount * multiplier2;
				}
				else if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) != symbol && Character.getNumericValue(result[position5[0]].charAt(4)) == symbol) {
					// $$$#$
					totalWin += actualBetAmount * multiplier1;
				}
				else if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) != symbol && Character.getNumericValue(result[position5[0]].charAt(4)) != symbol) {
					// $$$##
					totalWin += actualBetAmount * multiplier1;
				}

				// GRAPES
				symbol = GRAPES;
				multiplier1 = M_GRAPES3;
				multiplier2 = M_GRAPES4;
				multiplier3 = M_GRAPES5;
				if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) == symbol && Character.getNumericValue(result[position5[0]].charAt(4)) == symbol) {
					// $$$$$
					totalWin += actualBetAmount * multiplier3;
				}
				else if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) == symbol && Character.getNumericValue(result[position5[0]].charAt(4)) != symbol) {
					// $$$$#
					totalWin += actualBetAmount * multiplier2;
				}
				else if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) != symbol && Character.getNumericValue(result[position5[0]].charAt(4)) == symbol) {
					// $$$#$
					totalWin += actualBetAmount * multiplier1;
				}
				else if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) != symbol && Character.getNumericValue(result[position5[0]].charAt(4)) != symbol) {
					// $$$##
					totalWin += actualBetAmount * multiplier1;
				}

				// SEVEN
				symbol = SEVEN;
				multiplier1 = M_SEVEN2;
				multiplier2 = M_SEVEN3;
				multiplier3 = M_SEVEN4;
				multiplier4 = M_SEVEN5;
				if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) == symbol && Character.getNumericValue(result[position5[0]].charAt(4)) == symbol) {
					// $$$$$
					totalWin += actualBetAmount * multiplier4;
				}
				else if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) == symbol && Character.getNumericValue(result[position5[0]].charAt(4)) != symbol) {
					// $$$$#
					totalWin += actualBetAmount * multiplier3;
				}
				else if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) != symbol && Character.getNumericValue(result[position5[0]].charAt(4)) == symbol) {
					// $$$#$
					totalWin += actualBetAmount * multiplier2;
				}
				else if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) == symbol && Character.getNumericValue(result[position4[0]].charAt(3)) != symbol && Character.getNumericValue(result[position5[0]].charAt(4)) != symbol) {
					// $$$##
					totalWin += actualBetAmount * multiplier2;
				}
				else if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) != symbol && Character.getNumericValue(result[position4[0]].charAt(3)) != symbol && Character.getNumericValue(result[position5[0]].charAt(4)) == symbol) {
					// $$##$
					totalWin += actualBetAmount * multiplier1;
				}
				else if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) != symbol && Character.getNumericValue(result[position4[0]].charAt(3)) == symbol && Character.getNumericValue(result[position5[0]].charAt(4)) != symbol) {
					// $$#$#
					totalWin += actualBetAmount * multiplier1;
				}
				else if(Character.getNumericValue(result[position1[0]].charAt(0)) == symbol && Character.getNumericValue(result[position2[0]].charAt(1)) == symbol && Character.getNumericValue(result[position3[0]].charAt(2)) != symbol && Character.getNumericValue(result[position4[0]].charAt(3)) != symbol && Character.getNumericValue(result[position5[0]].charAt(4)) != symbol) {
					// $$###
					totalWin += actualBetAmount * multiplier1;
				}
			}

			System.out.println("Sim " + i + " complete.");
		}

		double RTP = (totalWin / totalStake) * 100;

		System.out.println("Total Stake: R" + toDecimal(totalStake));
		System.out.println("Total Win: R" + toDecimal(totalWin));
		System.out.println("RTP: " + toDecimal(RTP) + "%");
	}

	public void initializeLines() {
		// ##### - ROW1
		// ##### - ROW2
		// ##### - ROW3
		// ##### - ROW4
		lines[0] = new int[]{ROW1, ROW1, ROW1, ROW1, ROW1}; // 1
		lines[1] = new int[]{ROW1, ROW2, ROW2, ROW2, ROW2}; // 2
		lines[2] = new int[]{ROW1, ROW1, ROW1, ROW1, ROW2}; // 3
		lines[3] = new int[]{ROW1, ROW1, ROW1, ROW2, ROW3}; // 4
		lines[4] = new int[]{ROW1, ROW2, ROW3, ROW2, ROW1}; // 5
		lines[5] = new int[]{ROW1, ROW1, ROW2, ROW1, ROW1}; // 6
		lines[6] = new int[]{ROW1, ROW2, ROW2, ROW2, ROW1}; // 7
		lines[7] = new int[]{ROW1, ROW2, ROW3, ROW3, ROW3}; // 8
		lines[8] = new int[]{ROW2, ROW2, ROW2, ROW2, ROW1}; // 9
		lines[9] = new int[]{ROW2, ROW1, ROW1, ROW1, ROW2}; // 10
		lines[10] = new int[]{ROW2, ROW2, ROW1, ROW2, ROW2}; // 11
		lines[11] = new int[]{ROW2, ROW3, ROW3, ROW3, ROW3}; // 12
		lines[12] = new int[]{ROW2, ROW2, ROW2, ROW2, ROW2}; // 13
		lines[13] = new int[]{ROW2, ROW1, ROW1, ROW1, ROW1}; // 14
		lines[14] = new int[]{ROW2, ROW2, ROW2, ROW2, ROW3}; // 15
		lines[15] = new int[]{ROW2, ROW3, ROW4, ROW4, ROW4}; // 16
		lines[16] = new int[]{ROW2, ROW2, ROW3, ROW2, ROW2}; // 17
		lines[17] = new int[]{ROW2, ROW2, ROW2, ROW3, ROW4}; // 18
		lines[18] = new int[]{ROW2, ROW3, ROW4, ROW3, ROW2}; // 19
		lines[19] = new int[]{ROW2, ROW3, ROW3, ROW3, ROW2}; // 20
		lines[20] = new int[]{ROW3, ROW3, ROW3, ROW2, ROW1}; // 21
		lines[21] = new int[]{ROW3, ROW2, ROW2, ROW2, ROW3}; // 22
		lines[22] = new int[]{ROW3, ROW3, ROW2, ROW3, ROW3}; // 23
		lines[23] = new int[]{ROW3, ROW2, ROW1, ROW2, ROW3}; // 24
		lines[24] = new int[]{ROW3, ROW3, ROW3, ROW3, ROW2}; // 25
		lines[25] = new int[]{ROW3, ROW2, ROW1, ROW1, ROW1}; // 26
		lines[26] = new int[]{ROW3, ROW3, ROW3, ROW3, ROW3}; // 27
		lines[27] = new int[]{ROW3, ROW2, ROW2, ROW2, ROW2}; // 28
		lines[28] = new int[]{ROW3, ROW3, ROW4, ROW3, ROW3}; // 29
		lines[29] = new int[]{ROW3, ROW4, ROW4, ROW4, ROW4}; // 30
		lines[30] = new int[]{ROW3, ROW3, ROW3, ROW3, ROW4}; // 31
		lines[31] = new int[]{ROW3, ROW4, ROW4, ROW4, ROW3}; // 32
		lines[32] = new int[]{ROW4, ROW4, ROW3, ROW4, ROW4}; // 33
		lines[33] = new int[]{ROW4, ROW3, ROW3, ROW3, ROW4}; // 34
		lines[34] = new int[]{ROW4, ROW4, ROW4, ROW3, ROW2}; // 35
		lines[35] = new int[]{ROW4, ROW3, ROW2, ROW3, ROW4}; // 36
		lines[36] = new int[]{ROW4, ROW4, ROW4, ROW4, ROW3}; // 37
		lines[37] = new int[]{ROW4, ROW3, ROW3, ROW3, ROW3}; // 38
		lines[38] = new int[]{ROW4, ROW3, ROW2, ROW2, ROW2}; // 39
		lines[39] = new int[]{ROW4, ROW4, ROW4, ROW4, ROW4}; // 40
	}

	public void increaseBetAmount() {
		if(currentBetIndex < betAmounts.length - 1) {
			currentBetIndex++;
			betAmount = betAmounts[currentBetIndex];
			if(volumeEnabled) {
				if(game.res.buttonSound.getSound().isPlaying()) {
					game.res.buttonSound.getSound().stop();
				}
				game.res.buttonSound.getSound().play();
			}

			displayBalanceAndBetAmount();
		}
		else {
			System.out.println("Bet Amount Already At Maximum");
		}
	}

	public void decreaseBetAmount() {
		if(currentBetIndex != 0) {
			currentBetIndex--;
			betAmount = betAmounts[currentBetIndex];
			if(volumeEnabled) {
				if(game.res.buttonSound.getSound().isPlaying()) {
					game.res.buttonSound.getSound().stop();
				}
				game.res.buttonSound.getSound().play();
			}
			displayBalanceAndBetAmount();
		}
		else {
			System.out.println("Bet Amount Already At Minimum");
		}
	}

	public void increaseCount(int type) {
		if(type == LEMON)
			lemonCount++;
		if(type == CHERRY)
			cherryCount++;
		if(type == ORANGE)
			orangeCount++;
		if(type == PLUM)
			plumCount++;
		if(type == PEACH)
			peachCount++;
		if(type == MELON)
			melonCount++;
		if(type == GRAPES)
			grapesCount++;
		if(type == SEVEN)
			sevensCount++;
		displayCounts();
	}

	public void decreaseCount(int type) {
		if(type == LEMON)
			lemonCount--;
		if(type == CHERRY)
			cherryCount--;
		if(type == ORANGE)
			orangeCount--;
		if(type == PLUM)
			plumCount--;
		if(type == PEACH)
			peachCount--;
		if(type == MELON)
			melonCount--;
		if(type == GRAPES)
			grapesCount--;
		if(type == SEVEN)
			sevensCount--;
		displayCounts();
	}

	public void displayCounts() {
		System.out.println("LEMONS:" + lemonCount + " .. " + "CHERRY:" + cherryCount + " .. " + "ORANGE:" + orangeCount + " .. " + "PLUM:" + plumCount + " .. " + "PEACH:" + peachCount + " .. " + "MELON:" + melonCount + " .. " + "GRAPES:" + grapesCount + " .. " + "SEVEN:" + sevensCount + " .. ");
	}

	public void displayRows(String[] rows) {
		System.out.println("-----");
		for(String row : rows) {
			System.out.println(row);
		}

		if(linesWonList.size() > 0) {
			System.out.print("Lines: ");
			for(Integer i : linesWonList) {
				System.out.print(i + 1 + ", ");
			}
			System.out.println();
		}
	}

	public String toDecimal(double value) {
		DecimalFormat decimalFormat = new DecimalFormat("#.##");
		decimalFormat.setRoundingMode(RoundingMode.DOWN);
		return decimalFormat.format(value);
	}

	// Update & Render
	public void update() {
		// Simulation
		if(Config.simulationEnabled)
			return;

		// Controller
		if(Config.controllerEnabled)
			checkInputState();

		// Reset Keys Just Pressed
		resetJustPressedKeys();

		if(spinning) {
			if(spinCountDown > 0) {
				spinCountDown--;
				if(spinCountDown == 0) {
					stopSpinning();
				}
			}

			if(Config.stopSpinEnabled) {
				// Set spin button to 'stop' button
				spinButton.setImages(game.res.spinButtonStop.getPath(), game.res.spinButtonStop.getPath());
			}
			else {
				// Set spin button to 'inactive' button
				spinButton.setImages(game.res.spinButtonInactive.getPath(), game.res.spinButtonInactive.getPath());
			}
		}
		else {
			// Set spin button to 'idle' button
			spinButton.setImages(game.res.spinButtonReady.getPath(), game.res.spinButtonReady.getPath());

			// Win Last Win Amount
			if(new_win_last_win_amount > 0) {
				// Initialize win amount
				if(win_counting_up) {
					win_last_win_text = "WIN";
					win_last_win_amount = 0;
					win_counting_up = false;
				}

				// Initialize win_portion_divider
				if(new_win_last_win_amount >= (1 * betAmount)) win_portion_divider = 100;
				if(new_win_last_win_amount >= (2 * betAmount)) win_portion_divider = 200;
				if(new_win_last_win_amount >= (5 * betAmount)) win_portion_divider = 300;
				if(new_win_last_win_amount >= (10 * betAmount)) win_portion_divider = 400;
				if(new_win_last_win_amount >= (20 * betAmount)) win_portion_divider = 500;
				if(new_win_last_win_amount >= (50 * betAmount)) win_portion_divider = 600;
				if(new_win_last_win_amount >= (100 * betAmount)) win_portion_divider = 700;
				if(new_win_last_win_amount < 1) win_portion_divider = 10;

				// Coin Drop Sound
				if(!game.res.coinDropSoundLong.getSound().isPlaying() && volumeEnabled) {
					game.res.coinDropSoundLong.getSound().play(1f, 1f, true);
				}

				// Increment win_last_win_amount...
				double portion = new_win_last_win_amount / win_portion_divider;
				if((win_last_win_amount + portion) > new_win_last_win_amount) {
					portion = new_win_last_win_amount - win_last_win_amount;
				}

				win_last_win_amount += portion;
				winLimitDisplay -= portion;
			}

			// Set winLimit to absolute zero if less than 0.00
			if(winLimit <= 0.00) {
				winLimit = 0;
			}

			if(win_last_win_amount == new_win_last_win_amount) {
				new_win_last_win_amount = 0;

				// Update Credit Display
				creditDisplay = credit;

				// Update winLimitDisplay
				winLimitDisplay = winLimit;

				// Stop Coin Drop Sound
				if(game.res.coinDropSoundLong.getSound().isPlaying()) {
					game.res.coinDropSoundLong.getSound().stop();
				}
			}
		}

		reelManager.update();

		// Buttons
		spinButton.update();

		// AutoSpin
		if(autoSpinEnabled && !spinning && (new_win_last_win_amount == 0)) {
			if((credit - betAmount) > 0) {
				playerSpin();
			}
			else {
				toggleAutoSpin();
			}
		}
	}

	public void render(Graphics2D g) {
		// Background Graphics
		g.drawImage(game.res.background_full.getImage(), 0, 0, null);
		g.drawImage(game.res.background.getImage(), 0, 0, null);

		// Other
		if(Config.renderReelsEnabled) {
			reelManager.render(g);
		}

		// Overlay Graphics
		g.drawImage(game.res.background_art.getImage(), 0, 0, null);
		g.drawImage(game.res.gradient_overlay.getImage(), 0, 0, null);

		// Text
		g.drawImage(game.res.text_mockup.getImage(), 0, 0, null);

		// CREDIT
		drawText("CREDIT", 329, 638, new Color(204, 204, 204), "Century Gothic", Font.BOLD, 22, true, g);
		drawText("R" + decimalFormat.format(creditDisplay), 329, 685, Color.white, "Century Gothic", Font.BOLD, 50, true, g);

		// WIN / LAST WIN
		drawText(win_last_win_text, 590, 638, new Color(204, 204, 204), "Century Gothic", Font.BOLD, 22, true, g);
		drawText("R" + decimalFormat.format(win_last_win_amount), 590, 685, Color.white, "Century Gothic", Font.BOLD, 50, true, g);

		// Win Limit Reached
		if(winLimitDisplay == 0 && !win_counting_up) {
			drawText("WIN LIMIT REACHED - No More Spins Available.", 680, 605, Color.white, "Century Gothic", Font.BOLD, 22, true, g);
		}

		// BET AMOUNT
		drawText("BET", 894, 638, new Color(204, 204, 204), "Century Gothic", Font.BOLD, 22, true, g);
		drawText("R" + decimalFormat.format(betAmount), 894, 685, Color.white, "Century Gothic", Font.BOLD, 50, true, g);

		// WIN LIMIT
		drawText("WIN LIMIT", 165, 200 + 175, new Color(204, 204, 204), "Century Gothic", Font.BOLD, 22, true, g);
		drawText("R" + decimalFormat.format(winLimitDisplay), 165, 247 + 175, Color.white, "Century Gothic", Font.BOLD, 50, true, g);

		// Buttons
		autoSpinButton.render(g);
		quickSpinButton.render(g);
		spinButton.render(g);
		increaseBetButton.render(g);
		decreaseBetButton.render(g);
		volumeButton.render(g);
		musicButton.render(g);
		infoButton.render(g);
	}

	public void drawText(String text, int x, int y, Color color, String fontType, int fontWeight, int fontSize, boolean centered, Graphics2D g) {
		// Set the color...
		g.setColor(color);

		// Check if centered
		if(centered) {
			// Create a font and text
			Font font = new Font(fontType, fontWeight, fontSize);

			// Get the font metrics for the font
			FontMetrics metrics = g.getFontMetrics(font);

			// Calculate the size of the text
			int textWidth = metrics.stringWidth(text);

			// Adjust x coordinate
			x -= (textWidth / 2);

			// Set the font...
			g.setFont(font);
		}
		else {
			// Just set the font...
			g.setFont(new Font(fontType, fontWeight, fontSize));
		}

		// Draw it...
		g.drawString(text, x, y);
	}

	// Other Functions
	public void stopSpinning() {
		// spinResult is:
		// 0 ##### -->
		// 1 ##### -->
		// 2 ##### -->
		// 3 ##### -->

		// Update Static Symbols
		for(int i = 0; i < reelManager.reels.size(); i++) {
			for(int j = 0; j < reelManager.reels.get(i).symbolsStatic.size(); j++) {
				reelManager.reels.get(i).symbolsStatic.get(j).setType(Integer.parseInt(String.valueOf(spinResult[j].charAt(i))));
			}
		}
		spinning = false;

		if(!win_counting_up) {
			creditDisplay = credit;
		}

		// Sounds
		if(game.res.spinSound.getSound().isPlaying()) {
			game.res.spinSound.getSound().stop();
		}
	}

	// AutoSpin Functions
	public void toggleAutoSpin() {
		// WinLimit Check
		if(winLimit == 0) {
			return;
		}

		if(autoSpinEnabled) {
			autoSpinEnabled = false;
			autoSpinButton.setImages(game.res.autoSpinOffButton.getPath(), game.res.autoSpinOffButton.getPath());
		}
		else {
			autoSpinEnabled = true;
			autoSpinButton.setImages(game.res.autoSpinOnButton.getPath(), game.res.autoSpinOnButton.getPath());
		}

		if(autoSpinEnabled) {
			System.out.println("Autospin Enabled");
		}
		else {
			System.out.println("Autospin Disabled");
		}
	}

	// Quick Spin Functions
	public void toggleQuickSpin() {
		if(quickSpinEnabled) {
			quickSpinEnabled = false;
			quickSpinButton.setImages(game.res.quickSpinButtonOff.getPath(), game.res.quickSpinButtonOff.getPath());
		}
		else {
			quickSpinEnabled = true;
			quickSpinButton.setImages(game.res.quickSpinButtonOn.getPath(), game.res.quickSpinButtonOn.getPath());
		}
	}

	// Sound Functions
	public void toggleVolume() {
		if(volumeEnabled) {
			volumeEnabled = false;
			volumeButton.setImages(game.res.volumeOffButton.getPath(), game.res.volumeOffButton.getPath());
			if(game.res.spinSound.getSound().isPlaying()) {
				game.res.spinSound.getSound().stop();
			}

			// Music
			if(musicEnabled) {
				if(game.res.themesong.getSound().isPlaying()) {
					game.res.themesong.getSound().stop();
				}
			}
		}
		else {
			volumeEnabled = true;
			volumeButton.setImages(game.res.volumeOnButton.getPath(), game.res.volumeOnButton.getPath());

			if(spinning) {
				game.res.spinSound.getSound().play(1f, 1f, true);
			}

			// Music
			if(musicEnabled) {
				game.res.themesong.getSound().play(1f, 1f, true);
			}
		}
	}

	public void toggleMusic() {
		if(musicEnabled) {
			if(game.res.themesong.getSound().isPlaying()) {
				game.res.themesong.getSound().stop();
			}
			musicEnabled = false;
			musicButton.setImages(game.res.musicOffButton.getPath(), game.res.musicOffButton.getPath());
		}
		else {
			if(volumeEnabled) {
				game.res.themesong.getSound().play(1f, 1f, true);
			}
			musicEnabled = true;
			musicButton.setImages(game.res.musicOnButton.getPath(), game.res.musicOnButton.getPath());
		}
	}

	// Key Input
	public void keyPressed(KeyEvent e) {
		// Increase / Decrease Counts
		if(Config.simulationEnabled) {
			if(e.getKeyCode() == KeyEvent.VK_1)
				increaseCount(SlotMachine.LEMON);
			if(e.getKeyCode() == KeyEvent.VK_2)
				increaseCount(SlotMachine.CHERRY);
			if(e.getKeyCode() == KeyEvent.VK_3)
				increaseCount(SlotMachine.ORANGE);
			if(e.getKeyCode() == KeyEvent.VK_4)
				increaseCount(SlotMachine.PLUM);
			if(e.getKeyCode() == KeyEvent.VK_5)
				increaseCount(SlotMachine.PEACH);
			if(e.getKeyCode() == KeyEvent.VK_6)
				increaseCount(SlotMachine.MELON);
			if(e.getKeyCode() == KeyEvent.VK_7)
				increaseCount(SlotMachine.GRAPES);
			if(e.getKeyCode() == KeyEvent.VK_8)
				increaseCount(SlotMachine.SEVEN);
			if(e.getKeyCode() == KeyEvent.VK_F1)
				decreaseCount(SlotMachine.LEMON);
			if(e.getKeyCode() == KeyEvent.VK_F2)
				decreaseCount(SlotMachine.CHERRY);
			if(e.getKeyCode() == KeyEvent.VK_F3)
				decreaseCount(SlotMachine.ORANGE);
			if(e.getKeyCode() == KeyEvent.VK_F4)
				decreaseCount(SlotMachine.PLUM);
			if(e.getKeyCode() == KeyEvent.VK_F5)
				decreaseCount(SlotMachine.PEACH);
			if(e.getKeyCode() == KeyEvent.VK_F6)
				decreaseCount(SlotMachine.MELON);
			if(e.getKeyCode() == KeyEvent.VK_F7)
				decreaseCount(SlotMachine.GRAPES);
			if(e.getKeyCode() == KeyEvent.VK_F8)
				decreaseCount(SlotMachine.SEVEN);
		}

		// Display Counts
		if(e.getKeyCode() == KeyEvent.VK_D) {
			displayCounts();
		}

		// Toggle Music
		if(e.getKeyCode() == KeyEvent.VK_M) {
			toggleMusic();
		}

		// Toggle Volume
		if(e.getKeyCode() == KeyEvent.VK_V) {
			toggleVolume();
		}

		// Player Spin / Run Simulation
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			if(Config.simulationEnabled) {
				simulate();
				return;
			}

			if(!spinning) {
				// Handle AutoSpin
				if(autoSpinEnabled) {
					toggleAutoSpin();
				}

				playerSpin();
			}
			else {
				if(Config.stopSpinEnabled) {
					// Turn off AutoSpin
					if(autoSpinEnabled) {
						toggleAutoSpin();
					}

					stopSpinning();
				}
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		// Increase Decrease Bet Amount
		if(e.getKeyCode() == KeyEvent.VK_EQUALS) {
			increaseBetAmount();
		}
		if(e.getKeyCode() == KeyEvent.VK_MINUS) {
			decreaseBetAmount();
		}

		// Toggle Autospin
		if(e.getKeyCode() == KeyEvent.VK_A) {
			a_key_just_pressed = true;
		}

		// Toggle Quickspin
		if(e.getKeyCode() == KeyEvent.VK_Q) {
			q_key_just_pressed = true;
		}

		// Loading Credit
		if(!Config.simulationEnabled) {
			if(e.getKeyCode() == KeyEvent.VK_1)
				loadCredit(1);
			if(e.getKeyCode() == KeyEvent.VK_2)
				loadCredit(10);
			if(e.getKeyCode() == KeyEvent.VK_3)
				loadCredit(100);
			if(e.getKeyCode() == KeyEvent.VK_4)
				loadCredit(1000);
			if(e.getKeyCode() == KeyEvent.VK_5)
				loadCredit(10000);

			// Reset Credit
			if(e.getKeyCode() == KeyEvent.VK_0)
				resetCredit();
		}

		if(e.getKeyCode() == KeyEvent.VK_B)
			displayBalanceAndBetAmount();
	}

	// Mouse Input
	public void mousePressed(MouseEvent e) {
		if(Config.simulationEnabled)
			return;

		if(e.getButton() == MouseEvent.BUTTON1) {
			if(spinButton.containsMouse()) {
				spinButton.toggleOffset(false);

				if(!spinning) {
					// Handle AutoSpin
					if(autoSpinEnabled) {
						toggleAutoSpin();
					}

					playerSpin();
				}
				else {
					if(Config.stopSpinEnabled) {
						// Turn off AutoSpin
						if(autoSpinEnabled) {
							toggleAutoSpin();
						}

						stopSpinning();
					}
				}
			}

			if(autoSpinButton.containsMouse()) {
				if((credit - betAmount) > 0) {
					toggleAutoSpin();
				}
			}

			if(quickSpinButton.containsMouse()) {
				quickSpinButton.toggleOffset(false);
				toggleQuickSpin();
			}

			if(increaseBetButton.containsMouse()) {
				increaseBetAmount();
			}

			if(decreaseBetButton.containsMouse()) {
				decreaseBetAmount();
			}

			if(volumeButton.containsMouse()) {
				toggleVolume();
			}

			if(musicButton.containsMouse()) {
				toggleMusic();
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		if(Config.simulationEnabled)
			return;

		if(spinButton.containsMouse()) {
			spinButton.toggleOffset(false);
		}
	}

	// Controller Input
	public void checkInputState() {
		// Spin
		if(a_btn_just_pressed || space_key_just_pressed) {
			if(Config.simulationEnabled) {
				simulate();
				return;
			}

			if(!spinning) {
				// Handle AutoSpin
				if(autoSpinEnabled) {
					toggleAutoSpin();
				}

				playerSpin();
			}
			else {
				if(Config.stopSpinEnabled) {
					// Turn off AutoSpin
					if(autoSpinEnabled) {
						toggleAutoSpin();
					}

					stopSpinning();
				}
			}
		}

		// Toggle Auto Spin
		if(y_btn_just_pressed || a_key_just_pressed) {
			if((credit - betAmount) > 0) {
				toggleAutoSpin();
			}
		}

		// Toggle Quick Spin
		if(x_btn_just_pressed || q_key_just_pressed) {
			toggleQuickSpin();
		}

		// Increase Bet Amount
		if(rb_btn_just_pressed) {
			increaseBetAmount();
		}

		// Decrease Bet Amount
		if(lb_btn_just_pressed) {
			decreaseBetAmount();
		}

		// Toggle Music
		if(back_btn_just_pressed) {
			toggleMusic();
		}

		// Toggle Sound
		if(start_btn_just_pressed) {
			toggleVolume();
		}

		if(Config.continuousInput) {
			if(x_btn) {
				System.out.println("X");
			}
		}
		else {
			if(x_btn_just_pressed) {
				System.out.println("X");
			}
		}
	}

	public void controllerUpdate(ControllerState controller) {
		// Update Controller State
		this.controller = controller;

		// Start, Back
		this.start_btn = (controller.start);
		this.back_btn = (controller.back);
		this.start_btn_just_pressed = (controller.startJustPressed);
		this.back_btn_just_pressed = (controller.backJustPressed);

		// DPad
		this.dpadUp_btn = (controller.dpadUp);
		this.dpadDown_btn = (controller.dpadDown);
		this.dpadLeft_btn = (controller.dpadLeft);
		this.dpadRight_btn = (controller.dpadRight);
		this.dpadUp_btn_just_pressed = (controller.dpadUpJustPressed);
		this.dpadDown_btn_just_pressed = (controller.dpadDownJustPressed);
		this.dpadLeft_btn_just_pressed = (controller.dpadLeftJustPressed);
		this.dpadRight_btn_just_pressed = (controller.dpadRightJustPressed);

		// A, B, X, Y
		this.a_btn = (controller.a);
		this.b_btn = (controller.b);
		this.x_btn = (controller.x);
		this.y_btn = (controller.y);
		this.a_btn_just_pressed = controller.aJustPressed;
		this.b_btn_just_pressed = controller.bJustPressed;
		this.x_btn_just_pressed = controller.xJustPressed;
		this.y_btn_just_pressed = controller.yJustPressed;

		// Left Button, Left Trigger
		this.lb_btn = (controller.lb);
		this.leftTrigger_btn = (controller.leftTrigger > 0.5);
		this.lb_btn_just_pressed = (controller.lbJustPressed);

		// Right Button, Right Trigger
		this.rb_btn = (controller.rb);
		this.rightTrigger_btn = (controller.rightTrigger > 0.5);
		this.rb_btn_just_pressed = (controller.rbJustPressed);

		// Left Stick
		this.leftStick_btn = (controller.leftStickClick);
		this.leftStick_btn_just_pressed = (controller.leftStickJustClicked);
		this.leftStickY_up = (controller.leftStickY > 0.5);
		this.leftStickY_down = (controller.leftStickY < -0.5);
		this.leftStickX_left = (controller.leftStickX < -0.1);
		this.leftStickX_right = (controller.leftStickX > 0.1);

		// Right Stick
		this.rightStick_btn = (controller.rightStickClick);
		this.rightStick_btn_just_pressed = (controller.rightStickJustClicked);
		this.rightStickY_up = (controller.rightStickY > 0.5);
		this.rightStickY_down = (controller.rightStickY < -0.5);
		this.rightStickX_left = (controller.rightStickX < -0.5);
		this.rightStickX_right = (controller.rightStickX > 0.5);
	}

	public void resetJustPressedKeys() {
		a_key_just_pressed = false;
		q_key_just_pressed = false;
		shift_key_just_pressed = false;
		ctrl_key_just_pressed = false;
		alt_key_just_pressed = false;
		space_key_just_pressed = false;
		r_key_just_pressed = false;
		f1_key_just_pressed = false;
		f2_key_just_pressed = false;
		f3_key_just_pressed = false;
		f4_key_just_pressed = false;
		f5_key_just_pressed = false;
		f6_key_just_pressed = false;
		f7_key_just_pressed = false;
		f8_key_just_pressed = false;
		f9_key_just_pressed = false;
		f10_key_just_pressed = false;
		f11_key_just_pressed = false;
		f12_key_just_pressed = false;
	}
}