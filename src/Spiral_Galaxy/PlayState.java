package Spiral_Galaxy;

import EngineX.GameObject;
import EngineX.State;
import com.studiohartman.jamepad.ControllerManager;
import com.studiohartman.jamepad.ControllerState;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Stack;

public class PlayState extends State {
	Game              game;
	ControllerManager controllerManager = new ControllerManager();
	boolean           initialized       = false;

	public int worldX;
	public int worldY;

	boolean up    = false;
	boolean down  = false;
	boolean left  = false;
	boolean right = false;

	ArrayList<Star> stars = new ArrayList<>();

	public PlayState(Game game) {
		super(game);
		this.game = game;

		// Controller Manager
		if(Config.controllerEnabled)
			controllerManager.initSDLGamepad();
	}

	void postInit() {
		// Return if already initialized
		if(initialized)
			return;

		// Do work
		stars = generateGalaxy();

		// Initialize
		initialized = true;
	}

	public void update() {
		postInit();

		move();
		for(Star star : stars)
			star.update();
	}

	public ArrayList<Star> generateGalaxy() {
		int    centerX        = game.width / 2;
		int    centerY        = game.height / 2;
		int    numArms        = 8;
		int    numPoints      = 1000;
		int    radius         = 10;
		double angleIncrement = 1;

		// Generate all the stars
		for(int arm = 0; arm < numArms; arm++) {
			for(int i = 0; i < numPoints; i++) {
				double angle     = angleIncrement * i;
				double armOffset = 2 * Math.PI * arm / numArms;
				double armAngle  = angle + armOffset;
				int    x         = centerX + (int)(radius * angle * Math.cos(armAngle));
				int    y         = centerY + (int)(radius * angle * Math.sin(armAngle));
				Star   star      = new Star(game, x, y);
				stars.add(star);
			}
		}

		// Connect each star to its nearest neighbor and add connections randomly
		for(Star star : stars) {
			int    connections    = 0;
			Random random         = new Random();
			int    maxRand        = 3;
			int    maxConnections = random.nextInt(maxRand) + 1; // Either 1 or 2...

			ArrayList<Star> potentialNeighbors = new ArrayList<>(stars);
			potentialNeighbors.remove(star); // Remove the star itself from potential neighbors

			while(connections < maxConnections && !potentialNeighbors.isEmpty()) {
				// Find the nearest neighbor
				double minDistance     = Double.MAX_VALUE;
				Star   nearestNeighbor = null;

				for(Star neighbor : potentialNeighbors) {
					double distance = Math.sqrt(Math.pow(star.x - neighbor.x, 2) + Math.pow(star.y - neighbor.y, 2));
					if(distance < minDistance) {
						minDistance = distance;
						nearestNeighbor = neighbor;
					}
				}

				// Flip a coin to decide whether to add the connection
				if(nearestNeighbor != null && Math.random() < 0.5) {
					star.addNeighbor(nearestNeighbor);
					connections++;
				}

				potentialNeighbors.remove(nearestNeighbor);
			}
		}

		// Remove duplicate connections from stars
		for(Star star : stars) {
			ArrayList<Star> uniqueConnections = new ArrayList<>();

			for(Star neighbor : star.neighbors) {
				if(!uniqueConnections.contains(neighbor)) {
					uniqueConnections.add(neighbor);
				}
			}

			// Update the star's list of neighbors with the unique connections
			star.neighbors = uniqueConnections;
		}

		return stars;
	}

	public void render(Graphics2D g) {
		// Draw lines between connected stars
		g.setColor(Color.yellow);
		for(Star star : stars) {
			for(Star neighbor : star.neighbors) {
				g.drawLine(star.x, star.y, neighbor.x, neighbor.y);
			}
		}

		// Draw stars
		g.setColor(Color.white);
		for(Star star : stars) {
			star.render(g);
		}
	}

	public void pollControllers() {
		int numControllers = controllerManager.getNumControllers();

		for(int i = 0; i < numControllers; i++) {
			ControllerState controller = controllerManager.getState(i);
			if(controller.isConnected && i == 0) {
				controllerUpdate(controller);
			}
		}
	}

	void move() {
		if(up)
			worldY += 5;
		if(down)
			worldY -= 5;
		if(left)
			worldX += 5;
		if(right)
			worldX -= 5;
	}

	void controllerUpdate(ControllerState controller) {
		// See HEC player for reference
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			game.exit();

		if(e.getKeyCode() == KeyEvent.VK_W)
			up = true;
		if(e.getKeyCode() == KeyEvent.VK_S)
			down = true;
		if(e.getKeyCode() == KeyEvent.VK_A)
			left = true;
		if(e.getKeyCode() == KeyEvent.VK_D)
			right = true;
	}

	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_W)
			up = false;
		if(e.getKeyCode() == KeyEvent.VK_S)
			down = false;
		if(e.getKeyCode() == KeyEvent.VK_A)
			left = false;
		if(e.getKeyCode() == KeyEvent.VK_D)
			right = false;
	}

	public void mousePressed(MouseEvent e) {}

	public void mouseReleased(MouseEvent e) {

	}

	public void mouseWheelMoved(MouseWheelEvent e) {}
}
