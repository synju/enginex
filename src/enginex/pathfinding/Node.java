package enginex.pathfinding;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import enginex.core.EngineX;
import enginex.core.GameObject;

@SuppressWarnings("serial")
public class Node extends GameObject {
	float	x;
	float	y;
	float	w;
	float	h;

	public Node parentNode;

	public double	tempG		= 0;
	public double	gScore		= 0;
	public double	hScore		= 0;
	public double	fScore		= 0;
	boolean			drawValues	= false;

	boolean	up		= false;
	boolean	down	= false;
	boolean	left	= false;
	boolean	right	= false;

	public static final int	OPEN	= 0;
	public static final int	CLOSED	= 1;
	public static final int	START	= 2;
	public static final int	END		= 3;
	public static final int	HOVER	= 4;
	public static final int	SCOUT	= 5;
	public static final int	PATH	= 6;

	public static final Color	RED		= new Color(255, 0, 0);
	public static final Color	GREEN	= new Color(0, 255, 0);
	public static final Color	BLUE	= new Color(0, 0, 255);
	public static final Color	BLACK	= new Color(0, 0, 0);
	public static final Color	OUTLINE	= new Color(15, 15, 30);
	public static final Color	GRAY	= new Color(100, 100, 100);

	public int	type		= OPEN;
	Color		outlineC	= OUTLINE;

