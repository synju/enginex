package Archive.spriteAnimation;

import EngineX.EngineX;

public class Game extends EngineX {
	GameState gs;
	
	protected Game(String gameName, int w, int h) {
		super(gameName, w, h);
	}
	
	public void init() {
		gs = new GameState(this);
		stateMachine.pushState(gs);
		stateMachine.states.get(0).init();
		run();
	}
	
	public static void main(String[] args) {
		new Game("Animation Experiment", 800, 600).init();
	}
}
