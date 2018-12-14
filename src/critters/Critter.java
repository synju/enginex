package critters;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Critter {
	private double x,y;
	private int w,h;
	private Map map;
	private int targetX;
	private int targetY;
	private Game game;
	private Node targetNode;
	private double moveSpeed = 0.4;
	ArrayList<PathNode> pathNodes;

	Critter(int x, int y, int w, int h, Map map, Game game) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.map = map;
		this.game = game;
	}

	private void setTargetLocation(int x, int y) {
		targetX = x;
		targetY = y;
	}

	void update() {
		move();
	}

	private void move() {
		if(targetX != x || targetY != y) {
			// horizontal movement
			if(this.x < targetX) {
				this.x += moveSpeed;
				if(this.x > targetX) {
					this.x = targetX;
				}
			}

			if(this.x > targetX) {
				this.x -= moveSpeed;
				if(this.x < targetX) {
					this.x = targetX;
				}
			}

			// vertical movement
			if(this.y < targetY) {
				this.y += moveSpeed;
				if(this.y > targetY) {
					this.y = targetY;
				}
			}

			if(this.y > targetY) {
				this.y -= moveSpeed;
				if(this.y < targetY) {
					this.y = targetY;
				}
			}
		}
	}

	void render(Graphics2D g) {
		g.setColor(Color.BLUE);
		g.fillRect((int)x+map.originX,(int)y+map.originY,w,h);
	}

	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			targetNode = map.getCurrentNode();
			setTargetLocation(targetNode.getX(), targetNode.getY());
		}
	}

	public void mouseReleased(MouseEvent e) {
		// do nothing yet
	}
}
