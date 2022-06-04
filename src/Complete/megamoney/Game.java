package Complete.megamoney;

import EngineX.EngineX;

public class Game extends EngineX {
	PlayState ps;

	Resources res		= new Resources();
	
	public static void main(String[] args) {
		new Game().init();
	}
	
	Game() {
		super("Mega Money",800,600);
	}
	
	public void init() {
		ps = new PlayState(this);
		stateMachine.pushState(ps);
		stateMachine.states.get(0).init();
		run();
	}
}
