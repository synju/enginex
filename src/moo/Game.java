package moo;

import enginex.EngineX;

public class Game extends EngineX {
	PlayState ps;
	Resources res = new Resources();

	int gameWidth;
	int gameHeight;

	public static void main(String[] args) {
		new Game().init();
	}

	Game() {
		// Game Window.... Decided to make it 42*32px Width by 21*32px Height
		super("Moo", 42*32, 42*32);
		gameWidth = 42*32;
		gameHeight = 21*32;
	}

	public void init() {
		ps = new PlayState(this);
		stateMachine.pushState(ps);
		stateMachine.states.get(0).init();
		run();
	}
}
