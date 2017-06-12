package enginex.smartrockets;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import enginex.core.EngineX;
import enginex.core.State;

public class SmartRocketState extends State {
	public Rectangle	start, wall, target;
	ArrayList<Rocket>	rockets;
	ArrayList<Rocket> matingPool = new ArrayList<Rocket>();

	int	generation	= 0;
	
	int	population	= 10000;
	int	maxMoves		= 500;
	double mutation = 0.01;
	
	int speed 			= 10;
	
	boolean normal 	= true;
	int normalCount = 0;
	int normalMax 	= 20;

	protected SmartRocketState(EngineX game) {
		super(game);
		initEnvironment();
		initRockets();
	}

	void initEnvironment() {
		start = new Rectangle(new Dimension(10, 10));
		start.setLocation(game.width / 2 - start.width / 2, game.height - start.height);

		wall = new Rectangle(new Dimension(500, 20));
		wall.setLocation(game.width / 2 - wall.width / 2, (int)(game.height / 1.5 - wall.height / 2));

		target = new Rectangle(new Dimension(10, 10));
		target.setLocation(game.width / 2 - target.width / 2, 0);
	}
	
	void initRockets() {
		printGeneration();
		rockets = new ArrayList<Rocket>();
		for(int i = 0; i < population; i++)
			rockets.add(new Rocket(game, start.x, start.y, maxMoves,speed));
	}

	protected void update() {
		if(normal) {
			updateRockets();
		}
		else {
			while(!normal) {
				updateRockets();
				
				if(normalCount == normalMax)
					normal = true;
			}
		}
	}

	void updateRockets() {
		for(Rocket r:rockets)
			r.update();
		
		// Alive...
		boolean alive = false;
		for(Rocket r:rockets)
			if(r.alive == true)
				alive = true;
		
		if(!alive){
			evaluate();
			normalCount++;
		}
	}

	void evaluate() {
		// Calculate Fitness and Normalize
		calculateFitnessAndNormalize();
		
		// MatingPool
		matingPool();
		
		// Selection
		selection();
		
		// Update Generation
		generation++;
		printGeneration();
	}
	
	void printGeneration() {
		System.out.println("Generation: " + generation);
	}
	
	void calculateFitnessAndNormalize() {
		// Calculate Fitness
		double maxFit = 0;
		for(int i = 0; i < population; i++) {
			rockets.get(i).calcFitness();
			if(rockets.get(i).fitness > maxFit)
				maxFit = rockets.get(i).fitness;
		}
		
		// Normalize
		for(Rocket r:rockets)
			r.fitness /= maxFit;
	}
	
	void matingPool() {
		matingPool = new ArrayList<Rocket>();
		for(int i = 0; i < rockets.size(); i++) {
			double n = rockets.get(i).fitness * 100;
			for(int j = 0; j < n; j++)
				matingPool.add(rockets.get(i));
		}
	}
	
	void selection() {
		ArrayList<Rocket> newRockets = new ArrayList<Rocket>();
		for(int i = 0; i < rockets.size(); i++) {
			int[] parentAdna = matingPool.get((int)(Math.random()*rockets.size())).dna;
			int[] parentBdna = matingPool.get((int)(Math.random()*rockets.size())).dna;
			int[] childdna = crossover(parentAdna,parentBdna);
			childdna = mutation(childdna);
			newRockets.add(new Rocket(game,start.x,start.y,childdna,maxMoves,speed));
		}
		
		rockets = newRockets;
	}
	
	public int[] crossover(int[] father, int[] mother) {
		int[] newdna = new int[maxMoves];
		int mid = floor(random(maxMoves));
		for(int i = 0; i < maxMoves; i++)
			if(i > mid)
				newdna[i] = father[i];
			else
				newdna[i] = mother[i];
		
		return newdna;
	}
	
	int[] mutation(int[] dna) {
		for(int i = 0; i < maxMoves; i++)
			if(random(1) < mutation)
				dna[i] = (int)(Math.random()*3);
		
		return dna;
	}

	protected void render(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fillRect(target.x, target.y, target.width, target.height);
		g.fillRect(wall.x, wall.y, wall.width, wall.height);
		g.fillRect(start.x, start.y, start.width, start.height);

		for(Rocket r:rockets)
			r.render(g);
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			game.exit();
		
		if(e.getKeyCode() == KeyEvent.VK_R)
			for(Rocket r:rockets)
				r.alive = false;
	}
}