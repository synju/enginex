package enginex.zombieapocalypse;

import enginex.core.EngineX;

public class ZombieApocalypse extends EngineX {
	PlayState playState = new PlayState(this);

	ZombieApocalypse(String gameName) {
		super(gameName);
	}

	public void init() {
		stateMachine.pushState(playState);
		((PlayState)stateMachine.getCurrentState()).init();

		playState = new PlayState(this);
		stateMachine.pushState(playState);
		stateMachine.getCurrentState().init();
		run();
	}
}
