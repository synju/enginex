package spaceshooter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import enginex.GameObject;

@SuppressWarnings("serial")
public class PlayerBullet extends GameObject {
	Spaceshooter				game;
	
	double							x;
	double							y;
	int									width				= 10;
	int									height			= 10;
	
	ArrayList<Monster>	monsters		= new ArrayList<>();
	
	float								speed				= 7f;
	int		damage	= 10;
	
	boolean							visible			= true;
	boolean							used				= false;
	boolean							outOfBounds	= false;
	boolean							drawBounds	= false;
	
	public PlayerBullet(Spaceshooter game, int x, int y) {
		super(game);
		this.game = game;
		this.x = x - width / 2;
		this.y = y;
		updateBounds();
	}
	
	public void update() {
		this.y -= speed;
		if(!outOfBounds)
			if(this.y < 0 - height)
				outOfBounds = true;
			
		updateBounds();
		monsterCollision();
	}
	
	void monsterCollision() {
		// List of Monsters
		monsters = getCurrentState().waveHandler.monsters;
		
		for(Monster m:monsters) {
			if(isColliding(m.bounds)) {
				m.deductLife(damage);
				visible = false;
				used = true;
				break;
			}
		}
		
	}
	
	public PlayState getCurrentState() {
		return (PlayState)game.stateMachine.getCurrentState();
	}
	
	protected void updateBounds() {
		bounds.setLocation(new Point((int)x, (int)y));
		bounds.setSize(new Dimension(width, height));
	}
	
	public void render(Graphics2D g) {
		if(visible)
			g.drawImage(game.res.playerBullet.getImage(), (int)x, (int)y, null);
		
		if(drawBounds) {
			g.setColor(Color.WHITE);
			g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
		}
	}
}
