package bubable;

import enginex.EngineX;

public class Game extends EngineX {
	public static final int PLAY = 0;

	PlayState playState;

	Game() {
		super("Bubable", 1360, 768, false, false);
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
