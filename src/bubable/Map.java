package bubable;

import java.awt.*;
import java.util.ArrayList;

public class Map {
	Game game;
	char[][] mapFile;

	ArrayList<Tile> tiles = new ArrayList<>();

	Map(Game game) {
		this.game = game;
		mapFile = new char[][]{
				{'#', '#', '#', ' ', '#'},
				{'#', '#', '#', ' ', '#'},
				{'#', '#', '#', ' ', '#'},
				{' ', ' ', ' ', ' ', ' '},
				{'#', '#', '#', ' ', '#'}
		};
		generateTiles();
	}

	private void generateTiles() {
		for(int y = 0; y < 5; y++) {
			for(int x = 0; x < 5; x++) {
				char c = mapFile[y][x];

				int tileAssignment = 0;

				// tile is wall
				if(c == '#') {
					if(checkForWall(x, y - 1)) tileAssignment += 1;
					if(checkForWall(x + 1, y)) tileAssignment += 2;
					if(checkForWall(x, y + 1)) tileAssignment += 4;
					if(checkForWall(x - 1, y)) tileAssignment += 8;

					if(tileAssignment == 1) System.out.println("found");
					tiles.add(new Tile(game, x, y, Tile.WALL, tileAssignment));
				}

				if(c == ' ') {
					tiles.add(new Tile(game, x, y, Tile.EMPTY, tileAssignment));
				}
			}
		}
	}

	private boolean checkForWall(int x, int y) {
		try {
			if(mapFile[y][x] == '#') {
				return true;
			}
		}
		catch(Exception e) {
			// No Character Found
		}

		return false;
	}

	public void render(Graphics2D g) {
		for(Tile t : tiles)
			t.render(g);
	}
}
