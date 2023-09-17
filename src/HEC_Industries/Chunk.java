package HEC_Industries;

import java.awt.*;
import java.util.ArrayList;

public class Chunk {
	Game             game;
	LevelHandler     levelHandler;
	ArrayList<Chunk> levelChunks;
	ArrayList<Block> bList = new ArrayList<>();
	int              x;
	int              y;
	int              offsetX;
	int              offsetY;
	public static int chunkWidth  = 64;
	public static int chunkHeight = 64;
	char[][] chunkData;

	// Create an ArrayList to store quadrants
	ArrayList<Quadrant> quadCollection = new ArrayList<>();

	static long SEED_DIRT   = 1;
	static long SEED_IRON   = 2;
	static long SEED_COAL   = 3;
	static long SEED_COPPER = 4;

	public Chunk(Game game, int offsetX, int offsetY, boolean isStartingChunk) {
		// Get Game
		this.game = game;

		// Get Level Handler
		this.levelHandler = game.ps.levelHandler;

		// Get Existing levelChunks
		this.levelChunks = levelHandler.levelChunks;

		// Map Stuff
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.x = levelHandler.worldX + offsetX;
		this.y = levelHandler.worldY + offsetY;

		// Generate Chunk
		generateChunk(isStartingChunk);
	}

	void generateChunk(boolean isStartingChunk) {
		// Generate Dirt (With Empty Blocks, just empty spaces..)
		long     desiredSeed = Chunk.SEED_DIRT;
		char     desiredType = Block.TYPE_DIRT;
		char[][] dirtMap     = chunkGen(desiredSeed + levelHandler.game_seed, desiredType, 0.2, 0.2f);

		// Generate Iron
		desiredSeed = Chunk.SEED_IRON;
		desiredType = Block.TYPE_IRON;
		char[][] overlayMap = chunkGen(desiredSeed + levelHandler.game_seed, desiredType, 0.2, 0.2f);
		for(int y = 0; y < chunkHeight; y++) {
			for(int x = 0; x < chunkHeight; x++) {
				if(overlayMap[y][x] == desiredType && dirtMap[y][x] == Block.TYPE_DIRT) {
					dirtMap[y][x] = desiredType;
				}
			}
		}

		// Generate Copper
		desiredSeed = Chunk.SEED_COPPER;
		desiredType = Block.TYPE_COPPER;
		overlayMap = chunkGen(desiredSeed + levelHandler.game_seed, desiredType, 0.2, 0.2f);
		for(int y = 0; y < chunkHeight; y++) {
			for(int x = 0; x < chunkHeight; x++) {
				if(overlayMap[y][x] == desiredType && dirtMap[y][x] == Block.TYPE_DIRT) {
					dirtMap[y][x] = desiredType;
				}
			}
		}

		// Generate Coal
		desiredSeed = Chunk.SEED_COAL;
		desiredType = Block.TYPE_COAL;
		overlayMap = chunkGen(desiredSeed + levelHandler.game_seed, desiredType, 0.140, 0.2f);
		for(int y = 0; y < chunkHeight; y++) {
			for(int x = 0; x < chunkHeight; x++) {
				if(overlayMap[y][x] == desiredType && dirtMap[y][x] == Block.TYPE_DIRT) {
					dirtMap[y][x] = desiredType;
				}
			}
		}

		// Initialize ChunkData
		chunkData = dirtMap;

		if(isStartingChunk) {
			chunkData[31][31] = Block.TYPE_EMPTY;
			chunkData[31][32] = Block.TYPE_EMPTY;
			chunkData[31][33] = Block.TYPE_EMPTY;

			chunkData[32][31] = Block.TYPE_EMPTY;
			chunkData[32][32] = '$'; // start position
			chunkData[32][33] = Block.TYPE_EMPTY;

			chunkData[33][31] = Block.TYPE_DIRT;
			chunkData[33][32] = Block.TYPE_DIRT;
			chunkData[33][33] = Block.TYPE_DIRT;
		}

		// Display Map
		//		displayMap(chunkData, chunkData.length, chunkData[0].length);
		//		game.exit();

		// Create quadrants for the entire chunk
		for(int quadrantY = 0; quadrantY < 8; quadrantY++) {
			for(int quadrantX = 0; quadrantX < 8; quadrantX++) {
				quadCollection.add(new Quadrant(game, quadrantX, quadrantY, this));
			}
		}

		// Generate Blocks
		for (int chunkY = 0; chunkY < chunkHeight; chunkY++) {
			for (int chunkX = 0; chunkX < chunkWidth; chunkX++) {
				char blockType = chunkData[chunkY][chunkX];

				// Create and add the block to the corresponding quadrant
				Block block = new Block(game, offsetX + (chunkX * Block.blockWidth), offsetY + (chunkY * Block.blockHeight), blockType);
				if(blockType == Block.TYPE_EMPTY) {
					block.blockType = Block.TYPE_DIRT;
					block.condition = 0;
					block.isHidden = false;
				}

				// Start Location
				if(blockType == '$') {
					block = new Block(game, offsetX + (chunkX * Block.blockWidth), offsetY + (chunkY * Block.blockHeight), Block.TYPE_DIRT);
					block.condition = 0;
					block.isHidden = false;
					levelHandler.startLocation = new EmptyObject(game, offsetX + (chunkX * Block.blockWidth), offsetY + (chunkY * Block.blockHeight), Block.blockWidth, Block.blockHeight);
				}

				addBlock(block);

				// Calculate quadrant coordinates
				int quadrantY = chunkY / 8;
				int quadrantX = chunkX / 8;

				// Add
				quadCollection.get(quadrantY * 8 + quadrantX).blocks.add(block);
			}
		}

		// List Quad Sizes
		// for(Quadrant quad: quadCollection) System.out.println("Quad["+quad.x+","+quad.y+"] size: " + quad.blocks.size());
	}

