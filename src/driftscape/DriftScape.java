package driftscape;

import enginex.EngineX;

public class DriftScape extends EngineX {
	PlayState gs;

	protected DriftScape(String gameName, int w, int h, boolean sizeable, boolean autoAdjust) {
		super(gameName, w, h, sizeable, true);
		window.setVisible(true);
	}

	public void init() {
		gs = new PlayState(this);
		stateMachine.pushState(gs);
		stateMachine.states.get(0).init();
		run();
	}
	
	public static void main(String[] args) {
		new DriftScape("DriftScape", 640, 480, true, true).init();
	}
}
