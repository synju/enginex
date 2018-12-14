package megamoney;

import enginex.EngineX;

public class Game extends EngineX {
	PlayState ps;
	
	public static void main(String[] args) {
		new Game().init();
	}
	
	Game() {
		super("Mega Money");
	}
	
	public void init() {
		ps = new PlayState(this);
		stateMachine.pushState(ps);
		stateMachine.states.get(0).init();
		run();
	}
}
