package driftscape;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.text.DecimalFormat;

import enginex.EngineX;
import enginex.GameObject;

@SuppressWarnings("serial")
public class Node extends GameObject {
	static double	w						= 32;
	static double	h						= 32;
	double				scale				= 1;
	boolean				hover				= false;
	PlayState			state				= null;
	boolean				live				= false;
	boolean				mouseClick	= false;
	boolean				drawCoords	= true;
	int						rotation		= 0;
	int						maxRotation	= 259;
	World					world;
	int id = 0;
	
	public Node(EngineX game, int x, int y, int id) {
		super(game);
		this.id = id;
		this.x = x;
		this.y = y;
		this.state = (PlayState)(game.stateMachine.getCurrentState());
	}
	
	public void update(Point m) {
		updateHover(m);
	}
	
	void updateHover(Point m) {
		if(m != null)
			if((m.x > getWorld().x * scale + x * w * scale) && (m.x < getWorld().x * scale + x * w * scale + w * scale) && (m.y > getWorld().y * scale + y * h * scale) && (m.y < getWorld().y * scale + y * h * scale + h * scale))
				this.hover = true;
			else
				this.hover = false;
	}
	
	public void render(Graphics2D g) {
		if(x > 0 - w * scale && x < game.width && y > 0 - h * scale && y < game.height) {
			if(!live) {
				AffineTransform at = new AffineTransform();
				at.scale(scale, scale);
				at.translate(getWorld().x + x * w, getWorld().y + y * h);
				
				at.rotate(Math.toRadians(rotation), (this.x + w / 2) * scale, (this.y + h / 2) * scale);
				
				g.drawImage(state.grassImage, at, null);
				
				if(hover)
					g.drawImage(state.rectWhite, at, null);
				
				g.drawImage(state.rectGray, at, null);
			}
			else {
				g.setColor(Color.white);
				g.fillRect((int)(x * w), (int)(y * h), 32, 32);
			}
		}
		
		if(drawCoords) {
			if(world == null)
				world = this.state.w;
			
			g.setColor(Color.white);
			
			// String xtoString = new DecimalFormat("#.#").format(x);
			String xtoString = new DecimalFormat("#.#").format((int)((x * (w * world.scale)) + (world.x * world.scale)));
			// String ytoString = new DecimalFormat("#.#").format(y);
			String ytoString = new DecimalFormat("#.#").format((int)((y * (h * world.scale)) + (world.y * world.scale)));
			
			int xL = (int)((x * (w * world.scale)) + (world.x * world.scale));
			int yL = (int)((y * (h * world.scale)) + (world.y * world.scale)); 
			// g.drawString("x:" + dfx + " y:" + dfy, (int)((x * w) * scale), (int)((y * h) * scale));
			g.drawString("x:" + xtoString + " y:" + ytoString, xL, yL + 10);
			
			g.drawString("id:" + id, xL, yL + 25);
		}
	}
	
	public World getWorld() {
		return ((PlayState)game.stateMachine.getCurrentState()).w;
	}
}
