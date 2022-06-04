package Project_Platformer;

import com.studiohartman.jamepad.ControllerManager;
import com.studiohartman.jamepad.ControllerState;
import EngineX.State;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class PlayState extends State {
	Game game;
	boolean initialized = false;

	int worldX = 0;
	int worldY = 0;

	Player p;
	ArrayList<Collidable> clist = new ArrayList<>();

	GraphicObject grid_bg;

	EmptyObject startLocation;

	EmptyObject cameraBox;
	int cameraBoxWidth = 3 * 64;
	int cameraBoxHeight = 42 * 64;
	int cameraBoxExtraHeight = 28;
	boolean cameraCorrection = true;
	boolean sideToSideCamera = true;
	boolean upAndDownCamera = true;
	boolean renderCameraBox = true;

	ControllerManager controllerManager = new ControllerManager();

	public PlayState(Game game) {
		super(game);
		this.game = game;

		// Controller Manager
		if(Config.controllerEnabled)
			controllerManager.initSDLGamepad();
	}

	public void postInit() {
		// Check if initialized
		if(initialized)
			return;

		// Graphic Objects
		grid_bg = new GraphicObject(game, game.res.grid_bg.getPath(), worldX + 0, worldY + 0);

		// Start Location
		startLocation = new EmptyObject(game, worldX + (2 * 32), worldY + (9 * 64), 64, 64);

		// CameraBox
		cameraBox = new EmptyObject(game, (worldX + ((game.getWidth() / 2) - (cameraBoxWidth / 2))), (worldY + (((game.getHeight() / 2) - (cameraBoxHeight - 32)) - cameraBoxExtraHeight)), (cameraBoxWidth), cameraBoxHeight + cameraBoxExtraHeight);

		// Collider List
		generateColliders();

		// Player
		p = new Player(game, startLocation.x, ((startLocation.y) + 10));

		// Complete initialization
		initialized = true;
	}

	void generateColliders() {
		// Ceiling
//		clist.add(new Collidable(game, (0 * 32), (0 * 32), 42 * 32, 32));

		// Floor
		clist.add(new Collidable(game, (0 * 64), (10 * 64), 21 * 64, 64));

		// Platforms
		clist.add(new Collidable(game, (4 * 64), (9 * 64), 64, 64));
		clist.add(new Collidable(game, (4 * 64), (8 * 64), 64, 64));
	}

	public void update() {
		// This is Run Only Once...
		postInit();

		// Get Controllers Info
		if(Config.controllerEnabled)
			pollControllers();

		// Update Player...
		p.update();

		// Camera Correction...
		cameraCorrection();

		// Collider Updates
		for(Collidable c:clist) c.update();

		// Graphic Objects
		grid_bg.update();

		// Start Location
		startLocation.update();
	}

	void cameraCorrection() {
		if(cameraCorrection) {
			int difference;

			// Side to Side
			if(sideToSideCamera) {
				// Left Side
				if(p.x < cameraBox.x) {
					difference = ((int)cameraBox.x - (int)p.x);
					worldX += difference;
					p.x += difference;
				}

				// Right Side
				if((p.x + p.w) > (cameraBox.x + cameraBox.w)) {
					difference = (((int)p.x + p.w) - (int)(cameraBox.x + cameraBox.w));
					worldX -= difference;
					p.x -= difference;
				}
			}

			// Up and Down
			if(upAndDownCamera) {// Upper Side
				if(p.y < (cameraBox.y - cameraBoxExtraHeight)) {
					difference = ((int)(cameraBox.y - cameraBoxExtraHeight) - (int)p.y);
					worldY += difference;
					p.y += difference;
				}

				// Bottom Side
				if(p.y + p.h > (cameraBox.y + cameraBox.h)) {
					difference = (int)(p.y + p.h) - (int)(cameraBox.y + cameraBox.h);
					worldY -= difference;
					p.y -= difference;
				}
			}
		}
	}

	public void render(Graphics2D g) {
		// Graphic Objects
		grid_bg.render(g);

		// Collision Objects
		for(Collidable c : clist) {
			c.render(g);
		}

		// Start Location
		g.setColor(Color.GREEN);
		g.fillRect(startLocation.x, startLocation.y, startLocation.w, startLocation.h);

		// Camera Correction
		if(renderCameraBox) {
			g.setColor(Color.GRAY);
			g.drawRect(cameraBox.x, cameraBox.y - cameraBoxExtraHeight, cameraBox.getWidth(), cameraBox.getHeight() + cameraBoxExtraHeight);
		}

		// Player
		p.render(g);
	}

	public void pollControllers() {
		int numControllers = controllerManager.getNumControllers();

		for(int i = 0; i < numControllers; i++) {
			ControllerState controller = controllerManager.getState(i);
			if(controller.isConnected && i == 0) {
				p.controllerUpdate(controller);
			}
		}
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
