package menuexp;

import enginex.EngineX;

public class MenuExp extends EngineX {
	MenuState menuState;

	MenuExp(String gameName) {
		super(gameName,800,600);
	}

	public void init() {
		menuState = new MenuState(this);
		stateMachine.pushState(menuState);
		stateMachine.getCurrentState().init();
		run();
	}
	
	public static void main(String[] args) {
		new MenuExp("Menu Experiment").init();
	}
}
