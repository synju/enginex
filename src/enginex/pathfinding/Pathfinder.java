package enginex.pathfinding;

import enginex.core.EngineX;

public class Pathfinder extends EngineX {
	PathfinderState ps;

	protected Pathfinder(String gameName, int w, int h) {
		super(gameName, w, h);
	}

	public void init() {
		ps = new PathfinderState(this);
		stateMachine.pushState(ps);
		stateMachine.states.get(0).init();
		run();
	}

}
