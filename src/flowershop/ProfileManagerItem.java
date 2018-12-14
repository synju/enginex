package flowershop;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import enginex.GameObject;
import enginex.Util;

@SuppressWarnings("serial")
public class ProfileManagerItem extends GameObject {
	ProfileManager	pm;
	Game						game;
	String					name;
	Point						p;
	boolean					hover				= false;
	Color						hoverColor	= Color.RED;
	Color						normalColor	= Color.DARK_GRAY;
	Profile					profile;
	
	public ProfileManagerItem(Game game, int x, int y, int w, int h, String name, ProfileManager pm, Profile profile) {
		super(game);
		this.pm = pm;
		this.game = game;
		this.x = x;
		this.y = y;
		this.h = h;
		this.w = w;
		this.name = name;
	}
	
	public void update() {
		try {
			hover = contains(game.getMousePosition());
		}
		catch(Exception e) {}
	}
	
	public void render(Graphics2D g) {
		// BG Color
		if(hover)
			g.setColor(hoverColor);
		else
			g.setColor(normalColor);
		
		// Render BG
		g.fillRect((int)x, (int)y, (int)w, (int)h);
		
		// Render Title
		g.setColor(Color.white);
		Util.drawText(name, (int)x + 10, (int)y + 37, 30, g);
	}
}
