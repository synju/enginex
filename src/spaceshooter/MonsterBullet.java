package spaceshooter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;

import enginex.GameObject;

@SuppressWarnings("serial")
public class MonsterBullet extends GameObject {
	Spaceshooter						game;
	
	double									x;
	double									y;
	int											width							= 10;
	int											height						= 10;
	
	public static final int	TYPE_CONVERTED		= 141411;
	public static final int	TYPE_POSSESSED		= 241412;
	public static final int	TYPE_MINION				= 341413;
	public static final int	TYPE_OVERLORD			= 441414;
	public static final int	TYPE_DEMON				= 541415;
	public static final int	TYPE_FALLEN_ANGEL	= 776;
	public static final int	TYPE_DEVIL				= 666;
	
	int											type							= TYPE_CONVERTED;
	
	Image										image;
	int											speed							= 10;
	int											damage						= 10;
	
	boolean									visible						= true;
	boolean									used							= false;
	boolean									outOfBounds				= false;
	boolean									drawBounds				= false;
	
	Player									player;
	
	MonsterBullet(Spaceshooter game, int x, int y, int type) {
		super(game);
		this.game = game;
		this.x = x - width / 2;
		this.y = y + 10;
		this.type = type;
		
		setupMonsterBullet();
		updateBounds();
	}
	
	public void setupMonsterBullet() {
		switch(type) {
			case TYPE_CONVERTED:
				speed = 6;
				image = game.res.converted_bullet.getImage();
				damage = 10;
				break;
			case TYPE_POSSESSED:
				speed = 6;
				image = game.res.possessed_bullet.getImage();
				damage = 20;
				break;
			case TYPE_MINION:
				speed = 6;
				image = game.res.minion_bullet.getImage();
				damage = 30;
				break;
			case TYPE_OVERLORD:
				speed = 8;
				image = game.res.overlord_bullet.getImage();
				damage = 40;
				break;
			case TYPE_DEMON:
				speed = 10;
				image = game.res.demon_bullet.getImage();
				damage = 50;
				break;
			case TYPE_FALLEN_ANGEL:
				speed = 10;
				image = game.res.fallen_angel_bullet.getImage();
				damage = 60;
				break;
			case TYPE_DEVIL:
				speed = 10;
				image = game.res.devil_bullet.getImage();
				damage = 70;
				break;
		}
	}
	
	public void update() {
		this.y += speed;
		
		updateBounds();
		playerCollision();
		
		if(this.y > game.height)
			outOfBounds = true;
	}
	
	protected void updateBounds() {
		bounds.setLocation(new Point((int)x, (int)y));
		bounds.setSize(new Dimension(width, height));
	}
	
	private void playerCollision() {
		player = getCurrentState().player;
		if(isColliding(getCurrentState().player.bounds)) {
			player.deductLife(damage);
			visible = false;
			used = true;
		}
	}
	
	public PlayState getCurrentState() {
		return (PlayState)game.stateMachine.getCurrentState();
	}
	
	public void render(Graphics2D g) {
		if(visible)
			g.drawImage(image, (int)x, (int)y, null);
		
		if(drawBounds) {
			g.setColor(Color.WHITE);
			g.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
		}
	}
}
