package platformer;

import java.util.ArrayList;

@SuppressWarnings(value = { "all" })
public class LevelGenerator {
	Platformer			game;

	ArrayList<Room>	rooms	= new ArrayList<>();

	public LevelGenerator(Platformer game) {
		this.game = game;
	}

	public void generateLevel() {
		generateRooms();
		parseRooms();
	}

	void generateRooms() {
		rooms = new ArrayList<Room>();
		generateUpper();
		generateMiddle(1);
		generateMiddle(2);
		generateBottom();
	}

	void generateUpper() {
		int entrance = Utility.random(1, 4);
		
		rooms.add(new Room(Room.UPPER, Room.LEFT, (entrance == 1) ? true : false, false, 0, 0));
		rooms.add(new Room(Room.UPPER, Room.CENTER, (entrance == 2) ? true : false, false, 1, 0));
		rooms.add(new Room(Room.UPPER, Room.CENTER, (entrance == 3) ? true : false, false, 2, 0));
		rooms.add(new Room(Room.UPPER, Room.RIGHT, (entrance == 4) ? true : false, false, 3, 0));
	}

	void generateMiddle(int level) {
		int topBottom = (level == 1) ? Room.MIDDLE_TOP : Room.MIDDLE_BTM;
		
		rooms.add(new Room(topBottom, Room.LEFT, false, false, 0, level));
		rooms.add(new Room(topBottom, Room.CENTER, false, false, 1, level));
		rooms.add(new Room(topBottom, Room.CENTER, false, false, 2, level));
		rooms.add(new Room(topBottom, Room.RIGHT, false, false, 3, level));
	}

	void generateBottom() {
		int exit = Utility.random(1, 4);
		
		rooms.add(new Room(Room.BOTTOM, Room.LEFT, false, (exit == 1) ? true : false, 0, 3));
		rooms.add(new Room(Room.BOTTOM, Room.CENTER, false, (exit == 2) ? true : false, 1, 3));
		rooms.add(new Room(Room.BOTTOM, Room.CENTER, false, (exit == 3) ? true : false, 2, 3));
		rooms.add(new Room(Room.BOTTOM, Room.RIGHT, false, (exit == 4) ? true : false, 3, 3));
	}

	void parseRooms() {
		getState().clist = new ArrayList<>();

		for(Room room:rooms) {
			for(int i = 0; i < 8; i++) {
				for(int j = 0; j < 10; j++) {
					char c = room.room[i][j];

					if(c == '#')
						getState().clist.add(new Collidable(game, room.x + j * 50, room.y + i * 50));

					if(c == '@')
						getState().p = new Player(game, room.x + j * 50, room.y + i * 50);
				}
			}
		}
	}

	PlayState getState() {
		return (PlayState)game.stateMachine.getCurrentState();
	}
}
