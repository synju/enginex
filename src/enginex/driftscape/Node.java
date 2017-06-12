package enginex.driftscape;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.text.DecimalFormat;

import enginex.core.EngineX;
import enginex.core.GameObject;

@SuppressWarnings("serial")
public class Node extends GameObject {
	static double	w						= 32;
	static double	h						= 32;
	double				scale				= 1;
	boolean				hover				= false;
	PlayState			state				= null;
	boolean				live				= false;
	boolean				mouseClick	= false;
	boolean				drawCoords	= false;
	int						rotation		= 0;
	int						maxRotation	= 259;
	
	public Node(EngineX game, int x, int y) {
		super(game);
		this.x = x;
		this.y = y;
		this.state = (PlayState)(game.stateMachine.getCurrentState());
	}
	
	public void update(Point m) {
		updateHover(m);
	}
	
	void updateHover(Point m) {
		if(m != null)
			if((m.x > getWorld().x * scale + x * w * scale) && (m.x < getWorld().x * scale + x * w * scale + w * scale)
					&& (m.y > getWorld().y * scale + y * h * scale) && (m.y < getWorld().y * scale + y * h * scale + h * scale))
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
				
				at.rotate(Math.toRadians(rotation),(this.x + w / 2) * scale, (this.y + h / 2) * scale);
				
				g.drawImage(state.grassImage, at, null);
				
				if(hover)
					g.drawImage(state.rectWhite, at, null);
				
				g.drawImage(state.rectGray, at, null);
				
				System.out.println(rotation);
				
			}
			else {
				g.setColor(Color.white);
				g.fillRect((int)(x * w), (int)(y * h), 32, 32);
			}
		}
		
		if(drawCoords) {
			g.setColor(Color.white);
			String dfx = new DecimalFormat("#.#").format(x);
			String dfy = new DecimalFormat("#.#").format(y);
			g.drawString("x:" + dfx + " y:" + dfy, (int)((x * w) * scale), (int)((y * h) * scale));
		}
	}
	
	public World getWorld() {
		return ((PlayState)game.stateMachine.getCurrentState()).w;
	}
}
