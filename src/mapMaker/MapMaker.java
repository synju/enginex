package mapMaker;

import enginex.EngineX;

public class MapMaker extends EngineX {
	PlayState					playState;

	public final int	PLAY	= 0;

	MapMaker() {
		// super("MapMaker", 1600, 900);
		// super("MapMaker", 1366, 768);
		super("MapMaker", 1024, 576, true);
		// super("MapMaker");
		// setSize(1024,576,1,true);
		// window.setLocation(2150, 250);
	}

	public void init() {
		playState = new PlayState(this);
		stateMachine.pushState(playState);
		stateMachine.states.get(PLAY).init();

		run();
	}

	public static void main(String[] args) {
		new MapMaker().init();
	}
}
