package Archive.smartrockets;

import EngineX.EngineX;

public class Game extends EngineX {
	protected Game(String gameName, int w, int h) {
		super(gameName, w, h);
	}
	
	public void init() {
		stateMachine.pushState(new SmartRocketState(this));
		run();
	}
	
	public static void main(String[] args) {
		new Game("Smart Rockets", 800, 600).init();
	}
}
