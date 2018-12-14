package flowershop;

import enginex.EngineX;

public class Game extends EngineX {
	// Window Positions
	public static final int	LEFT	= 2250;
	public static final int	RIGHT	= 2250;

	MenuState	menuState;
	PlayState	playState;

	public final int	MENU	= 0;
	public final int	PLAY	= 1;

	public ProfileManager profileManager;

	Game() {
		super("Flower Shop", 800, 600, false, false);
		window.setVisible(true);
		//		window.setLocation(RIGHT, 200);
	}

	public void init() {
		// Menu State
		menuState = new MenuState(this);
		stateMachine.pushState(menuState);
		stateMachine.states.get(MENU).init();

		// Play State
		playState = new PlayState(this);
		stateMachine.pushState(playState);
		stateMachine.states.get(PLAY).init();

		run();
	}

	public static void main(String[] args) {
		new Game().init();
	}
}
