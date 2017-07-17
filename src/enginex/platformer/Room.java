package enginex.platformer;

import java.util.ArrayList;

import enginex.core.Util;

public class Room {
	public static final int	A0				= 0;
	public static final int	A1				= 1;
	public static final int	A2				= 2;
	public static final int	A3				= 3;
	public static final int	B0				= 4;
	public static final int	B1				= 5;
	public static final int	B2				= 6;
	public static final int	B3				= 7;
	public static final int	C0				= 8;
	public static final int	C1				= 9;
	public static final int	C2				= 10;
	public static final int	C3				= 11;
	public static final int	D0				= 12;
	public static final int	D1				= 13;
	public static final int	D2				= 14;
	public static final int	D3				= 15;
	
	public static final int	START			= 0;
	public static final int	NORMAL		= 1;
	public static final int	EXIT			= 2;
	
	public int							position	= 0;
	public char[][]					room			= null;
	int											x					= 0;
	int											y					= 0;
	int											type			= 0;
	
	public Room(int position, int type) {
		this.position = position;
		this.type = type;
		
		// A
		if(position == A0) {
			this.room = A0(type);
			x = 0;
			y = 0;
		}
		if(position == A1) {
			this.room = A1(type);
			x = 1;
			y = 0;
		}
		if(position == A2) {
			this.room = A2(type);
			x = 2;
			y = 0;
		}
		if(position == A3) {
			this.room = A3(type);
			x = 3;
			y = 0;
		}
		
		// B
		if(position == B0) {
			this.room = B0(type);
			x = 0;
			y = 1;
		}
		if(position == B1) {
			this.room = B1(type);
			x = 1;
			y = 1;
		}
		if(position == B2) {
			this.room = B2(type);
			x = 2;
			y = 1;
		}
		if(position == B3) {
			this.room = B3(type);
			x = 3;
			y = 1;
		}
		
		// C
		if(position == C0) {
			this.room = C0(type);
			x = 0;
			y = 2;
		}
		if(position == C1) {
			this.room = C1(type);
			x = 1;
			y = 2;
		}
		if(position == C2) {
			this.room = C2(type);
			x = 2;
			y = 2;
		}
		if(position == C3) {
			this.room = C3(type);
			x = 3;
			y = 2;
		}
		
		// D
		if(position == D0) {
			this.room = D0(type);
			x = 0;
			y = 3;
		}
		if(position == D1) {
			this.room = D1(type);
			x = 1;
			y = 3;
		}
		if(position == D2) {
			this.room = D2(type);
			x = 2;
			y = 3;
		}
		if(position == D3) {
			this.room = D3(type);
			x = 3;
			y = 3;
		}
	}
	
