package Archive.smartrockets;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import EngineX.EngineX;
import EngineX.State;

public class SmartRocketState extends State {
	public Rectangle start, wall, target;
	ArrayList<Rocket> rockets;
	ArrayList<Rocket> matingPool          = new ArrayList<Rocket>();
	int               population          = 10000;
	int               maxMoves            = 500;
	int               generation          = 0;
	int               maxMatingPopulation = 100;
	double            mutation            = 0.01;
	int               speed               = 10;
	boolean           normal              = true;
	int               normalCount         = 0;
	int               normalMax           = 20;
	boolean           rocketsInitialized  = false;

	// Initialization (Create Game --> Initialize Environment --> Initialize Rockets)
	protected SmartRocketState(EngineX game) {
		super(game);
		initEnvironment();
		initRockets();
	}

	void initEnvironment() {
		// Start
		start = new Rectangle(new Dimension(10, 10));
		start.setLocation(game.width / 2 - start.width / 2, game.height - start.height);

		// Wall
		wall = new Rectangle(new Dimension(500, 20));
		wall.setLocation(game.width / 2 - wall.width / 2, (int)(game.height / 1.5 - wall.height / 2));

		// Target
		target = new Rectangle(new Dimension(10, 10));
		target.setLocation(game.width / 2 - target.width / 2, 0);
	}

	void initRockets() {
		// Print Generation Count
		System.out.println("Generation: " + generation);

		rockets = new ArrayList<Rocket>();
		for(int i = 0; i < population; i++)
			rockets.add(new Rocket(game, start.x, start.y, maxMoves, speed));

		rocketsInitialized = true;
	}

	// Update Everything
	public void update() {
		if(rocketsInitialized) {
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
	}

	void updateRockets() {
		for(Rocket r : rockets)
			r.update();

		// Alive...
		boolean alive = false;
		for(Rocket r : rockets)
			if(r.alive == true)
				alive = true;

		if(!alive) {
			evaluate();
			normalCount++;
		}
	}

	void evaluate() {
		// Calculate Fitness and Normalize
		calculateFitnessAndNormalize();

		// MatingPool
		//		matingPool();
		matingPool2();

		// Selection
		selection();

		// Update Generation
		generation++;

		// Print Generation Count
		System.out.println("Generation: " + generation);
	}

	void calculateFitnessAndNormalize() {
		// Calculate Fitness
		double maxFit = 0;
		for(int i = 0; i < population; i++) {
			Rocket r = rockets.get(i);
			r.calcFitness();
			if(r.fitness > maxFit)
				maxFit = r.fitness;
		}

		// Normalize
		for(Rocket r : rockets) {
			r.fitness /= maxFit;
			System.out.println(r.fitness);
		}
	}

	void matingPool() {
		matingPool = new ArrayList<Rocket>();
		for(int i = 0; i < rockets.size(); i++) {
			double n = rockets.get(i).fitness * 100;
			for(int j = 0; j < n; j++)
				matingPool.add(rockets.get(i));
		}

		this.game.exit(String.valueOf(matingPool.size()));
	}

	void matingPool2() {
		matingPool = new ArrayList<Rocket>();

		// Only add 100 fittest
		for(int i = 0; i < rockets.size(); i++) {
			Rocket r = rockets.get(i);

			double n = r.fitness * 100;

			// Decision:
			// if less than 100, add...
			// if greater than 100, replace...
			if(matingPool.size() < maxMatingPopulation) {
				matingPool.add(r);
			}
			else {
				for(int j = 0; j < matingPool.size(); j++) {
					if(matingPool.get(j).fitness > n) {
						matingPool.set(j, r);
					}
				}
			}
		}

		// Check top 100 fitness
		//		for(Rocket t:matingPool) {
		//			System.out.println(t.fitness);
		//		}
	}

	void selection() {
		ArrayList<Rocket> newRockets = new ArrayList<Rocket>();
		for(int i = 0; i < rockets.size(); i++) {
			int a = 0, b = 0;
			while(a == b) {
				a = (int)(Math.random() * matingPool.size());
				b = (int)(Math.random() * matingPool.size());
			}

			int[] parentAdna = matingPool.get(a).dna;
			int[] parentBdna = matingPool.get(b).dna;
			int[] childdna   = crossover(parentAdna, parentBdna);
			childdna = mutation(childdna);
			newRockets.add(new Rocket(game, start.x, start.y, maxMoves, speed, childdna));
		}

		rockets = newRockets;
	}

	public int[] crossover(int[] father, int[] mother) {
		int[] newdna = new int[maxMoves];
		int   mid    = floor(random(maxMoves));
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
				dna[i] = (int)(Math.random() * 3);

		return dna;
	}

	public void render(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fillRect(target.x, target.y, target.width, target.height);
		g.fillRect(wall.x, wall.y, wall.width, wall.height);
		g.fillRect(start.x, start.y, start.width, start.height);

		if(rocketsInitialized)
			for(Rocket r : rockets)
				r.render(g);
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			game.exit();

		if(e.getKeyCode() == KeyEvent.VK_R)
			for(Rocket r : rockets)
				r.alive = false;
	}
}