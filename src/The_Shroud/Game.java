package The_Shroud;

import EngineX.EngineX;

import java.awt.*;

public class Game extends EngineX {
	public PlayState ps;
	public boolean renderEnabled = true;

	public static void main(String[] args) {
		new Game().init();
	}

	Game() {
		super(Config.title, Config.fullscreen, Config.sizeable, (Config.fullscreen) ? (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() : Config.width, (Config.fullscreen) ? (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight() : Config.height);
	}

	public void init() {
		ps = new PlayState(this);
		stateMachine.pushState(ps);
		stateMachine.states.get(0).init();
		run();
	}
}
