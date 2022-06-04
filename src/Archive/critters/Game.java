package Archive.critters;

import EngineX.EngineX;

public class Game extends EngineX {
	MenuState menuState;
	PlayState playState;
	
	public final int	MENU	= 0;
	public final int	PLAY	= 1;
	
	Game() {
		super("Critters",800,600);
		window.setVisible(true);
	}
	
	public void init() {
		// MenuState
		menuState = new MenuState(this);
		stateMachine.pushState(menuState);
		stateMachine.getState(MENU).init();
		
		// PlayState
		playState = new PlayState(this);
		stateMachine.pushState(playState);
		stateMachine.getState(PLAY).init();

		// tempState..
		stateMachine.setState(PLAY);

		run();
	}
	
	public static void main(String[] args) {
		new Game().init();
	}
}
