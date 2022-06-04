package Project_Station_8;

import java.awt.*;
import java.util.ArrayList;

public class Map {
	Game game;

	ArrayList<ArrayList<Tile>> tilesList = new ArrayList<>();
	ArrayList<Tile> tiles;

	Map(Game game) {
		this.game = game;
		char[][] mapFile;

		// Floor
		mapFile = new char[][]{
				{'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#',},
				{'#',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','#',},
				{'#',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','#',},
				{'#',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','#',},
				{'#',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','#',},
				{'#',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','#',},
				{'#',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','#',},
				{'#',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ','#',},
				{'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#',}
		};
		tilesList.add(generateTiles(mapFile));

		// Floor
		mapFile = new char[][]{
				{' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',},
				{' ','@','@','@','@','@','@','@','@','@','@','@','@','@',' ',},
				{' ','@','@','@','@','@','@','@','@','@','@','@','@','@',' ',},
				{' ','@','@','@','@','@','@','@','@','@','@','@','@','@',' ',},
				{' ','@','@','@','@','@','@','@','@','@','@','@','@','@',' ',},
				{' ','@','@','@','@','@','@','@','@','@','@','@','@','@',' ',},
				{' ','@','@','@','@','@','@','@','@','@','@','@','@','@',' ',},
				{' ','@','@','@','@','@','@','@','@','@','@','@','@','@',' ',},
				{' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',' ',}
		};
		tilesList.add(generateTiles(mapFile));
	}

	private ArrayList<Tile> generateTiles(char[][] mapFile) {
		tiles = new ArrayList<>();
		for(int y = 0; y < 9; y++) {
			for(int x = 0; x < 15; x++) {
				char c = mapFile[y][x];
				int tileAssignment = 0;

				// Floor
				if(c == '#') {
					if(checkNeighbors(x, y - 1, mapFile)) tileAssignment += 1;
					if(checkNeighbors(x + 1, y, mapFile)) tileAssignment += 2;
					if(checkNeighbors(x, y + 1, mapFile)) tileAssignment += 4;
					if(checkNeighbors(x - 1, y, mapFile)) tileAssignment += 8;

					tiles.add(new Tile(game, x, y, Tile.SCIFI_FLOOR_000, tileAssignment));
				}

				if(c == '@') {
					if(checkNeighbors(x, y - 1, mapFile)) tileAssignment += 1;
					if(checkNeighbors(x + 1, y, mapFile)) tileAssignment += 2;
					if(checkNeighbors(x, y + 1, mapFile)) tileAssignment += 4;
					if(checkNeighbors(x - 1, y, mapFile)) tileAssignment += 8;

					tiles.add(new Tile(game, x, y, Tile.SCIFI_FLOOR_001, tileAssignment));
				}

				// Empty
				if(c == ' ') {
					tiles.add(new Tile(game, x, y, Tile.EMPTY, tileAssignment));
				}
			}
		}
		return tiles;
	}

	private boolean checkNeighbors(int x, int y, char[][] mapFile) {
		try {
			char c = mapFile[y][x];
			if(c == '#') {
				return true;
			}
			if(c == '@') {
				return true;
			}
		}
		catch(Exception e) {
			// No Character Found
		}

		return false;
	}

	public void render(Graphics2D g) {
		for(ArrayList<Tile> tiles: tilesList) {
			for(Tile t : tiles) t.render(g);
		}
	}
}
