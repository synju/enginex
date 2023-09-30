package GrappleMines;

import EngineX.Button;

import java.awt.*;

public class MenuDisplay {
	Game game;
	boolean visible = true;
	boolean initialized = false;

	Button newGameButton;
	Button exitButton;

	MenuDisplay(Game game) {
		this.game = game;
	}

	void postInit() {
		if(initialized) return;

		newGameButton = new Button(game, "New Game", 40,170,180,40);
		exitButton = new Button(game, "Exit", 40,220,180,40);

		initialized = true;
	}

	void update() {
		postInit();
	}

	void render(Graphics2D g) {
		if(visible) {
			newGameButton.render(g);
			exitButton.render(g);
		}
	}
}
