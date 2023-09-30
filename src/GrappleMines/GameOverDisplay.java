package GrappleMines;

import EngineX.Button;

import java.awt.*;

public class GameOverDisplay {
	Game game;
	boolean visible = false;
	boolean initialized = false;

	Button restartButton;
	Button exitButton;

	GameOverDisplay(Game game) {
		this.game = game;
	}

	void postInit() {
		if(initialized) return;

		restartButton = new Button(game, "Restart", 40,170,180,40);
		exitButton = new Button(game, "Exit", 40,220,180,40);

		initialized = true;
	}

	void update() {
		postInit();
	}

	void render(Graphics2D g) {
		if(visible) {
			restartButton.render(g);
			exitButton.render(g);
		}
	}
}
