package multistate;

import enginex.EngineX;

public class MultiState extends EngineX {
	MSMenuState	menuState;
	MSGameState	gameState;

	public static final int	MENU	= 0;
	public static final int	GAME	= 1;

	MultiState(String gameName) {
		super(gameName);
	}

	public void init() {
		menuState = new MSMenuState(this);
		gameState = new MSGameState(this);

		stateMachine.pushState(menuState);
		stateMachine.pushState(gameState);

		stateMachine.states.get(MENU).init();
		stateMachine.states.get(GAME).init();

		run();
	}
	
	public static void main(String[] args) {
		new MultiState("MultiState").init();
	}
}
