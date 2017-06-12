package enginex.driftscape;

import enginex.core.EngineX;

public class DriftScape extends EngineX {
	PlayState gs;

	protected DriftScape(String gameName, int w, int h, boolean sizeable, boolean autoAdjust) {
		super(gameName, w, h, sizeable, true);
	}

	public void init() {
		gs = new PlayState(this);
		stateMachine.pushState(gs);
		stateMachine.states.get(0).init();
		run();
	}
}
