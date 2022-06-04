package zeldaClone;

import enginex.EngineX;

public class Game extends EngineX {
	public MenuState menuState;
	public PlayState playState;
	public TestState testState;

	public static final int MENU = 0;
	public static final int PLAY = 1;
	public static final int TEST = 2;

	public Resources resources;

	Game() {
		super("Zelda Clone", 800, 600);
	}

	public void init() {
		resources = new Resources();

		menuState = new MenuState(this);
		playState = new PlayState(this);
		testState = new TestState(this);

		stateMachine.pushState(menuState);
		stateMachine.pushState(playState);
		stateMachine.pushState(testState);

		stateMachine.initAll();

		stateMachine.setState(PLAY);

		window.setVisible(true);
		run();
	}

	public static void main(String args[]) {
		new Game().init();
	}
}