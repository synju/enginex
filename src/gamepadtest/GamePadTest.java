package gamepadtest;

import enginex.EngineX;

public class GamePadTest extends EngineX {
	PlayState playState;
	PlayStateTest playStateTest;

	public final int PLAY = 0;

	GamePadTest() {
		super("Gamepad Test", 288 * 2, 288 * 2);
	}

	public void init() {
		// Set Scale
		scale = 2;

		playState = new PlayState(this);
		playStateTest = new PlayStateTest(this);

//		stateMachine.pushState(playState);
		stateMachine.pushState(playStateTest);

		stateMachine.states.get(PLAY).init();

		run();
	}

	public static void main(String[] args) {
		new GamePadTest().init();
	}
}
