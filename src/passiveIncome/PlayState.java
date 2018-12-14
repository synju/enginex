package passiveIncome;

import enginex.State;

public class PlayState extends State {
	Game game;
	boolean initialized = false;
	
	public PlayState(Game game) {
		super(game);
		this.game = game;
	}
}
