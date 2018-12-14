package zombieapocalypse;

import enginex.EngineX;

public class ZombieApocalypse extends EngineX {
	PlayState playState = new PlayState(this);

	ZombieApocalypse(String gameName) {
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
		new ZombieApocalypse("Zombie Apocalypse").init();
	}
}
