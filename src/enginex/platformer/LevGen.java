package enginex.platformer;

import java.util.ArrayList;

import enginex.core.Util;

public class LevGen {
	Platformer			game;
	ArrayList<Room>	rooms;
	int							startPos;
	int							exitPos;
	
	public LevGen(Platformer game) {
		this.game = game;
		rooms = new ArrayList<>();
	}
	
	public PlayState getState() {
		return (PlayState)game.stateMachine.getCurrentState();
	}
	
	public void generateLevel() {
		rooms = new ArrayList<>();
		
		addEntryRooms();
		addMiddleRooms();
		addExitRooms();
		
		parseRooms();
	}
	
	public void centerCamera() {
		Player p = getState().p;
		ArrayList<Collidable> clist = getState().clist;
		int gw = game.width / 2 - p.w / 2;
		int gh = game.height / 2;
		
		while(p.x > gw) {
			p.x--;
			for(Collidable c:clist)
				c.x--;
		}
		while(p.x < gw) {
			p.x++;
			for(Collidable c:clist)
				c.x++;
		}
		
		while(p.y > gh) {
			p.y--;
			for(Collidable c:clist)
				c.y--;
		}
		while(p.y < gh) {
			p.y++;
			for(Collidable c:clist)
				c.y++;
		}
	}
	
	void addEntryRooms() {
		// LEVEL A
		startPos = Util.getRandom(0, 3);
		rooms.add(new Room(startPos, Room.START));
		
		if(startPos == Room.A0) {
			rooms.add(new Room(Room.A1, Room.NORMAL));
			rooms.add(new Room(Room.A2, Room.NORMAL));
			rooms.add(new Room(Room.A3, Room.NORMAL));
		}
		if(startPos == Room.A1) {
			rooms.add(new Room(Room.A0, Room.NORMAL));
			rooms.add(new Room(Room.A2, Room.NORMAL));
			rooms.add(new Room(Room.A3, Room.NORMAL));
		}
		if(startPos == Room.A2) {
			rooms.add(new Room(Room.A1, Room.NORMAL));
			rooms.add(new Room(Room.A0, Room.NORMAL));
			rooms.add(new Room(Room.A3, Room.NORMAL));
		}
		if(startPos == Room.A3) {
			rooms.add(new Room(Room.A1, Room.NORMAL));
			rooms.add(new Room(Room.A2, Room.NORMAL));
			rooms.add(new Room(Room.A0, Room.NORMAL));
		}
	}
	
	void addMiddleRooms() {
		// LEVEL B
		rooms.add(new Room(Room.B0, Room.NORMAL));
		rooms.add(new Room(Room.B1, Room.NORMAL));
		rooms.add(new Room(Room.B2, Room.NORMAL));
		rooms.add(new Room(Room.B3, Room.NORMAL));
		
		// LEVEL C
		rooms.add(new Room(Room.C0, Room.NORMAL));
		rooms.add(new Room(Room.C1, Room.NORMAL));
		rooms.add(new Room(Room.C2, Room.NORMAL));
		rooms.add(new Room(Room.C3, Room.NORMAL));
	}
	
	void addExitRooms() {
		// LEVEL D
		exitPos = Util.getRandom(0, 3);
		rooms.add(new Room(exitPos, Room.EXIT));
		
		rooms.add(new Room(Room.D0, Room.NORMAL));
		rooms.add(new Room(Room.D1, Room.NORMAL));
		rooms.add(new Room(Room.D2, Room.NORMAL));
		rooms.add(new Room(Room.D3, Room.NORMAL));
	}
	
	@SuppressWarnings("all")
	public void parseRooms() {
		getState().clist = new ArrayList<Collidable>();
		
		int w = 10, h = 8;
		
		for(Room room:rooms) {
			char[][] ci = room.room;
			for(int i = 0; i < h; i++) {
				for(int j = 0; j < w; j++) {
					char c = ci[i][j];
					
					int rw = room.x * game.width;
					int rh = room.y * game.height;
					
					// Add Collision Tile
					if(c == '#')
						getState().clist.add(new Collidable(game, j * 50 + rw, i * 50 + rh));
					
					// Add Player
					if(c == '$')
						getState().p = new Player(game, j * 50 + rw, i * 50 + rh);
				}
			}
		}
	}
}
