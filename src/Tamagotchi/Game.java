package Tamagotchi;

import EngineX.EngineX;

public class Game extends EngineX {
	Resources res = new Resources();

	PlayState ps;

	int width;
	int height;

	Tamagotchi tamagotchi;

	public static void main(String[] args) {
		new Game().init();
	}

	Game() {
		super("Tamagotchi", Config.fullscreen, Config.sizeable, Config.width, Config.height);
		this.width = super.width;
		this.height = super.height;
	}

	public void init() {
		tamagotchi = new Tamagotchi(this);

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
