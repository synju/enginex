package enginex.replicant;

import enginex.core.EngineX;

public class Replicants extends EngineX {
	RMenuState menuState;
	RGameState gameState;
	
	public static final int MENU = 0;
	public static final int GAME = 1;
	
	Replicants() {
		super("Replicants");
	}
	
	public void init() {
		menuState = new RMenuState(this);
		gameState = new RGameState(this);
		
		stateMachine.pushState(menuState);
		stateMachine.pushState(gameState);
		
		stateMachine.states.get(MENU).init();
		stateMachine.states.get(GAME).init();
		
		run();
	}
}
