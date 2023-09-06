package Tamagotchi;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Tamagotchi {
	// Core Variables
	Game    game;
	boolean initialized = false;

	// Time
	long lastUpdateTime;
	long currentTimeMillis;

	// Tamagotchi Properties
	int health       = 100;
	int hunger       = 25;
	int happiness    = 25;
	int credits      = 0;
	int originalType = Creatures.BULBASAUR;
	int type         = originalType;
	int age          = 0; // days

	// Images etc..
	Image creatureImage;

	public Tamagotchi(Game game) {
		this.game = game;
	}

	public void init() {
		if(initialized)
			return;

		// Initialize things...
		currentTimeMillis = System.currentTimeMillis();
		lastUpdateTime = currentTimeMillis;

		// Set initial creature image...
		setCreatureImage();

		initialized = true;
	}

	public void setCreatureImage() {
		if(type == Creatures.BULBASAUR) creatureImage = game.res.bulbasaur.getImage();
		if(type == Creatures.IVYSAUR) creatureImage = game.res.ivysaur.getImage();
		if(type == Creatures.VENUSAUR) creatureImage = game.res.venusaur.getImage();
	}

	public void update() {
		init();
		currentTimeMillis = System.currentTimeMillis();
		if(currentTimeMillis > (lastUpdateTime + 1000)) {
			lastUpdateTime = currentTimeMillis;

			// Do Work

		}
	}

	public void render(Graphics2D g) {
		g.drawImage(creatureImage, game.getWidth() / 2 - creatureImage.getWidth(null) / 2, game.getHeight() / 2 - creatureImage.getHeight(null) / 2, null);
	}

	public void keyReleased(KeyEvent e) {
		// Machine keyReleased...
	}
}