	char[][] chunkGen(long seed, char desiredBlock, double threshold, float maxProbability) {
		// Default Modifiers
		char defaultBlock = Block.TYPE_DIRT;
		//long  seed           = 123457;
		//char  desiredBlock    = Block.TYPE_EMPTY;
		//float maxProbability = 0.2f;
		//double threshold = 0.2;

		// Initialize Noise Generator
		OpenSimplexNoise openSimplexNoise = new OpenSimplexNoise(seed);

		// Initialize Chunk Map
		char[][] chunkMap = new char[chunkHeight][chunkWidth];

		// Stage 1 - Noise Gen
		for(int y = 0; y < chunkMap.length; y++) {
			for(int x = 0; x < chunkMap[0].length; x++) {
				chunkMap[y][x] = defaultBlock;

				double noise       = openSimplexNoise.eval(x, y, 0);
				double scaledNoise = (noise + 1.0) / 2;
				if(scaledNoise < threshold)
					chunkMap[y][x] = desiredBlock;
			}
		}

		// Stage 2 - Terrain Refinement
		for(int y = 0; y < chunkMap.length; y++) {
			for(int x = 0; x < chunkMap[0].length; x++) {
				char currentTile = chunkMap[y][x];
				if(currentTile == desiredBlock) {
					// Check nearby tiles and convert them to the desired tile within a maxProbability
					for(int i = -1; i <= 1; i++) {
						for(int j = -1; j <= 1; j++) {
							int newX = x + i;
							int newY = y + j;
							if(newX >= 0 && newX < chunkMap[0].length && newY >= 0 && newY < chunkMap.length) {
								//chunkMap[newY][newX] = desiredBlock;
								if(Math.random() < maxProbability) { // Adjust this probability as needed
									chunkMap[newY][newX] = desiredBlock;
								}
							}
						}
					}
				}
			}
		}

		return chunkMap;
	}

	void addBlock(Block block) {
		bList.add(block);
	}

	void displayMap(char[][] map, int maxY, int maxX) {
		for(int y = 0; y < maxY; y++) {
			for(int x = 0; x < maxX; x++) {
				System.out.print(map[y][x]);
			}
			System.out.println();
		}
	}

	boolean containsPlayer() {
		Player player = game.ps.levelHandler.player;
		return (player.x > this.x) && (player.x < this.x + (Chunk.chunkWidth * Block.blockWidth) && (player.y > this.y) && (player.y < this.y + (Chunk.chunkHeight * Block.blockHeight)));
	}

	int getWidth() {
		return (Chunk.chunkWidth * Block.blockWidth);
	}

	int getHeight() {
		return (Chunk.chunkHeight * Block.blockHeight);
	}

	boolean containsEntity(int ex, int ey, int ew, int eh) {
		int e_center_x = ex + (ew / 2);
		int e_center_y = ey + (eh / 2);

		int chunk_x = this.x;
		int chunk_y = this.y;
		int chunk_w = chunkWidth * Block.blockWidth;
		int chunk_h = chunkHeight * Block.blockHeight;

		return (e_center_x >= chunk_x && e_center_x <= chunk_x + chunk_w) && (e_center_y >= chunk_y && e_center_y <= chunk_y + chunk_h);
	}

	public void update() {
		x = game.ps.levelHandler.worldX + offsetX;
		y = game.ps.levelHandler.worldY + offsetY;

		for(Block b : bList) {
			b.update();
		}
	}

	public void render(Graphics2D g) {
		for(Block b : bList) {
			b.render(g);
		}
	}
}
