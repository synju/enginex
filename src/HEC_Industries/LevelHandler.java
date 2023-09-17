package HEC_Industries;

import HEC_Industries.structures.Structure;
import com.studiohartman.jamepad.ControllerState;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class LevelHandler {
	Game game;
	public int worldX;
	public int worldY;
	EmptyObject   startLocation;
	GraphicObject grid_bg;
	GraphicObject focus_bg;
	public Player player;
	boolean              renderStartLocation = false;
	long                 game_seed           = 0;
	ArrayList<Chunk>     levelChunks         = new ArrayList<>();
	ArrayList<Structure> levelStructures     = new ArrayList<>();

	Camera camera;

	boolean initialized = false;

	LevelHandler(Game game) {
		this.game = game;
	}

	void postInit() {
		if(initialized)
			return;

		// World Coords
		worldX = 0;
		worldY = 0;

		// Graphic Objects
		grid_bg = new GraphicObject(game, game.res.grid_bg.getPath(), worldX, worldY);

		// Generate Level
		levelChunks.add(new Chunk(game, 0, 0, true));

		// Player
		player = new Player(game, startLocation.x, startLocation.y);

		// Focus BG
		focus_bg = new GraphicObject(game, game.res.focus_bg.getPath(), (int)player.x + player.w / 2 - game.width, (int)player.y + player.h / 2 - game.width);

		// CameraBox
		camera = new Camera(game, (worldX + ((game.getWidth() / 2) - (Camera.cameraBoxWidth / 2))), (worldY + (((game.getHeight() / 2) - (Camera.cameraBoxHeight - 32)) - Camera.cameraBoxExtraHeight)), (Camera.cameraBoxWidth), Camera.cameraBoxHeight + Camera.cameraBoxExtraHeight);

		initialized = true;
	}

	public void update() {
		postInit();

		// Graphic Objects
		grid_bg.update();

		// Focus Object
		focus_bg.update();

		// Update Player...
		player.update();

		// Focus on player...
		focus_bg.x = (int)player.x + player.w / 2 - game.width;
		focus_bg.y = (int)player.y + player.h / 2 - game.width;

		// Camera Correction...
		camera.cameraCorrection();

		// Update Chunks
		for(Chunk chunk : levelChunks) {
			chunk.update();
		}

		// Update Structures
		for(Structure structure:levelStructures) {
			structure.update();
		}

		startLocation.update();
	}

	public void render(Graphics2D g) {
		// Graphic Objects
		grid_bg.render(g);

		// Blocks
		for(Chunk chunk : levelChunks) {
			for(Block block : chunk.bList) {
				if((block.x >= -game.width && block.x <= game.width) && (block.y >= -game.height && block.y <= game.height)) {
					block.render(g);
				}
			}
		}

		// Structures
		for(Structure structure:levelStructures) {
			structure.render(g);
		}

		// Start Location
		if(renderStartLocation) {
			g.setColor(Color.BLUE);
			g.fillRect(startLocation.x, startLocation.y, startLocation.w, startLocation.h);
		}

		// Camera Correction
		if(camera.renderCameraBox) {
			g.setColor(Color.GRAY);
			g.drawRect(camera.x, camera.y - Camera.cameraBoxExtraHeight, camera.getWidth(), camera.getHeight() + Camera.cameraBoxExtraHeight);
		}

		// Player
		player.render(g);

		// Focus Objects
		//focus_bg.render(g);
	}

	void controllerUpdate(ControllerState controller) {
		player.controllerUpdate(controller);
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			game.exit();

		player.keyPressed(e);
	}

	public void keyReleased(KeyEvent e) {
		player.keyReleased(e);
	}

	public void mousePressed(MouseEvent e) {
		player.mousePressed(e);
	}

	public void mouseReleased(MouseEvent e) {
		player.mouseReleased(e);
	}
}
