package passiveIncome;

import enginex.EngineX;

public class Game extends EngineX {
	MenuState menuState;
	PlayState playState;
	
	public static final int MENU = 0;
	public static final int PLAY = 1;
	
	public Resources res = new Resources();
	
	Game() {
		super("Passive Income", 800, 600);
	}
	
	public static void main(String[] args) {
		new Game().init();
	}
	
	public void init() {
		menuState = new MenuState(this);
		playState = new PlayState(this);
		
		stateMachine.pushState(menuState);
		stateMachine.pushState(playState);
		
		stateMachine.states.get(MENU).init();
		
		run();
	}
}
