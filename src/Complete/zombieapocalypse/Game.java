package Complete.zombieapocalypse;

import EngineX.EngineX;

public class Game extends EngineX {
	PlayState playState = new PlayState(this);
	public boolean controllerEnabled = Config.controllerEnabled;

	Game(String gameName) {
		super(gameName);
		window.setVisible(true);
	}

	public void init() {
		stateMachine.pushState(playState);
		((PlayState)stateMachine.getCurrentState()).init();

		playState = new PlayState(this);
		stateMachine.pushState(playState);
		stateMachine.getCurrentState().init();
		run();
	}

	public static void main(String[] args) {
		new Game("Zombie Apocalypse").init();
	}
}
