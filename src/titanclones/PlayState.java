package titanclones;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import enginex.Resource;
import enginex.State;

public class PlayState extends State {
	TitanClones	game;
	boolean		initialized	= false;

	Player						playerA;
	Player						playerB;
	public ArrayList<Player>	players	= new ArrayList<>();

	AffineTransform	at	= new AffineTransform();
	Image			bg	= new ImageIcon("res/titanclones/stage.png").getImage();

	Resource songResource = new Resource("res/titanclones/music.ogg", Resource.SOUND);

	ArrayList<Collidable>	clist	= new ArrayList<>();
	public char[][]			room;

	// Stage Origin
	int	ox	= 0;
	int	oy	= 0;

	boolean musicPlaying = true;

	public PlayState(TitanClones game) {
		super(game);
		this.game = game;
	}

	public void postInit() {
		if(initialized)
			return;

		if(musicPlaying)
			songResource.getSound().playSong();

		generateStage();

		playerA = new Player(game, 100, (288 * (int)game.scale) - (32 * (int)game.scale) * 2, Player.A);
		players.add(playerA);

		playerB = new Player(game, game.width - 100, (288 * (int)game.scale) - (32 * (int)game.scale) * 2, Player.B);
		players.add(playerB);

		initialized = true;
	}

	public void toggleMusic() {
		if(musicPlaying) {
			musicPlaying = false;
			songResource.getSound().stop();
		}
		else {
			musicPlaying = true;
			songResource.getSound().playSong();
		}
	}

	public void generateStage() {
		room = new char[][] {{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {' ', ' ', ' ', ' ', '#', ' ', ' ', ' ', ' '}, {'#', ' ', ' ', '#', '#', ' ', ' ', ' ', '#'}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {' ', '#', ' ', ' ', ' ', ' ', ' ', '#', ' '}, {'#', '#', '#', '#', ' ', ' ', '#', '#', '#'}, {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '}, {' ', ' ', ' ', ' ', '#', ' ', ' ', ' ', ' '}, {'#', '#', '#', '#', '#', ' ', '#', '#', '#'}};

		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				char c = room[i][j];

				if(c == '#')
					clist.add(new Collidable(game, ox + j * 32 * (int)game.scale, oy + i * 32 * (int)game.scale, 32 * (int)game.scale, 32 * (int)game.scale));
			}
		}
	}

	public void update() {
		postInit();
		for(Player p:players)
			p.update();
	}

	public void render(Graphics2D g) {
		//		AffineTransform at = new AffineTransform();
		//		at.scale(game.scale, game.scale);
		//		at.translate(0, 0);
		//		g.drawImage(bg, at, null);
		//		g.drawImage(bg, 0, 0, null);

		for(Collidable c:clist)
			c.render(g);

		for(Player p:players)
			p.render(g);
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			game.stateMachine.setState(game.MENU);
			songResource.getSound().stop();
		}

		if(e.getKeyCode() == KeyEvent.VK_M) {
			toggleMusic();
		}

		for(Player p:players)
			p.keyPressed(e);

		if(e.getKeyCode() == KeyEvent.VK_R)
			playerA.reset();
	}

	public void keyReleased(KeyEvent e) {
		for(Player p:players)
			p.keyReleased(e);
	}
}
