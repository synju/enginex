package moo;

import enginex.EngineX;

public class Game extends EngineX {
	PlayState ps;

	//	Resources res		= new Resources();

	public static void main(String[] args) {
		new Game().init();
	}

	Game() {
		// Game Window.... Decided to make it 25*32px Width by 17*32px Height
		super("Moo", 25*32, 17*32);
	}

	public void init() {
		ps = new PlayState(this);
		stateMachine.pushState(ps);
		stateMachine.states.get(0).init();
		run();
	}
}