	// LEVEL A
	public static char[][] A0(int type) {
		ArrayList<char[][]> generatedRooms = new ArrayList<>();
		
		char c = (type == START) ? '$' : ' ';
		
		generatedRooms.add(new char[][] {{'#', '#', '#', '#', '#', '#', '#', '#', '#', '#'}, {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {'#', ' ', ' ', ' ', c, '#', ' ', ' ', ' ', ' '}, {'#', ' ', ' ', '#', '#', '#', '#', '#', '#', '#'}, {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}});
		
		return generatedRooms.get(Util.getRandom(0, generatedRooms.size() - 1));
	}
	
	public static char[][] A1(int type) {
		ArrayList<char[][]> generatedRooms = new ArrayList<>();
		
		char c = (type == START) ? '$' : ' ';
		
		generatedRooms.add(new char[][] {{'#', '#', '#', '#', '#', '#', '#', '#', '#', '#'}, {' ', ' ', ' ', ' ', '#', ' ', ' ', ' ', ' ', ' '}, {' ', ' ', ' ', '#', '#', ' ', ' ', ' ', ' ', ' '}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {' ', ' ', ' ', ' ', c, ' ', '#', ' ', ' ', ' '}, {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#'}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}});
		
		return generatedRooms.get(Util.getRandom(0, generatedRooms.size() - 1));
	}
	
	public static char[][] A2(int type) {
		ArrayList<char[][]> generatedRooms = new ArrayList<>();
		
		char c = (type == START) ? '$' : ' ';
		
		generatedRooms.add(new char[][] {{'#', '#', '#', '#', '#', '#', '#', '#', '#', '#'}, {' ', ' ', ' ', ' ', '#', ' ', ' ', ' ', ' ', ' '}, {' ', ' ', ' ', ' ', '#', '#', ' ', ' ', ' ', ' '}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {' ', ' ', ' ', ' ', c, ' ', ' ', '#', ' ', ' '}, {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#'}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}});
		
		return generatedRooms.get(Util.getRandom(0, generatedRooms.size() - 1));
	}
	
	public static char[][] A3(int type) {
		ArrayList<char[][]> generatedRooms = new ArrayList<>();
		
		char c = (type == START) ? '$' : ' ';
		
		generatedRooms.add(new char[][] {{'#', '#', '#', '#', '#', '#', '#', '#', '#', '#'}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'}, {' ', ' ', ' ', ' ', ' ', c, '#', ' ', ' ', '#'}, {'#', '#', '#', '#', '#', '#', '#', ' ', ' ', '#'}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'}});
		
		return generatedRooms.get(Util.getRandom(0, generatedRooms.size() - 1));
	}
	
	// LEVEL B
	public static char[][] B0(int type) {
		ArrayList<char[][]> generatedRooms = new ArrayList<>();
		generatedRooms.add(new char[][] {{'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {'#', ' ', '#', '#', '#', '#', '#', '#', '#', ' '}, {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}});
		return generatedRooms.get(Util.getRandom(0, generatedRooms.size() - 1));
	}
	
	public static char[][] B1(int type) {
		ArrayList<char[][]> generatedRooms = new ArrayList<>();
		generatedRooms.add(new char[][] {{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {' ', ' ', '#', '#', '#', '#', '#', '#', ' ', ' '}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}});
		return generatedRooms.get(Util.getRandom(0, generatedRooms.size() - 1));
	}
	
	public static char[][] B2(int type) {
		ArrayList<char[][]> generatedRooms = new ArrayList<>();
		generatedRooms.add(new char[][] {{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {' ', ' ', '#', '#', '#', '#', '#', '#', ' ', ' '}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}});
		return generatedRooms.get(Util.getRandom(0, generatedRooms.size() - 1));
	}
	
	public static char[][] B3(int type) {
		ArrayList<char[][]> generatedRooms = new ArrayList<>();
		generatedRooms.add(new char[][] {{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'}, {' ', ' ', '#', '#', '#', '#', '#', '#', ' ', '#'}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'}});
		return generatedRooms.get(Util.getRandom(0, generatedRooms.size() - 1));
	}
	
	// LEVEL C
	public static char[][] C0(int type) {
		ArrayList<char[][]> generatedRooms = new ArrayList<>();
		generatedRooms.add(new char[][] {{'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}});
		return generatedRooms.get(Util.getRandom(0, generatedRooms.size() - 1));
	}
	
	public static char[][] C1(int type) {
		ArrayList<char[][]> generatedRooms = new ArrayList<>();
		generatedRooms.add(new char[][] {{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}});
		return generatedRooms.get(Util.getRandom(0, generatedRooms.size() - 1));
	}
	
	public static char[][] C2(int type) {
		ArrayList<char[][]> generatedRooms = new ArrayList<>();
		generatedRooms.add(new char[][] {{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}});
		return generatedRooms.get(Util.getRandom(0, generatedRooms.size() - 1));
	}
	
	public static char[][] C3(int type) {
		ArrayList<char[][]> generatedRooms = new ArrayList<>();
		generatedRooms.add(new char[][] {{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'}});
		return generatedRooms.get(Util.getRandom(0, generatedRooms.size() - 1));
	}
	
	// LEVEL D
	public static char[][] D0(int type) {
		ArrayList<char[][]> generatedRooms = new ArrayList<>();
		generatedRooms.add(new char[][] {{'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {'#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#'}});
		return generatedRooms.get(Util.getRandom(0, generatedRooms.size() - 1));
	}
	
	public static char[][] D1(int type) {
		ArrayList<char[][]> generatedRooms = new ArrayList<>();
		generatedRooms.add(new char[][] {{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {' ', '#', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#'}});
		return generatedRooms.get(Util.getRandom(0, generatedRooms.size() - 1));
	}
	
	public static char[][] D2(int type) {
		ArrayList<char[][]> generatedRooms = new ArrayList<>();
		generatedRooms.add(new char[][] {{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#', ' '}, {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#'}});
		return generatedRooms.get(Util.getRandom(0, generatedRooms.size() - 1));
	}
	
	public static char[][] D3(int type) {
		ArrayList<char[][]> generatedRooms = new ArrayList<>();
		generatedRooms.add(new char[][] {{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '#'}, {'#', '#', '#', '#', '#', '#', '#', '#', '#', '#'}});
		return generatedRooms.get(Util.getRandom(0, generatedRooms.size() - 1));
	}
}
