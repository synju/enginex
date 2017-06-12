package enginex.smartrockets;

import enginex.core.EngineX;

public class SmartRockets extends EngineX {
	protected SmartRockets(String gameName, int w, int h) {
		super(gameName, w, h);
	}

	public void init() {
		stateMachine.pushState(new SmartRocketState(this));
		run();
	}
}
