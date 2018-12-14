package pathfinding;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import enginex.EngineX;
import enginex.State;

public class PathfinderState extends State {
	ArrayList<Node>	nodes;
	ArrayList<Node>	pathNodes	= new ArrayList<>();

	int		scale		= 2;
	int		xNodes	= 50;
	int		yNodes	= 50;
	float	nw			= ((float)game.getWidth()) / xNodes;
	float	nh			= ((float)game.getHeight()) / yNodes;

	Builder			builder			= new Builder(game);
	PathFinder	pathfinder	= new PathFinder(game);

	protected PathfinderState(EngineX game) {
		super(game);

		addNodes();
		addGameObject(builder);
	}

	void addNodes() {
		nodes = new ArrayList<>();

		for(int y = 0; y < yNodes; y++)
			for(int x = 0; x < xNodes; x++)
				nodes.add(new Node(game, x, y, nw, nh));
	}

	public void update() {
		try {
			builder.update();
			pathfinder.update();
			
//			pathfinder.applyShortestPath(pathNodes, nodes);
		}
		catch(Exception e) {
			// Do Nothing...
		}
	}

	public void render(Graphics2D g) {
		try {
			for(Node n:nodes)
				n.render(g);

			pathfinder.render(g);
		}
		catch(Exception e) {}
	}

	void resetGame() {
		try {
			addNodes();
			pathfinder = new PathFinder(game);
		}
		catch(Exception e) {}
	}

	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_R)
			resetGame();

		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			game.exit();

		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			new Thread(() -> {
				pathfinder.applyShortestPath(pathNodes, nodes);
			}).start();
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		super.mouseReleased(e);
		pathfinder.mouseReleased(e);
	}
	
	public void mousePressed(MouseEvent e) {
		super.mousePressed(e);
		pathfinder.mousePressed(e);
	}
}
