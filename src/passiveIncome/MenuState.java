package passiveIncome;

import enginex.State;

public class MenuState extends State {
	Game game;
	boolean initialized = false;
	
	public MenuState(Game game) {
		super(game);
		this.game = game;
	}
}
