package mapMaker;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import enginex.GameObject;

@SuppressWarnings("serial")
public class Block extends GameObject {
	MapMaker game;
	boolean hover = false;
	Point m;
	public int xl;
	public int yl;
	int layer;

	public Block(MapMaker game, int x, int y, int xl, int yl, int w, int h, int layer) {
		super(game);
		this.game = game;
		
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.xl = xl;
		this.yl = yl;
		this.layer = layer;
	}
	
	public void update() {
		if(((getState().ox+this.x*game.scale) > 0 - this.w*game.scale) && ((getState().ox + this.x*game.scale + this.w*game.scale) < game.width + this.w*game.scale) && ((getState().oy+this.y*game.scale) > 0 - this.h*game.scale) && ((getState().oy + this.y*game.scale + this.h*game.scale) < game.height + this.h*game.scale)) {
			try {
				hover = contains(game.mousePosition);
			}
			catch(Exception e) {
				hover = false;
			}
		}
		
//		if(hover)
//			getState().currentBlock = this;
	}
	
	public void render(Graphics2D g) {
		if(((getState().ox+this.x*game.scale) > 0 - this.w*game.scale) && ((getState().ox + this.x*game.scale + this.w*game.scale) < game.width + this.w*game.scale) && ((getState().oy+this.y*game.scale) > 0 - this.h*game.scale) && ((getState().oy + this.y*game.scale + this.h*game.scale) < game.height + this.h*game.scale)) {
			g.setColor(new Color(0f,0f,1f,0.05f));
			g.fillRect((int)(getState().ox+(x*game.scale)), (int)(getState().oy+(y*game.scale)), (int)(w*game.scale), (int)(h*game.scale));
			
			g.setColor(new Color(0.7f,0.7f,1f,0.2f));
			g.drawRect((int)(getState().ox+(x*game.scale)), (int)(getState().oy+(y*game.scale)), (int)(w*game.scale), (int)(h*game.scale));
		}
	}
	
	public PlayState getState() {
		return (PlayState)game.stateMachine.getCurrentState();
	}
	
	public boolean contains(Point mousePosition) {
		Point m = mousePosition;
		
		if((m.x > getState().ox+this.x*game.scale) && (m.x < getState().ox + this.x*game.scale + this.w*game.scale) && (m.y > getState().oy+this.y*game.scale) && (m.y < getState().oy + this.y*game.scale + this.h*game.scale))
			return true;
		
		return false;
	}
	
	public int getLayer() {
		return layer;
	}
}
