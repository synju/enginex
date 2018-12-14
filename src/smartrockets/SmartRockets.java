package smartrockets;

import enginex.EngineX;

public class SmartRockets extends EngineX {
	protected SmartRockets(String gameName, int w, int h) {
		super(gameName, w, h);
	}
	
	public void init() {
		stateMachine.pushState(new SmartRocketState(this));
		run();
	}
	
	public static void main(String[] args) {
		new SmartRockets("Smart Rockets", 800, 600).init();
	}
}
