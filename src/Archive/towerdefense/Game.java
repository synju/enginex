package Archive.towerdefense;

import java.util.ArrayList;

import EngineX.EngineX;

public class Game extends EngineX {
	PlayState					playState;
	public final int PLAY = 0;
	static ArrayList<String> config = new ArrayList<>();

	public static void main(String[] args) {
		config.add("maximized:true");
		config.add("sizable:false");
		new Game().init();
	}

	Game() {
		super("Tower Defense", 1136, 640);
	}

	public void init() {
		playState = new PlayState(this);
		stateMachine.pushState(playState);
		stateMachine.states.get(PLAY).init();
		run();
	}
}
