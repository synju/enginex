package monsters;

import enginex.EngineX;

public class Game extends EngineX {
	PlayState playState;

	public final int PLAY = 0;

	Game() {
		super("Monsters", 800, 600, false, false);
		window.setVisible(true);
	}

	public void init() {
		playState = new PlayState(this);
		stateMachine.pushState(playState);
		stateMachine.states.get(PLAY).init();

		run();
	}

	public static void main(String[] args) {
		new Game().init();
	}
}
