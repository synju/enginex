package spinner;

import enginex.EngineX;

public class Game extends EngineX {
	PlayState playState;
	
	public static void main(String[] args) {
		new Game().init();
	}
	
	Game() {
		super("Platformer", 800, 600);
	}
	
	public void init() {
		playState = new PlayState(this);
		
		stateMachine.pushState(playState);
		stateMachine.states.get(0).init();
		run();
	}
}
