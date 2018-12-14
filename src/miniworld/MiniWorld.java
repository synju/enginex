package miniworld;

import java.util.ArrayList;

import enginex.EngineX;
import titanclones.Collidable;

public class MiniWorld extends EngineX {
	PlayState playState;
	static int customScale = 2;
	
	public ArrayList<Collidable> clist = new ArrayList<>();
	
	MiniWorld() {
		super("MiniWorld", (customScale*32)*7, (customScale*32)*7);
	}
	
	public void init() {
		scale = customScale;
		
		playState = new PlayState(this);
		stateMachine.pushState(playState);
		stateMachine.states.get(0).init();

		run();
	}
	
	public static void main(String[] args) {
		new MiniWorld().init();
	}
}