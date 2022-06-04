package Archive.critters;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Map {
	Game game;

	private int scrollSpeed = 5;

	int originX = 0;
	int originY = 0;

	int xNodes = 0;
	int yNodes = 0;

	private boolean upPressed = false;
	private boolean downPressed = false;
	private boolean leftPressed = false;
	private boolean rightPressed = false;

	private ArrayList<Node> nodes;
	private Node currentNode;

	public ArrayList<Node> getNodes() {
		return nodes;
	}

	Map(Game game) {
		this.game = game;
	}

	void generateMap(int xNodes, int yNodes, int size) {
		this.xNodes = xNodes;
		this.yNodes = yNodes;

		nodes = new ArrayList<>();
		for(int i = 0; i < xNodes; i++) {
			for(int j = 0; j < yNodes; j++) {
				nodes.add(new Node(i,j,i * size, j * size, size, this));
			}
		}
	}

	void update(Point m) {
		updateOriginXY();

		for(Node n:nodes)
			n.update(m);
	}

	void setCurrentNode(Node node) {
		currentNode = node;
	}

	void render(Graphics2D g) {
		try {
			for(Node node : nodes)
				node.render(g);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void updateOriginXY() {
		if(upPressed) originY += scrollSpeed;
		if(downPressed) originY -= scrollSpeed;
		if(leftPressed) originX += scrollSpeed;
		if(rightPressed) originX -= scrollSpeed;
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_W)
			upPressed = true;

		if(e.getKeyCode() == KeyEvent.VK_S)
			downPressed = true;

		if(e.getKeyCode() == KeyEvent.VK_A)
			leftPressed = true;

		if(e.getKeyCode() == KeyEvent.VK_D)
			rightPressed = true;
	}

	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_W)
			upPressed = false;

		if(e.getKeyCode() == KeyEvent.VK_S)
			downPressed = false;

		if(e.getKeyCode() == KeyEvent.VK_A)
			leftPressed = false;

		if(e.getKeyCode() == KeyEvent.VK_D)
			rightPressed = false;
	}

	public Node getCurrentNode() {
		return currentNode;
	}
}
