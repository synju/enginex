package Genalgorithm;

import enginex.EngineX;

public class Genalgorithm extends EngineX {
	protected Genalgorithm(String gameName, int w, int h) {
		super(gameName, w, h);
	}
	
	public void init() {
		stateMachine.pushState(new PlayState(this));
		run();
	}
	
	public static void main(String[] args) {
		new Genalgorithm("Genalgorithm", 800, 600).init();
	}
}
