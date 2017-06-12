package enginex.spriteAnimation;

import enginex.core.EngineX;

public class AnimationExperiment extends EngineX {
	GameState gs;
	
	protected AnimationExperiment(String gameName, int w, int h) {
		super(gameName, w, h);
	}
	
	public void init() {
		gs = new GameState(this);
		stateMachine.pushState(gs);
		stateMachine.states.get(0).init();
		run();
	}
}
