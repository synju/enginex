package spriteAnimation;

import enginex.EngineX;

public class SpriteAnimation extends EngineX {
	GameState gs;
	
	protected SpriteAnimation(String gameName, int w, int h) {
		super(gameName, w, h);
	}
	
	public void init() {
		gs = new GameState(this);
		stateMachine.pushState(gs);
		stateMachine.states.get(0).init();
		run();
	}
	
	public static void main(String[] args) {
		new SpriteAnimation("Animation Experiment", 800, 600).init();
	}
}
