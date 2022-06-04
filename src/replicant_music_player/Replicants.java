package replicant_music_player;

import enginex.EngineX;

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
		
		window.setVisible(true);
		
		run();
	}
	
	public static void main(String[] args) {
		new Replicants().init();
	}
}
