package flowershop;

import java.util.ArrayList;

import enginex.FileManager;

public class Profile {
	Game							game;
	String						name			= "";
	String						quantity	= "4120";
	String						grain			= "10";
	String						data;
	FileManager				fm;
	ProfileData				pd;
	ArrayList<Flower>	flowers		= new ArrayList<>();
	
	Profile(Game game, ProfileData pd) {
		this.game = game;
		this.pd = pd;
		this.name = pd.name;
	}
	
	Profile(Game game, String name) {
		this.game = game;
		this.name = name;
	}
	
	public ProfileData getProfileData() {
		pd = new ProfileData(name, 5, flowers);
		return pd;
	}
}