package moo;

import enginex.State;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class PlayState extends State {
	Game game;
	boolean initialized = false;

	Collidable cameraBox;
	Player p;
	ArrayList<Collidable> clist = new ArrayList<>();

	int cameraBoxExtraHeight = 34;
	boolean cameraCorrection = true;
	boolean sideToSideCamera = true;
	boolean upAndDownCamera = true;

	Image gridBG;
	Image gridBlock;

	int worldX = 0;
	int worldY = 0;

	Collidable startLocation;

	public PlayState(Game game) {
		super(game);
		this.game = game;
	}

	public void postInit() {
		// Check if initialized
		if(initialized)
			return;

		// Images
		gridBG = new ImageIcon(game.res.grid_bg.getPath()).getImage();
		gridBlock = new ImageIcon(game.res.grid_block.getPath()).getImage();

		// Start Location
		startLocation = new Collidable(game, 2 * 32, 11 * 32, 32, 32);

		// CameraBox
		//		cameraBox = new Collidable(game, 16 * 32, 7 * 32 - cameraBoxExtraHeight, 10 * 32, 5 * 32 + cameraBoxExtraHeight);
		//		cameraBox = new Collidable(game, 18 * 32, 7 * 32 - cameraBoxExtraHeight, 6 * 32, 5 * 32 + cameraBoxExtraHeight);
		cameraBox = new Collidable(game, 20 * 32, 9 * 32 - cameraBoxExtraHeight, 2 * 32, 4 * 32 + cameraBoxExtraHeight);

		// Player
		p = new Player(game, (int)startLocation.x, (int)startLocation.y - 32);

		// Collider List
		clist = new ArrayList<>();

		// Ceiling
		clist.add(new Collidable(game, (0 * 32), (0 * 32), 42 * 32, 32));

		// Floor
		clist.add(new Collidable(game, (0 * 32), (20 * 32), 42 * 32, 32));

		// Walls
//		clist.add(new Collidable(game, (0 * 32), (1 * 32), 1 * 32, 19 * 32));
//		clist.add(new Collidable(game, (41 * 32), (1 * 32), 1 * 32, 19 * 32));

		// Platforms
		clist.add(new Collidable(game, (2 * 32), (12 * 32), 2 * 32, 32));
		clist.add(new Collidable(game, (5 * 32), (9 * 32), 2 * 32, 32));
		clist.add(new Collidable(game, (8 * 32), (12 * 32), 2 * 32, 32));
		clist.add(new Collidable(game, (11 * 32), (9 * 32), 2 * 32, 32));
		clist.add(new Collidable(game, (14 * 32), (6 * 32), 2 * 32, 32));
		clist.add(new Collidable(game, (18 * 32), (6 * 32), 2 * 32, 32));
		clist.add(new Collidable(game, (20 * 32), (9 * 32), 2 * 32, 32));

		// Complete initialization
		initialized = true;
	}

	public void update() {
		// This is Run Only Once...
		postInit();

		// Update Player...
		p.update();

		// Camera Correction...
		cameraCorrection();
	}

	void cameraCorrection() {
		if(cameraCorrection) {
			int difference = 0;

			// Side to Side
			if(sideToSideCamera) {
				// Left Side
				if(p.x < cameraBox.x) {
					difference = ((int)cameraBox.x - (int)p.x);
					worldX += difference;
					startLocation.x += difference;
					p.x += difference;
					for(Collidable c : clist) {
						c.x += difference;
					}
				}

				// Right Side
				if((p.x + p.w) > (cameraBox.x + cameraBox.w)) {
					difference = (((int)p.x + p.w) - (int)(cameraBox.x + cameraBox.w));
					worldX -= difference;
					startLocation.x -= difference;
					p.x -= difference;
					for(Collidable c : clist) {
						c.x -= difference;
					}
				}
			}

			// Up and Down
			if(upAndDownCamera) {// Upper Side
				if(p.y < (cameraBox.y - cameraBoxExtraHeight)) {
					difference = ((int)(cameraBox.y - cameraBoxExtraHeight) - (int)p.y);
					worldY += difference;
					startLocation.y += difference;
					p.y += difference;
					for(Collidable c : clist) {
						c.y += difference;
					}
				}

				// Bottom Side
				if(p.y + p.h > (cameraBox.y + cameraBox.h)) {
					difference = (int)(p.y + p.h) - (int)(cameraBox.y + cameraBox.h);
					worldY -= difference;
					startLocation.y -= difference;
					p.y -= difference;
					for(Collidable c : clist) {
						c.y -= difference;
					}
				}
			}
		}
	}

	public void render(Graphics2D g) {
		// Grid BG
		g.drawImage(gridBG, worldX, worldY, null);
		g.drawImage(gridBG, worldX-game.gameWidth, worldY, null);

		// Collision Objects
		for(Collidable c : clist) {
			c.render(g);
		}

		// Start Location
		g.setColor(Color.GREEN);
		g.fillRect((int)startLocation.x, (int)startLocation.y, (int)startLocation.w, (int)startLocation.h);

		// Camera Correction
		g.setColor(Color.GRAY);
		g.drawRect((int)cameraBox.x, (int)cameraBox.y - cameraBoxExtraHeight, (int)cameraBox.w, (int)cameraBox.h + cameraBoxExtraHeight);

		// Player
		p.render(g);
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			game.exit();

		p.keyPressed(e);
	}

	public void keyReleased(KeyEvent e) {
		p.keyReleased(e);
	}

	public void mousePressed(MouseEvent e) {
		p.mousePressed(e);
	}

	public void mouseReleased(MouseEvent e) {
		p.mouseReleased(e);
	}
}
