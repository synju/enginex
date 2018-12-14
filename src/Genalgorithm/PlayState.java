package Genalgorithm;

import java.awt.Graphics2D;

import enginex.EngineX;
import enginex.State;

public class PlayState extends State {
	Rocket rocket;
	
	protected PlayState(EngineX game) {
		super(game);
		
		this.rocket = new Rocket(this.game, 50,50);
	}
	
	public void update() {
		
	}
	
	public void render(Graphics2D g) {
		rocket.render(g);
	}
}
