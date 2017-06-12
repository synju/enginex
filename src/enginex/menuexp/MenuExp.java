package enginex.menuexp;

import enginex.core.EngineX;

public class MenuExp extends EngineX {
	MenuState menuState;

	MenuExp(String gameName) {
		super(gameName);
	}

	public void init() {
		menuState = new MenuState(this);
		stateMachine.pushState(menuState);
		stateMachine.getCurrentState().init();
		run();
	}
}
