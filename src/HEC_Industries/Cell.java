package HEC_Industries;

import java.util.ArrayList;

public class Cell {
	int x, y;
	char type;
	ArrayList<Cell> neighbors = new ArrayList<>();
	boolean wall = true;

	Cell(int x, int y, char type) {
		this.x = x;
		this.y = y;
		this.type = type;
	}
}
