package enginex.pathfinding;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;

import enginex.core.EngineX;
import enginex.core.GameObject;

@SuppressWarnings("serial")
public class Beatle extends GameObject {
	ArrayList<Node>	openList	= new ArrayList<Node>();
	ArrayList<Node>	closedList	= new ArrayList<Node>();
	ArrayList<Node>	deadList	= new ArrayList<Node>();

	ArrayList<Node> pathNodes = new ArrayList<>();

	boolean	visible				= false;
	boolean	pathFindingEnabled	= false;

	float	w	= 0;
	float	h	= 0;

	Point	startXY;
	Point	endXY;

	boolean	stage1Complete	= false;
	boolean	stage2Complete	= false;
	boolean	stage3Complete	= false;
	boolean	canSearch		= false;
	boolean	canUpdate		= false;

	int i = 0;

	Node currentNode;

	public Beatle(EngineX game) {
		super(game);
	}

	public void setWH() {
		w = getState().nw / 2;
		h = getState().nh / 2;
	}

	public void update() {
		if(w == 0 && h == 0)
			setWH();

		if(pathFindingEnabled) {
						while(!stage3Complete) {
//			if(!stage3Complete) {
				if(canSearch()) {
					if(!stage1Complete)
						stage1();

					if(stage1Complete && !stage2Complete)
						stage2();

					if(stage2Complete && !stage3Complete)
						stage3();
				}
			}
		}
	}

	void stage1() {
		Node startNode = null;
		for(Node n:getNodes()) {
			n.resetScores();
			n.calculateH();
//			System.out.println(n.hScore);

			if(n.type == Node.START) {
				startNode = n;
				closedList.add(n);
			}
		}

		ArrayList<Node> sNodes = startNode.getSurroundingNodes(closedList);
		for(Node sn:sNodes) {
			sn.setParentNode(startNode);
			sn.calculateG();
			sn.calculateF();
			openList.add(sn);
		}

		stage1Complete = true;
	}

	void stage2() {
		// Get node with lowest fScore
		currentNode = openList.get(openList.size() - 1);
		for(Node n:openList)
			if(n.fScore < currentNode.fScore)
				currentNode = n;

		if(currentNode.type == Node.END) {
			stage2Complete = true;
			System.out.println("Path Found!");
		}
		else {
			// Move Node to closedList
			moveNode(currentNode, openList, closedList);

			// Get surrounding nodes
			ArrayList<Node> sNodes = currentNode.getSurroundingNodes(closedList);

			// Review surrounding nodes and adjust accordingly
			for(Node sn:sNodes) {
				if(!nodeExists(sn, closedList)) {
					double ng = currentNode.gScore;
					if(sn.getPosType(currentNode) == "+")
						ng += 1.0;
					else
						ng += 1.4;

					// If node exists in openlist
					// Check if ng (new g) is lower than og (old g)
					if(nodeExists(sn, openList)) {
						// If ng is lower than og, set ParentNode to currentNode.
						if(sn.gScore > ng) {
							sn.setParentNode(currentNode);
							sn.setG(ng);
						}
					}
					else {
						sn.setParentNode(currentNode);
						sn.setG(ng);
					}
					sn.calculateF();
				}
			}

			// Add Nodes to OpenList
			for(Node sn:sNodes)
				if(!nodeExists(sn, openList))
					openList.add(sn);

			currentNode.type = Node.SCOUT;
		}
	}

	void stage3() {
		for(Node n:getNodes())
			if(nodeExists(n, closedList))
				if(n.type != Node.START && n.type != Node.END)
					n.type = Node.SCOUT;

		Node tempNode = null;
		for(Node n:getNodes()) {
			if(n.type == Node.END) {
				pathNodes.add(n);
				tempNode = n.parentNode;
			}
		}

		while(tempNode.type != Node.START) {
			tempNode.type = Node.PATH;
			tempNode = tempNode.parentNode;
		}

		stage3Complete = true;
	}

	boolean canSearch() {
		Node a = null;
		Node b = null;

		for(Node n:getNodes()) {
			if(n.type == Node.START)
				a = n;
			if(n.type == Node.END)
				b = n;
		}

		if(a != null && b != null)
			return true;

		return false;
	}

	boolean nodeExists(Node node, ArrayList<Node> list) {
		for(Node n:list) {
			if(node.x == n.x && node.y == n.y) {
				return true;
			}
		}

		return false;
	}

	public void moveNode(Node node, ArrayList<Node> listA, ArrayList<Node> listB) {
		for(Iterator<Node> it = listA.iterator(); it.hasNext();) {
			Node n = it.next();
			if(n.x == node.x && n.y == node.y) {
				listB.add(new Node(game, n.x, n.y, n.w, n.h));

				it.remove();
				break;
			}
		}
	}

	public PathfinderState getState() {
		return (PathfinderState)game.stateMachine.getCurrentState();
	}

	public ArrayList<Node> getNodes() {
		return getState().nodes;
	}

	public boolean nodeInList(Node a, ArrayList<Node> list) {
		for(Iterator<Node> it = list.iterator(); it.hasNext();) {
			Node b = it.next();
			if(a.x == b.x && a.y == b.y) {
				return true;
			}
		}

		return false;
	}

	public void render(Graphics2D g) {
		if(visible) {
			if(startXY != null) {
				g.setColor(Color.GREEN);
				g.fillOval((int)(startXY.x - w / 2), (int)(startXY.y - h / 2), (int)w, (int)h);
			}

			if(endXY != null) {
				g.setColor(Color.BLACK);
				g.fillOval((int)(endXY.x - w / 2), (int)(endXY.y - h / 2), (int)w, (int)h);
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
//						update();
			if(!pathFindingEnabled)
				pathFindingEnabled = true;
		}
	}
}
