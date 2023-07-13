package Archive.miniworld_top_down_potential;

import java.util.ArrayList;

import EngineX.EngineX;
import Complete.titanclones.Collidable;

public class Game extends EngineX {
	PlayState playState;
	static int customScale = 2;
	
	public ArrayList<Collidable> clist = new ArrayList<>();
	
	Game() {
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
		new Game().init();
	}
}