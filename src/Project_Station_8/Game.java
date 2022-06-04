package Project_Station_8;

import enginex.EngineX;

public class Game extends EngineX {
	public static final int PLAY = 0;
	public int dim = 32;

	PlayState playState;

	Game() {
		super("Project_Station_8", 480, 288, false, false);
		window.setVisible(true);
	}

	public void init() {
		this.scale = 1;
		playState = new PlayState(this);
		stateMachine.pushState(playState);
		stateMachine.states.get(PLAY).init();

		run();
	}

	public static void main(String[] args) {
		new Game().init();
	}
}
