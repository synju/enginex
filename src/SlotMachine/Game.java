package SlotMachine;

import EngineX.EngineX;

import java.awt.*;

public class Game extends EngineX {
	Resources res = new Resources();
	PlayState ps;

	int width;
	int height;

	public static void main(String[] args) {
		new Game().init();
	}

	Game() {
		super(Config.gameName, Config.fullscreen, Config.sizeable, (Config.fullscreen) ? (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth() : Config.width, (Config.fullscreen) ? (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight() : Config.height);
		this.width = super.width;
		this.height = super.height;
	}

	public void init() {
		ps = new PlayState(this);
		stateMachine.pushState(ps);
		stateMachine.states.get(0).init();
		run();
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}
}
