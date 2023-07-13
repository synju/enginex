package Project_Platformer;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Chunk {
	Game game;
	int x;
	int y;
	int offsetX;
	int offsetY;
	LevelHandler levelHandler;
	ChunkTypes chunkType;
	char[][] map;
	char[][] chunkData;
	char chunkChar;
	int mapX;
	int mapY;
	ArrayList<Chunk> levelChunks;
	ArrayList<ChunkTypes> compatibilityList;
	ArrayList<Collidable> cList = new ArrayList<>();

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

	public Chunk(Game game, LevelHandler levelHandler, int offsetX, int offsetY, char[][] map, int mapX, int mapY, char chunkChar) {
		// Get Game
		this.game = game;

		// Map Stuff
		this.map = map;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.x = game.ps.levelHandler.worldX + offsetX;
		this.y = game.ps.levelHandler.worldY + offsetY;
		this.mapX = mapX;
		this.mapY = mapY;
		this.chunkChar = chunkChar;

		// Get Level Handler
		this.levelHandler = levelHandler;

		// Get Existing levelChunks
		this.levelChunks = levelHandler.levelChunks;

		// Get Chunk Collections
		a_up_chunks = levelHandler.a_up_chunks;
		a_down_chunks = levelHandler.a_down_chunks;
		a_left_chunks = levelHandler.a_left_chunks;
		a_right_chunks = levelHandler.a_right_chunks;
		b_up_chunks = levelHandler.b_up_chunks;
		b_down_chunks = levelHandler.b_down_chunks;
		b_left_chunks = levelHandler.b_left_chunks;
		b_right_chunks = levelHandler.b_right_chunks;
		c_up_chunks = levelHandler.c_up_chunks;
		c_down_chunks = levelHandler.c_down_chunks;
		c_left_chunks = levelHandler.c_left_chunks;
		c_right_chunks = levelHandler.c_right_chunks;
		d_chunks = levelHandler.d_chunks;
		e_vertical_chunks = levelHandler.e_vertical_chunks;
		e_horizontal_chunks = levelHandler.e_horizontal_chunks;
		solid_chunks = levelHandler.solid_chunks;

		// Setup Compatibility List
		compatibilityList = new ArrayList<>();
		setupCompatibilityList();

		// Set chunkType
		chunkType = compatibilityList.get(new Random().nextInt(compatibilityList.size()));

		// Chunk Data
		if(chunkType == ChunkTypes.A0) chunkData = a_up_chunks.get(new Random().nextInt(a_up_chunks.size()));
		if(chunkType == ChunkTypes.A1) chunkData = a_right_chunks.get(new Random().nextInt(a_right_chunks.size()));
		if(chunkType == ChunkTypes.A2) chunkData = a_down_chunks.get(new Random().nextInt(a_down_chunks.size()));
		if(chunkType == ChunkTypes.A3) chunkData = a_left_chunks.get(new Random().nextInt(a_left_chunks.size()));
		if(chunkType == ChunkTypes.B0) chunkData = b_up_chunks.get(new Random().nextInt(b_up_chunks.size()));
		if(chunkType == ChunkTypes.B1) chunkData = b_right_chunks.get(new Random().nextInt(b_right_chunks.size()));
		if(chunkType == ChunkTypes.B2) chunkData = b_down_chunks.get(new Random().nextInt(b_down_chunks.size()));
		if(chunkType == ChunkTypes.B3) chunkData = b_left_chunks.get(new Random().nextInt(b_left_chunks.size()));
		if(chunkType == ChunkTypes.C0) chunkData = c_up_chunks.get(new Random().nextInt(c_up_chunks.size()));
		if(chunkType == ChunkTypes.C1) chunkData = c_right_chunks.get(new Random().nextInt(c_right_chunks.size()));
		if(chunkType == ChunkTypes.C2) chunkData = c_down_chunks.get(new Random().nextInt(c_down_chunks.size()));
		if(chunkType == ChunkTypes.C3) chunkData = c_left_chunks.get(new Random().nextInt(c_left_chunks.size()));
		if(chunkType == ChunkTypes.D0) chunkData = d_chunks.get(new Random().nextInt(d_chunks.size()));
		if(chunkType == ChunkTypes.E0) chunkData = e_vertical_chunks.get(new Random().nextInt(e_vertical_chunks.size()));
		if(chunkType == ChunkTypes.E1) chunkData = e_horizontal_chunks.get(new Random().nextInt(e_horizontal_chunks.size()));
		if(chunkType == ChunkTypes.F0) chunkData = solid_chunks.get(new Random().nextInt(solid_chunks.size()));

		// Generate Chunk
		generateChunk();
	}

	public void update() {
		x = game.ps.levelHandler.worldX + offsetX;
		y = game.ps.levelHandler.worldY + offsetY;

		for(Collidable c : cList) {
			c.update();
		}
	}

	public void render(Graphics2D g) {
		for(Collidable c: cList) {
			c.render(g);
		}
	}

	void generateChunk() {
		// Collider Generation
		for(int chunkY = 0; chunkY < 8; chunkY++) {
			for(int chunkX = 0; chunkX < 10; chunkX++) {
				char c = chunkData[chunkY][chunkX];

				// "." = Empty Space.. Do Nothing
				if(c == '$') levelHandler.startLocations.add(new EmptyObject(game, offsetX + (chunkX*64), offsetY + (chunkY*64), 64, 64));

				// Walls
				if(c == '#') addCollider(offsetX + (chunkX*64), offsetY + (chunkY*64));
			}
		}
	}

	void addCollider(int x, int y) {
		cList.add(new Collidable(game, x, y, 64, 64));
	}

	private void setupCompatibilityList() {
		if(chunkChar != '#') {
			boolean up;
			boolean down;
			boolean left;
			boolean right;
			if(mapY == 0) {
				// its in the top row
				if(mapX == 0) {
					// its in the top left corner
					down = (map[mapY + 1][mapX] == '.');
					right = (map[mapY][mapX + 1] == '.');

					if(right && down)
						compatibilityList.add(ChunkTypes.B1);
					if(!right && down)
						compatibilityList.add(ChunkTypes.A2);
					if(right && !down)
						compatibilityList.add(ChunkTypes.A1);
				}
				if(mapX > 0 && mapX < (map[0].length - 1)) {
					// its in the top middle
					down = (map[mapY + 1][mapX] == '.');
					left = (map[mapY][mapX - 1] == '.');
					right = (map[mapY][mapX + 1] == '.');

					if(left && down && right)
						compatibilityList.add(ChunkTypes.C1);
					if(!left && down && right)
						compatibilityList.add(ChunkTypes.B1);
					if(left && !down && right)
						compatibilityList.add(ChunkTypes.E1);
					if(left && down && !right)
						compatibilityList.add(ChunkTypes.B2);
					if(!left && !down && right)
						compatibilityList.add(ChunkTypes.A1);
					if(!left && down && !right)
						compatibilityList.add(ChunkTypes.A2);
					if(left && !down && !right)
						compatibilityList.add(ChunkTypes.A3);
				}
				if(mapX == map[0].length - 1) {
					// its in the top right corner
					down = (map[mapY + 1][mapX] == '.');
					left = (map[mapY][mapX - 1] == '.');

					if(down && left)
						compatibilityList.add(ChunkTypes.B2);
					if(down && !left)
						compatibilityList.add(ChunkTypes.A2);
					if(!down && left)
						compatibilityList.add(ChunkTypes.A3);
				}
			}
			if(mapY > 0 && mapY < (map.length - 1)) {
				// its in the middle row
				if(mapX == 0) {
					// its in the middle left side
					up = (map[mapY - 1][mapX] == '.');
					down = (map[mapY + 1][mapX] == '.');
					right = (map[mapY][mapX + 1] == '.');

					if(up && right && down)
						compatibilityList.add(ChunkTypes.C0);
					if(!up && right && down)
						compatibilityList.add(ChunkTypes.B1);
					if(up && !right && down)
						compatibilityList.add(ChunkTypes.E0);
					if(up && right && !down)
						compatibilityList.add(ChunkTypes.B0);
					if(!up && !right && down)
						compatibilityList.add(ChunkTypes.A2);
					if(up && !right && !down)
						compatibilityList.add(ChunkTypes.A0);
					if(!up && right && !down)
						compatibilityList.add(ChunkTypes.A1);
				}
				if(mapX > 0 && mapX < (map[0].length - 1)) {
					// its in the middle middle
					up = (map[mapY - 1][mapX] == '.');
					down = (map[mapY + 1][mapX] == '.');
					left = (map[mapY][mapX - 1] == '.');
					right = (map[mapY][mapX + 1] == '.');

					if(left && up && down && right)
						compatibilityList.add(ChunkTypes.D0);
					if(left && up && !down && right)
						compatibilityList.add(ChunkTypes.C3);
					if(!left && up && down && right)
						compatibilityList.add(ChunkTypes.C0);
					if(left && !up && down && right)
						compatibilityList.add(ChunkTypes.C1);
					if(left && up && down && !right)
						compatibilityList.add(ChunkTypes.C2);
					if(left && !up && down && !right)
						compatibilityList.add(ChunkTypes.B2);
					if(left && up && !down && !right)
						compatibilityList.add(ChunkTypes.B3);
					if(!left && up && !down && right)
						compatibilityList.add(ChunkTypes.B0);
					if(!left && !up && down && right)
						compatibilityList.add(ChunkTypes.B1);
					if(left && !up && !down && right)
						compatibilityList.add(ChunkTypes.E1);
					if(!left && up && down && !right)
						compatibilityList.add(ChunkTypes.E0);
					if(!left && !up && down && !right)
						compatibilityList.add(ChunkTypes.A2);
					if(left && !up && !down && !right)
						compatibilityList.add(ChunkTypes.A3);
					if(!left && up && !down && !right)
						compatibilityList.add(ChunkTypes.A0);
					if(!left && !up && !down && right)
						compatibilityList.add(ChunkTypes.A1);
				}
				if(mapX == map[0].length - 1) {
					// its in the middle right side
					up = (map[mapY - 1][mapX] == '.');
					down = (map[mapY + 1][mapX] == '.');
					left = (map[mapY][mapX - 1] == '.');

					if(down && left && up)
						compatibilityList.add(ChunkTypes.C2);
					if(down && left && !up)
						compatibilityList.add(ChunkTypes.B2);
					if(down && !left && up)
						compatibilityList.add(ChunkTypes.E0);
					if(!down && left && up)
						compatibilityList.add(ChunkTypes.B3);
					if(down && !left && !up)
						compatibilityList.add(ChunkTypes.A2);
					if(!down && !left && up)
						compatibilityList.add(ChunkTypes.A0);
					if(!down && left && !up)
						compatibilityList.add(ChunkTypes.A3);
				}
			}
			if(mapY == (map.length - 1)) {
				// its in the bottom row
				if(mapX == 0) {
					// its in the bottom left side
					up = (map[mapY - 1][mapX] == '.');
					right = (map[mapY][mapX + 1] == '.');

					if(up && right)
						compatibilityList.add(ChunkTypes.B0);
					if(!up && right)
						compatibilityList.add(ChunkTypes.A1);
					if(up && !right)
						compatibilityList.add(ChunkTypes.A0);
				}
				if(mapX > 0 && mapX < (map[0].length - 1)) {
					// its in the bottom middle
					up = (map[mapY - 1][mapX] == '.');
					left = (map[mapY][mapX - 1] == '.');
					right = (map[mapY][mapX + 1] == '.');

					if(left && up && right)
						compatibilityList.add(ChunkTypes.C3);
					if(!left && up && right)
						compatibilityList.add(ChunkTypes.B0);
					if(left && !up && right)
						compatibilityList.add(ChunkTypes.E1);
					if(left && up && !right)
						compatibilityList.add(ChunkTypes.B3);
					if(!left && !up && right)
						compatibilityList.add(ChunkTypes.A1);
					if(left && !up && !right)
						compatibilityList.add(ChunkTypes.A3);
					if(!left && up && !right)
						compatibilityList.add(ChunkTypes.A0);
				}
				if(mapX == map[0].length - 1) {
					// its in the bottom right side
					up = (map[mapY - 1][mapX] == '.');
					left = (map[mapY][mapX - 1] == '.');

					if(left && up)
						compatibilityList.add(ChunkTypes.B3);
					if(left && !up)
						compatibilityList.add(ChunkTypes.A3);
					if(!left && up)
						compatibilityList.add(ChunkTypes.A0);
				}
			}
		}
		else {
			compatibilityList.add(ChunkTypes.F0);
		}
	}
}
