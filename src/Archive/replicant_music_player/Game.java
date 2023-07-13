package Archive.replicant_music_player;

import EngineX.EngineX;

public class Game extends EngineX {
	MenuState menuState;
	public static final int MENU = 0;

	Game() {
		super("Replicants");
	}

	public void init() {
		menuState = new MenuState(this);
		stateMachine.pushState(menuState);
		stateMachine.states.get(MENU).init();

		window.setVisible(true);

		run();
	}

	public static void main(String[] args) {
		new Game().init();
	}
}
