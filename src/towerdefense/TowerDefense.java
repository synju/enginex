package towerdefense;

import java.util.ArrayList;

import enginex.EngineX;

public class TowerDefense extends EngineX {
	PlayState					playState;
	public final int	PLAY	= 0;
	static ArrayList<String> config = new ArrayList<>();

	public static void main(String[] args) {
		config.add("maximized:true");
		config.add("sizable:false");
		new TowerDefense().init();
	}

	TowerDefense() {
		super("Tower Defense", 1136, 640);
	}

	public void init() {
		playState = new PlayState(this);
		stateMachine.pushState(playState);
		stateMachine.states.get(PLAY).init();
		run();
	}
}
