package Archive.smartrockets;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import EngineX.EngineX;
import EngineX.GameObject;
import processing.core.PApplet;

@SuppressWarnings("serial")
public class Rocket extends GameObject {
	int			maxMoves;
	int[]		dna;
	int			moveIndex	= 0;
	int			speed;
	boolean	alive			= true;
	Point		center;
	boolean	completed	= false;
	double	d;
	double	fitness;
	boolean	crashed		= false;
	
	public Rocket(EngineX game, int x, int y, int maxMoves, int speed) {
		super(game);
		init(x, y, maxMoves, speed);
	}
	
	public Rocket(EngineX game, int x, int y, int[] dna, int maxMoves, int speed) {
		super(game);
		this.dna = dna;
		init(x, y, maxMoves, speed);
	}
	
	public void init(int x, int y, int maxMoves, int speed) {
		this.speed = speed;
		this.maxMoves = maxMoves;
		this.x = x;
		this.y = y;
		w = 10;
		h = w;
		updateCenter();
		randomDNA();
	}
	
	void randomDNA() {
		if(dna == null) {
			dna = new int[maxMoves];
			for(int i = 0; i < maxMoves; i++)
				dna[i] = (int)(Math.random() * 3);
		}
	}
	
	public void update() {
		if(d > 10) {
			completed = true;
			x = getState().target.x;
			y = getState().target.y;
		}
		
		if(alive && !completed) {
			move();
			collision();
		}
	}
	
	void calcFitness() {
		float d = PApplet.dist((int)x, (int)y, getState().target.x, getState().target.y);
		fitness = PApplet.map(d, 0, game.width, game.height, 0);
		
		if(d <= 0)
			completed = true;
		
		if(completed) {
			fitness *= timeScore();
			fitness *= 10;
		}
		
		if(crashed)
			fitness /= 10;
	}
	
	double timeScore() {
		double timeScore = 1;
		
		for(int i = 1; i <= 10; i++)
			if(moveIndex < maxMoves / i)
				timeScore = i;
			
		return timeScore;
	}
	
	void collision() {
		// Window...
		if(!(center.x > 0 && center.x < game.width && center.y > 0 && center.y < game.height)) {
			alive = false;
			crashed = true;
		}
		
		// Environment
		if(center.x > getState().wall.x && center.x < (getState().wall.x + getState().wall.width) && center.y > getState().wall.y && center.y < (getState().wall.y + getState().wall.height)) {
			alive = false;
			crashed = true;
		}
		
		// Target
		if(center.x > getState().target.x && center.x < (getState().target.x + getState().target.width) && center.y > getState().target.y && center.y < (getState().target.y + getState().target.height)) {
			alive = false;
			completed = true;
		}
	}
	
	void move() {
		if(moveIndex < maxMoves) {
			// Up
			if(dna[moveIndex] == 0)
				y -= speed;
			
			// Left
			if(dna[moveIndex] == 1)
				x -= speed;
			
			// Right
			if(dna[moveIndex] == 2)
				x += speed;
			
			moveIndex++;
			updateCenter();
		}
		else {
			alive = false;
		}
	}
	
	SmartRocketState getState() {
		return (SmartRocketState)(game.stateMachine.getCurrentState());
	}
	
	void updateCenter() {
		if(center != null)
			center.setLocation((int)x + w / 2, (int)y + h / 2);
		else
			center = new Point((int)(x + w / 2), (int)(y + h / 2));
		
	}
	
	public void render(Graphics2D g) {
		g.setColor(Color.RED);
		g.fillOval((int)x, (int)y, (int)w, (int)h);
	}
}
