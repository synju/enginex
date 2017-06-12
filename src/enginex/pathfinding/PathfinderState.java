package enginex.pathfinding;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import enginex.core.EngineX;
import enginex.core.State;

public class PathfinderState extends State {
	ArrayList<Node> nodes = new ArrayList<Node>();

	int		scale	= 1;
	int		xNodes	= 10 * scale;
	int		yNodes	= 10 * scale;
	float	nw		= ((float)game.getWidth()) / xNodes;
	float	nh		= ((float)game.getHeight()) / yNodes;

	Builder	builder	= new Builder(game);
	Beatle	beatle	= new Beatle(game);

	protected PathfinderState(EngineX game) {
		super(game);

		addNodes();
		addGameObject(builder);
	}

	void addNodes() {
		for(int y = 0; y < yNodes; y++) {
			for(int x = 0; x < xNodes; x++) {
				//	nodes.add(new Node(game, x * nw, y * nh, nw, nh));
				nodes.add(new Node(game, x, y, nw, nh));
			}
		}
	}

	public void update() {
		try {
			for(Node n:nodes)
				n.update();

			builder.update();
			beatle.update();
		}
		catch(Exception e) {}
	}

	public void render(Graphics2D g) {
		try {
			for(Node n:nodes)
				n.render(g);
			beatle.render(g);
		}
		catch(Exception e) {}
	}

	void resetGame() {
		try {
			nodes = new ArrayList<>();
			addNodes();
			beatle = new Beatle(game);
		}
		catch(Exception e) {}
	}

	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_R) {
			resetGame();
		}

		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			game.exit();
		}

		beatle.keyReleased(e);
	}
}
