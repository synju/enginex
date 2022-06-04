package Archive.Genalgorithm;

import java.awt.Color;
import java.awt.Graphics2D;

import EngineX.EngineX;
import EngineX.GameObject;

@SuppressWarnings("serial")
public class Rocket extends GameObject {
	float w = 10;
	float h = 10;
	
	public Rocket(EngineX game, int x, int y) {
		super(game);
	}
	
	public void update() {
		
	}
	
	public void render(Graphics2D g) {
		g.setColor(Color.RED);
		g.fillRect((int)(game.width/2 - w/2), (int)(game.height - 44), 5, 15);
	}
}
