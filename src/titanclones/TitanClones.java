package titanclones;

import enginex.EngineX;

public class TitanClones extends EngineX {
	PlayState	playState;
	MenuState	menuState;

	public final int		MENU	= 0;
	public final int		PLAY	= 1;

	TitanClones() {
		super("Titan Clones", 288 * 2, 288 * 2);
	}

	public void init() {
		scale = 2;
		menuState = new MenuState(this);
		playState = new PlayState(this);

		stateMachine.pushState(menuState);
		stateMachine.pushState(playState);

		stateMachine.states.get(MENU).init();
		stateMachine.states.get(PLAY).init();

		run();
	}

	public static void main(String[] args) {
		new TitanClones().init();
	}
}