	public Node(EngineX game, float x, float y, float w, float h) {
		super(game);

		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public void update() {
		if(up)
			y--;
		if(down)
			y++;
		if(left)
			x--;
		if(right)
			x++;
	}

	public void resetScores() {
		gScore = 0;
		hScore = 0;
		fScore = 0;
	}

	public ArrayList<Node> getSurroundingNodes(ArrayList<Node> closedList) {
		ArrayList<Node> list = new ArrayList<>();

		for(Node s:getNodes()) {
			if((s.type == Node.OPEN || s.type == Node.END || s.type == Node.SCOUT) && !nodeExists(s, closedList)) {
				// left
				if(s.x == x - 1 && s.y == y)
					list.add(s);

				// top left
				else if(s.x == x - 1 && s.y == y - 1)
					list.add(s);

				// top
				else if(s.x == x && s.y == y - 1)
					list.add(s);

				// top right
				else if(s.x == x + 1 && s.y == y - 1)
					list.add(s);

				// right
				else if(s.x == x + 1 && s.y == y)
					list.add(s);

				// bottom right
				else if(s.x == x + 1 && s.y == y + 1)
					list.add(s);

				// bottom
				else if(s.x == x && s.y == y + 1)
					list.add(s);

				// bottom left
				else if(s.x == x - 1 && s.y == y + 1)
					list.add(s);
			}
		}

		return list;
	}

	boolean nodeExists(Node node, ArrayList<Node> list) {
		for(Node n:list)
			if(node.x == n.x && node.y == n.y)
				return true;

		return false;
	}

	public PathfinderState getState() {
		return (PathfinderState)game.stateMachine.getCurrentState();
	}

	public Point getCenter() {
		return new Point((int)(x * w + w / 2), (int)(y * h + h / 2));
	}

	public void setParentNode(Node n) {
		parentNode = n;
	}

	public String getPosType(Node currentNode) {
		Node a = this;
		Node b = currentNode;

		// LEFT
		if(a.x == b.x - 1 && a.y == b.y) {
			return "+";
		}

		// TOP LEFT
		if(a.x == b.x - 1 && a.y == b.y - 1) {
			return "x";
		}

		// TOP
		if(a.x == b.x && a.y == b.y - 1) {
			return "+";
		}

		// TOP RIGHT
		if(a.x == b.x + 1 && a.y == b.y - 1) {
			return "x";
		}

		// RIGHT
		if(a.x == b.x + 1 && a.y == b.y) {
			return "+";
		}

		// BOTTOM RIGHT
		if(a.x == b.x + 1 && a.y == b.y + 1) {
			return "x";
		}

		// BOTTOM
		if(a.x == b.x && a.y == b.y + 1) {
			return "+";
		}

		// BOTTOM LEFT
		if(a.x == b.x - 1 && a.y == b.y + 1) {
			return "x";
		}

		return "+";
	}

	public void calculateG() {
		Node a = this;
		Node b = parentNode;

		// LEFT
		if(a.x < b.x && a.y == b.y)
			setG(parentNode.gScore + 1.0);

		// TOP LEFT
		if(a.x < b.x && a.y < b.y)
			setG(parentNode.gScore + 1.4);

		// TOP
		if(a.x == b.x && a.y < b.y)
			setG(parentNode.gScore + 1.0);

		// TOP RIGHT
		if(a.x > b.x && a.y < b.y)
			setG(parentNode.gScore + 1.4);

		// RIGHT
		if(a.x > b.x && a.y == b.y)
			setG(parentNode.gScore + 1.0);

		// BOTTOM RIGHT
		if(a.x > b.x && a.y > b.y)
			setG(parentNode.gScore + 1.4);

		// BOTTOM
		if(a.x == b.x && a.y > b.y)
			setG(parentNode.gScore + 1.0);

		// BOTTOM LEFT
		if(a.x < b.x && a.y > b.y)
			setG(parentNode.gScore + 1.4);
	}

	public void calculateH() {
		Node a = this;
		Node b = null;

		// Get END Node
		for(Node n:getNodes())
			if(n.type == END)
				b = n;

		// LEFT
		if(a.x < b.x && a.y == b.y) {
			setH((b.x - a.x));
		}

		// TOP LEFT
		if(a.x < b.x && a.y < b.y)
			setH((b.x - a.x) + (b.y - a.y));

		// TOP
		if(a.x == b.x && a.y < b.y)
			setH(b.y - a.y);

		// TOP RIGHT
		if(a.x > b.x && a.y < b.y)
			setH((a.x - b.x) + (b.y - a.y));

		// RIGHT
		if(a.x > b.x && a.y == b.y)
			setH(a.x - b.x);

		// BOTTOM RIGHT
		if(a.x > b.x && a.y > b.y)
			setH((a.y - b.y) + (a.x - b.x));

		// BOTTOM
		if(a.x == b.x && a.y > b.y)
			setH(a.y - b.y);

		// BOTTOM LEFT
		if(a.x < b.x && a.y > b.y)
			setH((b.x - a.x) + (a.y - b.y));
	}

	public void calculateF() {
		fScore = gScore + hScore;
	}

	public void setG(double value) {
		gScore = value;
	}

	public void setH(double v) {
		hScore = v;
	}

	public ArrayList<Node> getNodes() {
		return getState().nodes;
	}

	public boolean mouseHovering() {
		try {
			Point m = game.getMousePosition();
			if(m.x > x * w && m.x < x * w + w && m.y > y * h && m.y < y * h + h) {
				return true;
			}
		}
		catch(Exception e) {}
		return false;
	}

	public void render(Graphics2D g) {
		if(type == OPEN) {
			g.setColor(BLACK);
		}
		if(type == CLOSED) {
			g.setColor(GRAY);
		}
		if(type == START) {
			g.setColor(RED);
		}
		if(type == END) {
			g.setColor(GREEN);
		}
		if(type == HOVER) {
			g.setColor(BLUE);
		}
		if(type == SCOUT) {
			//			g.setColor(BLACK);
			g.setColor(new Color(0, 0, 100));
		}
		if(type == PATH) {
			g.setColor(new Color(0, 50, 0));
		}
		g.fillRect((int)(x * w), (int)(y * h), (int)w, (int)h);

		// OUTLINE...
		g.setColor(OUTLINE);
		g.drawRect((int)(x * w), (int)(y * h), (int)w - 1, (int)h - 1);

		Beatle b = ((PathfinderState)game.stateMachine.getCurrentState()).beatle;
		if(b.nodeExists(this, b.openList)) {
			int cv = 60;
			g.setColor(new Color(cv, cv, cv));
		}

		// Draw Values.
		if(drawValues) {
			g.setFont(new Font("Arial", Font.PLAIN, 10));
			g.setColor(Color.WHITE);
			int fs = 3;
			g.drawString("G:" + gScore, x * w, y * h + (h / 100 * 10) + fs * 0);
			g.drawString("H:" + hScore, x * w, y * h + (h / 100 * 20) + fs * 1);
			g.drawString("F:" + fScore, x * w, y * h + (h / 100 * 30) + fs * 2);
			g.drawString("X:" + x, x * w, y * h + (h / 100 * 40) + fs * 3);
			g.drawString("Y:" + y, x * w, y * h + (h / 100 * 50) + fs * 4);
		}
	}
}
