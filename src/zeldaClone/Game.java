package zeldaClone;

import enginex.EngineX;

public class Game extends EngineX {
	public PlayState playState;
	public MenuState menuState;

	public static final int MENU = 0;
	public static final int PLAY = 1;

	public Resources resources;

	Game() {
		super("Zelda Clone", 800, 600);
	}

	public void init() {
		resources = new Resources();

		menuState = new MenuState(this);
		playState = new PlayState(this);

		stateMachine.pushState(menuState);
		stateMachine.pushState(playState);

		stateMachine.initAll();

		window.setVisible(true);
		run();
	}

	public static void main(String args[]) {
		new Game().init();
	}
}