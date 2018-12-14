package spaceshooter;

import enginex.EngineX;

public class Spaceshooter extends EngineX {
	MenuState					menuState;
	PlayState					playState;
	PauseState				pauseState;
	
	public final int	MENU	= 0;
	public final int	PAUSE	= 1;
	public final int	PLAY	= 2;
	
	public Resources	res		= new Resources();
	
	Spaceshooter() {
		super("Spaceshooter", 800, 600);
	}
	
	public void init() {
		menuState = new MenuState(this);
		playState = new PlayState(this);
		pauseState = new PauseState(this);
		
		stateMachine.pushState(menuState);
		stateMachine.pushState(pauseState);
		stateMachine.pushState(playState);
		
		stateMachine.states.get(MENU).init();
		
		run();
	}
	
	public static void main(String[] args) {
		new Spaceshooter().init();
	}
}
