package Project_Platformer;

import EngineX.Util;
import com.studiohartman.jamepad.ControllerState;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.*;

public class LevelHandler {
	Game game;
	int worldX;
	int worldY;
	EmptyObject startLocation;
	ArrayList<EmptyObject> startLocations;
	ArrayList<Collidable> cList;
	GraphicObject grid_bg;
	GraphicObject focus_bg;
	Player p;
	ArrayList<Chunk> levelChunks;
	//	char WALL_CHAR = '█';
	//	char PASSAGE_CHAR = ' ';
	char WALL_CHAR = '#';
	char PASSAGE_CHAR = '.';

	ArrayList<char[][]> a_up_chunks;
	ArrayList<char[][]> a_down_chunks;
	ArrayList<char[][]> a_left_chunks;
	ArrayList<char[][]> a_right_chunks;
	ArrayList<char[][]> b_up_chunks;
	ArrayList<char[][]> b_down_chunks;
	ArrayList<char[][]> b_left_chunks;
	ArrayList<char[][]> b_right_chunks;
	ArrayList<char[][]> c_up_chunks;
	ArrayList<char[][]> c_down_chunks;
	ArrayList<char[][]> c_left_chunks;
	ArrayList<char[][]> c_right_chunks;
	ArrayList<char[][]> d_chunks;
	ArrayList<char[][]> e_vertical_chunks;
	ArrayList<char[][]> e_horizontal_chunks;
	ArrayList<char[][]> solid_chunks;

	EmptyObject cameraBox;
	int cameraBoxWidth;
	int cameraBoxHeight;
	int cameraBoxExtraHeight;
	boolean cameraCorrection;
	boolean sideToSideCamera;
	boolean upAndDownCamera;
	boolean renderCameraBox = false;

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
		//		grid_bg = new GraphicObject(game, game.res.grid_bg.getPath(), worldX + 0, worldY + 0);

		// Generate Level
		startLocations = new ArrayList<>();
		generateLevel();
		startLocation = startLocations.get(new Random().nextInt(startLocations.size()));

		// Player
		p = new Player(game, startLocation.x, startLocation.y);

		// Focus BG
		focus_bg = new GraphicObject(game, game.res.focus_bg.getPath(), (int)p.x+p.w/2 - game.width,(int)p.y+p.h/2 - game.width);

		// CameraBox
		cameraBoxWidth = 3 * 64;
		cameraBoxHeight = 3 * 64;
		//		cameraBoxExtraHeight = 28;
		cameraBoxExtraHeight = 15;
		cameraCorrection = true;
		sideToSideCamera = true;
		upAndDownCamera = true;
		renderCameraBox = false;
		cameraBox = new EmptyObject(game, (worldX + ((game.getWidth() / 2) - (cameraBoxWidth / 2))), (worldY + (((game.getHeight() / 2) - (cameraBoxHeight - 32)) - cameraBoxExtraHeight)), (cameraBoxWidth), cameraBoxHeight + cameraBoxExtraHeight);

