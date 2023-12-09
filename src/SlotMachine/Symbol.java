package SlotMachine;

import javax.swing.*;
import java.awt.*;

public class Symbol {
	Game game;
	Reel reel;
	int  type;
	public static final int WIDTH  = 140;
	public static final int HEIGHT = 95;
	Image image;
	Image image_blurred;

	Symbol(Game game, Reel reel, int type) {
		this.game = game;
		this.reel = reel;
		setType(type);
	}

	public void update() {}

	public void render(Graphics2D g, int x, int y) {
		if(!reel.blurred) {
			g.drawImage(this.image, x, y, null);
		}
		else {
			g.drawImage(this.image_blurred, x, y, null);
		}
	}

	// Utility Functions
	public void setType(int type) {
		this.type = type;
		switch(type) {
			case SlotMachine.LEMON:
				image = game.res.lemon_img.getImage();
				image_blurred = game.res.lemon_img_blurred.getImage();
				break;

			case SlotMachine.CHERRY:
				image = game.res.cherry_img.getImage();
				image_blurred = game.res.cherry_img_blurred.getImage();
				break;

			case SlotMachine.ORANGE:
				image = game.res.orange_img.getImage();
				image_blurred = game.res.orange_img_blurred.getImage();
				break;

			case SlotMachine.PLUM:
				image = game.res.plum_img.getImage();
				image_blurred = game.res.plum_img_blurred.getImage();
				break;

			case SlotMachine.PEACH:
				image = game.res.peach_img.getImage();
				image_blurred = game.res.peach_img_blurred.getImage();
				break;

			case SlotMachine.MELON:
				image = game.res.melon_img.getImage();
				image_blurred = game.res.melon_img_blurred.getImage();
				break;

			case SlotMachine.GRAPES:
				image = game.res.grapes_img.getImage();
				image_blurred = game.res.grapes_img_blurred.getImage();
				break;

			case SlotMachine.SEVEN:
				image = game.res.seven_img.getImage();
				image_blurred = game.res.seven_img_blurred.getImage();
				break;

			default:
				break;
		}
	}
}
