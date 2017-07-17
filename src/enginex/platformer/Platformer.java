package enginex.platformer;

import enginex.core.EngineX;

public class Platformer extends EngineX {
	PlayState playState;
	
	Platformer() {
		super("Platformer", 10 * 50, 8 * 50);
	}
	
	public void init() {
		playState = new PlayState(this);
		
		stateMachine.pushState(playState);
		
		stateMachine.states.get(0).init();
		
		run();
	}
}