		initialized = true;
	}

	public void update() {
		postInit();

		// Graphic Objects
		//		grid_bg.update();

		// Focus Object
		focus_bg.update();

		// Update Player...
		p.update();

		focus_bg.x = (int)p.x + p.w/2 - game.width;
		focus_bg.y = (int)p.y + p.h/2 - game.width;

		// Camera Correction...
		cameraCorrection();

		for(Chunk chunk : levelChunks) {
			chunk.update();
		}

		startLocation.update();
	}

	public void render(Graphics2D g) {
		// Graphic Objects
		//		grid_bg.render(g);

		// Collision Objects
		for(Chunk chunk : levelChunks) {
			if((chunk.x >= -game.width && chunk.x <= game.width) && (chunk.y >= -game.height && chunk.y <= game.height)) {
				chunk.render(g);
			}
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

		// Focus Objects
		focus_bg.render(g);
	}

	ArrayList<char[][]> readChunks(Game game, String filePath) {
		// Initialize Chunk Collection
		ArrayList<char[][]> chunkCollection = new ArrayList<>();

		// Chunk File
		ArrayList<String> chunkFile = Util.readText(filePath);

		int lineTotal = chunkFile.size();
		int chunkTotal = lineTotal / 8;

		for(int chunkCount = 0; chunkCount < chunkTotal; chunkCount++) {
			char[][] chunk = new char[8][10];

			for(int y = 0; y < 8; y++) {
				String chunkLine = chunkFile.get(y + (chunkCount * 8));
				//				System.out.print(y + (chunkCount * 8) + ": ");
				//				System.out.println(chunkLine);

				for(int x = 0; x < 10; x++) {
					char c = chunkLine.charAt(x);
					chunk[y][x] = c;
				}
			}

			chunkCollection.add(chunk);
		}

		return chunkCollection;
	}

	void generateLevel() {
		// Get Chunk Collections
		a_up_chunks = readChunks(game, "bin/project_platformer/chunks/a_up_chunks.txt");
		a_down_chunks = readChunks(game, "bin/project_platformer/chunks/a_down_chunks.txt");
		a_left_chunks = readChunks(game, "bin/project_platformer/chunks/a_left_chunks.txt");
		a_right_chunks = readChunks(game, "bin/project_platformer/chunks/a_right_chunks.txt");
		b_up_chunks = readChunks(game, "bin/project_platformer/chunks/b_up_chunks.txt");
		b_down_chunks = readChunks(game, "bin/project_platformer/chunks/b_down_chunks.txt");
		b_left_chunks = readChunks(game, "bin/project_platformer/chunks/b_left_chunks.txt");
		b_right_chunks = readChunks(game, "bin/project_platformer/chunks/b_right_chunks.txt");
		c_up_chunks = readChunks(game, "bin/project_platformer/chunks/c_up_chunks.txt");
		c_down_chunks = readChunks(game, "bin/project_platformer/chunks/c_down_chunks.txt");
		c_left_chunks = readChunks(game, "bin/project_platformer/chunks/c_left_chunks.txt");
		c_right_chunks = readChunks(game, "bin/project_platformer/chunks/c_right_chunks.txt");
		d_chunks = readChunks(game, "bin/project_platformer/chunks/d_chunks.txt");
		e_vertical_chunks = readChunks(game, "bin/project_platformer/chunks/e_vertical_chunks.txt");
		e_horizontal_chunks = readChunks(game, "bin/project_platformer/chunks/e_horizontal_chunks.txt");
		solid_chunks = readChunks(game, "bin/project_platformer/chunks/solid_chunks.txt");

		// Generate Map
		char[][] map = genTest();

		// Add Outside Buffers
		map = addMapBorders(map, map.length,map[0].length);
		int levelHeight = map.length;
		int levelWidth = map[0].length;

		// Display
//		displayMap(map,levelHeight,levelWidth);
//		game.exit();

		// Create Level Chunks List
		levelChunks = new ArrayList<>();

		// Generate Chunks...
		for(int y = 0; y < levelHeight; y++) {
			for(int x = 0; x < levelWidth; x++) {
//				if(map[y][x] == '.') levelChunks.add(new Chunk(game, this, x * (10 * 64), y * (8 * 64), map, x, y, map[y][x]));
				levelChunks.add(new Chunk(game, this, x * (10 * 64), y * (8 * 64), map, x, y, map[y][x]));
			}
		}
	}

	char[][] genTest() {
		// Size .. remember this will be reduced by 1 on both y and x...
		int maxX = 30;
		int maxY = 10;

		// Initialize Map
		char[][] map = initializeMap(maxY, maxX);

		// Count Passages
		int passageCount = 0;
		for(int y = 0; y < maxY; y++) {
			for(int x = 0; x < maxX; x++) {
				if(map[y][x] == PASSAGE_CHAR) {
					passageCount++;
				}
			}
		}

		// Generate Walls Only Map
		for(int y = 0; y < maxY; y++) {
			for(int x = 0; x < maxX; x++) {
				map[y][x] = WALL_CHAR;
			}
		}

		// Create Cells List
		ArrayList<Cell> cells = new ArrayList<>();

		// Add Cells From Map
		addCellsFromMap(cells, map, maxY, maxX);

		// Create Passages List
		ArrayList<Cell> passages = new ArrayList<>();

		// Select Random Starting Point
		while(passages.size() == 0) {
			int ry = Util.getRandomNumberInRange(0, maxY);
			int rx = Util.getRandomNumberInRange(0, maxX);

			// Add Cell to Passages List
			for(Cell cell : cells) {
				if(cell.y == ry && cell.x == rx) {
					cell.type = PASSAGE_CHAR;
					passages.add(cell);
					map[ry][rx] = PASSAGE_CHAR;
				}
			}
		}

		// Generate Maze using Map + Cells + Passages + maxY + maxX
		while(passages.size() < passageCount) {
			generateMaze(map, cells, passages);
		}

		// Update Map
		for(Cell cell : cells) {
			for(int y = 0; y < maxY; y++) {
				for(int x = 0; x < maxX; x++) {
					if(cell.y == y && cell.x == x) {
						map[y][x] = cell.type;
					}
				}
			}
		}

		return map;
	}

	char[][] initializeMap(int maxY, int maxX) {
		char[][] map = new char[maxY][maxX];

		// Empty Grid
		for(int y = 0; y < maxY; y++) {
			for(int x = 0; x < maxX; x++) {
				map[y][x] = WALL_CHAR;
			}
		}

		// Add Walls
		int yCount = 0;
		for(int y = 0; y < maxY; y++) {
			if(yCount == 0) {
				int xCount = 0;
				for(int x = 0; x < maxX; x++) {
					if(xCount == 0) {
						map[y][x] = PASSAGE_CHAR;
						xCount++;
					}
					else {
						xCount = 0;
					}
				}
				yCount++;
			}
			else {
				yCount = 0;
			}
		}

		return map;
	}

	void addCellsFromMap(ArrayList<Cell> cells, char[][] map, int maxY, int maxX) {
		for(int y = 0; y < maxY; y++) {
			for(int x = 0; x < maxX; x++) {
				cells.add(new Cell(x, y, map[y][x]));
			}
		}
	}

	void generateMaze(char[][] map, ArrayList<Cell> cells, ArrayList<Cell> passages) {
		// Select Random Current Cell
		Cell currentCell = passages.get(new Random().nextInt(passages.size()));

		// Check for neighbors
		ArrayList<Cell> neighbors = new ArrayList<>();
		for(Cell cell : cells) {
			if(cell.y == currentCell.y - 2 && cell.x == currentCell.x && cell.type != PASSAGE_CHAR)
				neighbors.add(cell);
			if(cell.y == currentCell.y + 2 && cell.x == currentCell.x && cell.type != PASSAGE_CHAR)
				neighbors.add(cell);
			if(cell.y == currentCell.y && cell.x == currentCell.x - 2 && cell.type != PASSAGE_CHAR)
				neighbors.add(cell);
			if(cell.y == currentCell.y && cell.x == currentCell.x + 2 && cell.type != PASSAGE_CHAR)
				neighbors.add(cell);
		}

		// If neighbors has cells, then process neighbors
		if(neighbors.size() > 0) {
			// Select Random Neighbor
			Cell randomNeighbor = neighbors.get(new Random().nextInt(neighbors.size()));

			// Change randomNeighbor to a Wall
			for(Cell cell : cells) {
				if(cell.y == randomNeighbor.y && cell.x == randomNeighbor.x) {
					cell.type = PASSAGE_CHAR;
					map[cell.y][cell.x] = PASSAGE_CHAR;
				}
			}

			// Add randomNeighbor to Walls
			passages.add(randomNeighbor);

			// Change divider cell to Wall
			for(Cell cell : cells) {
				// Up
				if(randomNeighbor.y == currentCell.y - 2 && randomNeighbor.x == currentCell.x) {
					if(cell.y == currentCell.y - 1 && cell.x == currentCell.x) {
						cell.type = PASSAGE_CHAR;
						map[cell.y][cell.x] = PASSAGE_CHAR;
					}
				}

				// Down
				if(randomNeighbor.y == currentCell.y + 2 && randomNeighbor.x == currentCell.x) {
					if(cell.y == currentCell.y + 1 && cell.x == currentCell.x) {
						cell.type = PASSAGE_CHAR;
						map[cell.y][cell.x] = PASSAGE_CHAR;
					}
				}

				// Left
				if(randomNeighbor.y == currentCell.y && randomNeighbor.x == currentCell.x - 2) {
					if(cell.y == currentCell.y && cell.x == currentCell.x - 1) {
						cell.type = PASSAGE_CHAR;
						map[cell.y][cell.x] = PASSAGE_CHAR;
					}
				}

				// Right
				if(randomNeighbor.y == currentCell.y && randomNeighbor.x == currentCell.x + 2) {
					if(cell.y == currentCell.y && cell.x == currentCell.x + 1) {
						cell.type = PASSAGE_CHAR;
						map[cell.y][cell.x] = PASSAGE_CHAR;
					}
				}
			}
		}
	}

	char[][] addMapBorders(char[][] map, int levelHeight, int levelWidth) {
		int height = levelHeight+2;
		int width = levelWidth+2;
		char[][] newMap = new char[height][width];
		for(int y = 0; y < height; y++) {
			for(int x = 0; x < width; x++) {
				if(y == 0) {
					newMap[y][x] = '#';
				}
				if(y > 0 && y < height-1) {
					if(x == 0) {
						newMap[y][x] = '#';
					}
					else if(x == width - 1) {
						newMap[y][x] = '#';
					}
					else {
						newMap[y][x] = map[y-1][x-1];
					}
				}
				if(y == height-1) {
					newMap[y][x] = '#';
				}
			}
		}
		return newMap;
	}

	void displayMap(char[][] map, int maxY, int maxX) {
		for(int y = 0; y < maxY; y++) {
			for(int x = 0; x < maxX; x++) {
				System.out.print(map[y][x]);
			}
			System.out.println();
		}
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

	void controllerUpdate(ControllerState controller) {
		p.controllerUpdate(controller);
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
