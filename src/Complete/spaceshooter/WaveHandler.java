package Complete.spaceshooter;

import java.awt.Graphics2D;
import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;

public class WaveHandler {
	Spaceshooter							game;
	float											difficulty	= 0;
	
	ArrayList<MonsterBullet>	bullets			= new ArrayList<>();
	
	// Back Wave
	Vector2										pos1				= new Vector2(200 - 25, 50);
	Vector2										pos2				= new Vector2(300 - 25, 50);
	Vector2										pos3				= new Vector2(400 - 25, 50);
	Vector2										pos4				= new Vector2(500 - 25, 50);
	Vector2										pos5				= new Vector2(600 - 25, 50);
	
	// Middle Wave
	Vector2										pos6				= new Vector2(150 - 25, 120);
	Vector2										pos7				= new Vector2(250 - 25, 120);
	Vector2										pos8				= new Vector2(350 - 25, 120);
	Vector2										pos9				= new Vector2(450 - 25, 120);
	Vector2										pos10				= new Vector2(550 - 25, 120);
	Vector2										pos11				= new Vector2(650 - 25, 120);
	
	// Front Wave
	Vector2										pos12				= new Vector2(100 - 25, 190);
	Vector2										pos13				= new Vector2(200 - 25, 190);
	Vector2										pos14				= new Vector2(300 - 25, 190);
	Vector2										pos15				= new Vector2(400 - 25, 190);
	Vector2										pos16				= new Vector2(500 - 25, 190);
	Vector2										pos17				= new Vector2(600 - 25, 190);
	Vector2										pos18				= new Vector2(700 - 25, 190);
	
	public ArrayList<Monster>	monsters		= new ArrayList<>();
	
	public WaveHandler(Spaceshooter game) {
		this.game = game;
		generateWave();
	}
	
	public void update() {
		for(Monster monster:monsters)
			monster.update();
		
		for(MonsterBullet mb:bullets)
			mb.update();
		
		removeBullets();
		removeDeadMonsters();
		checkComplete();
	}
	
	private void removeBullets() {
		for(int i = 0; i < bullets.size(); i++) {
			MonsterBullet b = bullets.get(i);
			if(b.outOfBounds || b.used)
				bullets.remove(i);
		}
	}
	
	public void removeDeadMonsters() {
		for(int i = 0; i < monsters.size(); i++) {
			Monster m = monsters.get(i);
			if(!m.alive) {
				monsters.remove(i);
			}
		}
	}
	
	public void removeAllBullets() {
		for(int i = bullets.size() - 1; i >= 0; i--)
			bullets.remove(i);
	}
	
	public void render(Graphics2D g) {
		for(MonsterBullet mb:bullets)
			mb.render(g);
		
		for(Monster monster:monsters)
			monster.render(g);
	}
	
	public void generateWave() {
		monsters = new ArrayList<>();
		removeAllBullets();
		
		// Back Wave
		monsters.add(Monster.createMonster(game, pos1, Monster.TYPE_MINION));
		monsters.add(Monster.createMonster(game, pos2, Monster.TYPE_MINION));
		monsters.add(Monster.createMonster(game, pos3, Monster.TYPE_DEVIL));
		monsters.add(Monster.createMonster(game, pos4, Monster.TYPE_MINION));
		monsters.add(Monster.createMonster(game, pos5, Monster.TYPE_MINION));
		
		// Middle Wave
		monsters.add(Monster.createMonster(game, pos6, Monster.TYPE_POSSESSED));
		monsters.add(Monster.createMonster(game, pos7, Monster.TYPE_POSSESSED));
		monsters.add(Monster.createMonster(game, pos8, Monster.TYPE_POSSESSED));
		monsters.add(Monster.createMonster(game, pos9, Monster.TYPE_POSSESSED));
		monsters.add(Monster.createMonster(game, pos10, Monster.TYPE_POSSESSED));
		monsters.add(Monster.createMonster(game, pos11, Monster.TYPE_POSSESSED));
		
		// Front Wave
		monsters.add(Monster.createMonster(game, pos12, Monster.TYPE_CONVERTED));
		monsters.add(Monster.createMonster(game, pos13, Monster.TYPE_CONVERTED));
		monsters.add(Monster.createMonster(game, pos14, Monster.TYPE_CONVERTED));
		monsters.add(Monster.createMonster(game, pos15, Monster.TYPE_CONVERTED));
		monsters.add(Monster.createMonster(game, pos16, Monster.TYPE_CONVERTED));
		monsters.add(Monster.createMonster(game, pos17, Monster.TYPE_CONVERTED));
		monsters.add(Monster.createMonster(game, pos18, Monster.TYPE_CONVERTED));
	}
	
	public void checkComplete() {
		// Check player alive
		if(((PlayState)(game.stateMachine.getCurrentState())).player.alive == false) {
			getCurrentState().gameOver = true;
			getCurrentState().musicEnabled = false;
			getCurrentState().stopMusic();
			game.res.gameOverSound.getSound().playSound();
		}
		
		// Check all monsters dead
		boolean monstersAlive = false;
		for(Monster monster:monsters)
			if(monster.alive)
				monstersAlive = true;
			
		// New Wave
		if(!monstersAlive) {
			getCurrentState().levelComplete = true;
			getCurrentState().musicEnabled = false;
			getCurrentState().stopMusic();
			game.res.levelCompleteSound.getSound().playSound();
		}
	}
	
	public void increaseLevel() {
		increaseDifficulty();
		getCurrentState().player.resetPosition();
		generateWave();
		getCurrentState().levelComplete = false;
	}
	
	public void increaseDifficulty() {
		difficulty += 0.3;
	}
	
	public PlayState getCurrentState() {
		return (PlayState)game.stateMachine.getCurrentState();
	}
}
