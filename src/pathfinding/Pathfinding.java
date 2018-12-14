package pathfinding;

import enginex.EngineX;

public class Pathfinding extends EngineX {
	PathfinderState ps;

	protected Pathfinding(String gameName, int w, int h) {
		super(gameName, w, h);
	}

	public void init() {
		ps = new PathfinderState(this);
		stateMachine.pushState(ps);
		stateMachine.states.get(0).init();
		run();
	}
	
	public static void main(String[] args) {
		new Pathfinding("Pathfinder", 800, 600).init();
	}
}
