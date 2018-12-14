package platformer;

import enginex.EngineX;

public class Platformer extends EngineX {
	PlayState playState;
	
	Platformer() {
		super("Platformer",10*50,8*50);
		positionWindow(200,100);
	}
	
	public void init() {
		playState = new PlayState(this);
		
		stateMachine.pushState(playState);
		
		stateMachine.states.get(0).init();
		
		window.setVisible(true);
		run();
	}
	
	public static void main(String[] args) {
		new Platformer().init();
	}
}
