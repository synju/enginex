package Complete.bubable;

import EngineX.EngineX;

public class Game extends EngineX {
	public static final int PLAY = 0;

	PlayState playState;

	Game() {
		super("Infinity Dungeon", 640, 480, false, false);
		window.setVisible(true);
	}

	public void init() {
		this.scale = 6;
		playState = new PlayState(this);
		stateMachine.pushState(playState);
		stateMachine.states.get(PLAY).init();

		run();
	}

	public static void main(String[] args) {
		new Game().init();
	}
}
