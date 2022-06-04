package Complete.spaceshooter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;

import com.badlogic.gdx.math.Vector2;

import EngineX.GameObject;

@SuppressWarnings("serial")
public class Monster extends GameObject {
	Spaceshooter							game;
	
	double										x;
	double										y;
	int												width										= 50;
	int												height									= 50;
	
	int												posMin									= 0;
	int												posMax									= 50;
	int												pos											= posMax / 2;
	
	float											currentLife							= 100f;
	int												drawLife								= 100;
	
	boolean										drawBounds							= false;
	
	
	// TYPES
	public static final int		TYPE_CONVERTED					= 141411;
	public static final int		TYPE_POSSESSED					= 241412;
	public static final int		TYPE_MINION							= 341413;
	public static final int		TYPE_OVERLORD						= 441414;
	public static final int		TYPE_DEMON							= 541415;
	public static final int		TYPE_FALLEN_ANGEL				= 776;
	public static final int		TYPE_DEVIL							= 666;
	
	public static final float	TYPE_CONVERTED_LIFE			= 100f;
	public static final float	TYPE_POSSESSED_LIFE			= 150f;
	public static final float	TYPE_MINION_LIFE				= 250f;
	public static final float	TYPE_OVERLORD_LIFE			= 300f;
	public static final float	TYPE_DEMON_LIFE					= 400f;
	public static final float	TYPE_FALLEN_ANGEL_LIFE	= 750f;
	public static final float	TYPE_DEVIL_LIFE					= 1000f;
	
	int												type										= TYPE_MINION;
	
	boolean										movingRight							= true;
	
	boolean										alive										= true;
	
	int												shootTimer							= 0;
	int												shootTimerMax						= 100;
	
	Image											image;
	
	public static Monster createMonster(Spaceshooter game, Vector2 pos, int type) {
		return new Monster(game, pos, type);
	}
	
	private Monster(Spaceshooter game, Vector2 pos, int type) {
		super(game);
		
		this.game = game;
		this.x = pos.x;
		this.y = pos.y;
		this.type = type;
		
		setupMonster();
		updateBounds();
	}
	
	private void setupMonster() {
		if(type == TYPE_CONVERTED) {
			currentLife = TYPE_CONVERTED_LIFE;
			shootTimerMax = 100;
			image = game.res.converted.getImage();
		}
		if(type == TYPE_POSSESSED) {
			currentLife = TYPE_POSSESSED_LIFE;
			shootTimerMax = 125;
			image = game.res.possessed.getImage();
		}
		if(type == TYPE_MINION) {
			currentLife = TYPE_MINION_LIFE;
			shootTimerMax = 150;
			image = game.res.minion.getImage();
		}
		if(type == TYPE_OVERLORD) {
			currentLife = TYPE_OVERLORD_LIFE;
			shootTimerMax = 175;
			image = game.res.overlord.getImage();
		}
		if(type == TYPE_DEMON) {
			currentLife = TYPE_DEMON_LIFE;
			shootTimerMax = 200;
			image = game.res.demon.getImage();
		}
		if(type == TYPE_FALLEN_ANGEL) {
			currentLife = TYPE_FALLEN_ANGEL_LIFE;
			shootTimerMax = 225;
			image = game.res.fallen_angel.getImage();
		}
		if(type == TYPE_DEVIL) {
			currentLife = TYPE_DEVIL_LIFE;
			shootTimerMax = 250;
			image = game.res.devil.getImage();
		}
		
		shootTimer = (int)(Math.random() * shootTimerMax);
	}
	
	protected void updateBounds() {
		bounds.setLocation(new Point((int)x, (int)y + 15));
		bounds.setSize(new Dimension(width, height - 25));
	}
	
	public void update() {
		move();
		updateBounds();
		shoot();
	}
	
	private void shoot() {
		if(shootTimer <= 0) {
			int random = (int)(Math.random() * 3);
			if(random == 1) {
				getCurrentState().waveHandler.bullets.add(new MonsterBullet(game, (int)x + 25, (int)y, type));
				shootTimer = shootTimerMax;
			}
		}
		
		shootTimer--;
	}
	
	private void move() {
		if(movingRight) {
			if(pos < posMax) {
				this.x++;
				pos++;
			}
			
			if(pos == posMax)
				movingRight = false;
		}
		else {
			if(pos > posMin) {
				this.x--;
				pos--;
			}
			
			if(pos == posMin)
				movingRight = true;
		}
	}
	
	public void render(Graphics2D g) {
		g.drawImage(image, (int)x, (int)y, null);
		
		if(drawBounds) {
			g.setColor(Color.WHITE);
			g.drawRect((int)bounds.x, (int)bounds.y, bounds.width, bounds.height);
		}
		
		// Life
		drawLife(g);
	}
	
	public PlayState getCurrentState() {
		return (PlayState)game.stateMachine.getCurrentState();
	}
	
	public void deductLife(int damage) {
		currentLife -= damage;
		
		if(currentLife <= 0) {
			alive = false;
			game.res.monsterExplosion.getSound().playSound();
			
			if(type == Monster.TYPE_CONVERTED)
				getCurrentState().player.currentScore += 1;
			if(type == Monster.TYPE_POSSESSED)
				getCurrentState().player.currentScore += 2;
			if(type == Monster.TYPE_MINION)
				getCurrentState().player.currentScore += 3;
			if(type == Monster.TYPE_OVERLORD)
				getCurrentState().player.currentScore += 4;
			if(type == Monster.TYPE_DEMON)
				getCurrentState().player.currentScore += 5;
			if(type == Monster.TYPE_FALLEN_ANGEL)
				getCurrentState().player.currentScore += 6;
			if(type == Monster.TYPE_DEVIL)
				getCurrentState().player.currentScore += 7;
		}
		else {
			game.res.monsterHurt.getSound().playSound();
		}
	}
	
	private void drawLife(Graphics2D g) {
		// Draw Life BG
		g.setColor(Color.BLACK);
		g.fillRect((int)x, (int)y, width, 4);
		
		// Draw Life Bar
		g.setColor(Color.YELLOW);
		float lifeMaxWidth = (width - 2);
		
		if(type == TYPE_CONVERTED)
			drawLife = (int)((100 / TYPE_CONVERTED_LIFE) * currentLife);
		if(type == TYPE_POSSESSED)
			drawLife = (int)((100 / TYPE_POSSESSED_LIFE) * currentLife);
		if(type == TYPE_MINION)
			drawLife = (int)((100 / TYPE_MINION_LIFE) * currentLife);
		if(type == TYPE_OVERLORD)
			drawLife = (int)((100 / TYPE_OVERLORD_LIFE) * currentLife);
		if(type == TYPE_DEMON)
			drawLife = (int)((100 / TYPE_DEMON_LIFE) * currentLife);
		if(type == TYPE_FALLEN_ANGEL)
			drawLife = (int)((100 / TYPE_FALLEN_ANGEL_LIFE) * currentLife);
		if(type == TYPE_DEVIL)
			drawLife = (int)((100 / TYPE_DEVIL_LIFE) * currentLife);
		
		float remainingLife = (lifeMaxWidth / 100) * drawLife;
		g.fillRect((int)x + 1, (int)y + 1, (int)(remainingLife), 2);
	}
}
