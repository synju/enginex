package Archive.critters;

public class PathNode {
	Node node;

	int x;
	int y;
	int w;
	int h;
	int xPos;
	int yPos;

	public Node parentNode;

	public double tempG = 0;
	public double gScore = 0;
	public double hScore = 0;
	public double fScore = 0;
	boolean drawValues = false;
	boolean cornersEnabled = true;

	public static final int OPEN = 0;
	public static final int CLOSED = 1;
	public static final int START = 2;
	public static final int END = 3;
	public static final int SCOUTED_AREA = 4;
	public static final int PATH = 5;

	public int type = OPEN;

	PathNode(Node n) {
		this.x = n.getX();
		this.y = n.getY();
		this.w = n.getW();
		this.h = n.getH();
		this.xPos = n.xPos;
		this.yPos = n.yPos;
	}

	public void resetScores() {
		gScore = 0;
		hScore = 0;
		fScore = 0;
	}
}
