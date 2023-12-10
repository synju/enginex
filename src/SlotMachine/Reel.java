package SlotMachine;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Reel {
	Game        game;
	ReelManager reelManager;
	int         x;
	int         y;
	boolean     spinning          = false;
	boolean     blurred           = false;
	int spinSpeed     = 40;
	int spinCountdown = 0;

	int spinningAy;
	int spinningBy;

	ArrayList<Symbol> symbolsSpinningA = new ArrayList<>();
	ArrayList<Symbol> symbolsSpinningB = new ArrayList<>();
	ArrayList<Symbol> symbolsStatic    = new ArrayList<>();

	Reel(Game game, ReelManager reelManager, int x, int y) {
		this.game = game;
		this.reelManager = reelManager;
		this.x = x;
		this.y = y;

		spinningAy = y;
		spinningBy = y - (Symbol.HEIGHT * 4);

		Random rand = new Random();

		symbolsStatic.add(new Symbol(game, this, rand.nextInt(8)));
		symbolsStatic.add(new Symbol(game, this, rand.nextInt(8)));
		symbolsStatic.add(new Symbol(game, this, rand.nextInt(8)));
		symbolsStatic.add(new Symbol(game, this, rand.nextInt(8)));

		symbolsSpinningA.add(new Symbol(game, this, rand.nextInt(8)));
		symbolsSpinningA.add(new Symbol(game, this, rand.nextInt(8)));
		symbolsSpinningA.add(new Symbol(game, this, rand.nextInt(8)));
		symbolsSpinningA.add(new Symbol(game, this, rand.nextInt(8)));

		symbolsSpinningB.add(new Symbol(game, this, rand.nextInt(8)));
		symbolsSpinningB.add(new Symbol(game, this, rand.nextInt(8)));
		symbolsSpinningB.add(new Symbol(game, this, rand.nextInt(8)));
		symbolsSpinningB.add(new Symbol(game, this, rand.nextInt(8)));
	}

	public void update() {
		if(spinCountdown > 0) {
			spinCountdown--;
		}
		else  {
			spinCountdown = 0;
			spinning = false;
		}

		//if(reelManager.slotMachine.spinning) {
		if(spinning) {
			// Enable Blurring
			blurred = true;

			// Spinning Ay
			if(spinningAy < (reelManager.y + (Symbol.HEIGHT * 4))) {
				spinningAy += spinSpeed;
			}
			else {
				Random rand = new Random();
				for(int i = 0; i < symbolsSpinningA.size(); i++) {
					int randomIndex = rand.nextInt(8);
					symbolsSpinningA.get(i).setType(randomIndex);
				}
				spinningAy = reelManager.y - (Symbol.HEIGHT * 4);
			}

			// Spinning By
			if(spinningBy == (reelManager.y + (Symbol.HEIGHT * 4))) {
				Random rand = new Random();
				for(int i = 0; i < symbolsSpinningB.size(); i++) {
					int randomIndex = rand.nextInt(8);
					symbolsSpinningB.get(i).setType(randomIndex);
				}
				spinningBy = reelManager.y - (Symbol.HEIGHT * 4);
			}
			else {
				spinningBy += spinSpeed;
			}
		}
		else {
			// Disable Blurring
			blurred = false;
		}
	}

	public void render(Graphics2D g) {
		//if(reelManager.slotMachine.spinning) {
		if(spinning) {
			for(int i = 0; i < symbolsSpinningA.size(); i++) {
				Symbol s = symbolsSpinningA.get(i);
				s.render(g, x, spinningAy + (i * Symbol.HEIGHT));
			}

			for(int i = 0; i < symbolsSpinningB.size(); i++) {
				Symbol s = symbolsSpinningB.get(i);
				s.render(g, x, spinningBy + (i * Symbol.HEIGHT));
			}
		}
		else {
			for(int i = 0; i < symbolsStatic.size(); i++) {
				Symbol s = symbolsStatic.get(i);
				s.render(g, x, y + (i * Symbol.HEIGHT));
			}
		}
	}
}
