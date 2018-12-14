package critters;

import java.awt.*;

class Node {
	boolean hover = false;
	Map map;
	int xPos, yPos;
	private int x;
	private int y;
	private int w;
	private int h;

	public int getW() {
		return w;
	}

	public int getH() {
		return h;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	Node(int xPos, int yPos, int x, int y, int size, Map map) {
		this.map = map;
		this.xPos = xPos;
		this.yPos = yPos;
		this.x = x;
		this.y = y;
		this.w = size;
		this.h = size;
	}

	void update(Point m) {
		updateHover(m);
	}

	private void updateHover(Point m) {
		this.hover = false;

		if(m != null) {
			if((m.x > map.originX + x) && (m.x < map.originX + x + w) && (m.y > map.originY + y) && (m.y < map.originY + y + h)) {
				this.hover = true;
				map.setCurrentNode(this);
			}
		}
	}

	void render(Graphics2D g) {
		g.setColor(Color.GREEN);
		g.fillRect(x+map.originX,y+map.originY,w,h);

		g.setColor(Color.BLACK);
		if(hover)
			g.setColor(Color.WHITE);
		g.drawRect(x+map.originX,y+map.originY,w,h);
	}
}
