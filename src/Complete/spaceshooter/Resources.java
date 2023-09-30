package Complete.spaceshooter;

import EngineX.Resource;

class Resources {
	// CONSTANTS.... (Just to make it easier typing...)
	private static final int	IMAGE								= Resource.IMAGE;
	private static final int	SOUND								= Resource.SOUND;
	
	// IMAGES ==================================================================
	
	// MONSTERS
	Resource									converted						= new Resource("res/Complete.spaceshooter/images/monsters/converted.png", IMAGE);
	Resource									possessed						= new Resource("res/Complete.spaceshooter/images/monsters/possessed.png", IMAGE);
	Resource									minion							= new Resource("res/Complete.spaceshooter/images/monsters/minion.png", IMAGE);
	Resource									overlord						= new Resource("res/Complete.spaceshooter/images/monsters/overlord.png", IMAGE);
	Resource									demon								= new Resource("res/Complete.spaceshooter/images/monsters/demon.png", IMAGE);
	Resource									fallen_angel				= new Resource("res/Complete.spaceshooter/images/monsters/fallen_angel.png", IMAGE);
	Resource									devil								= new Resource("res/Complete.spaceshooter/images/monsters/devil.png", IMAGE);
	
	// MONSTER BULLETS
	Resource									converted_bullet		= new Resource("res/Complete.spaceshooter/images/monsters/converted_bullet.png", IMAGE);
	Resource									possessed_bullet		= new Resource("res/Complete.spaceshooter/images/monsters/possessed_bullet.png", IMAGE);
	Resource									minion_bullet				= new Resource("res/Complete.spaceshooter/images/monsters/minion_bullet.png", IMAGE);
	Resource									overlord_bullet			= new Resource("res/Complete.spaceshooter/images/monsters/overlord_bullet.png", IMAGE);
	Resource									demon_bullet				= new Resource("res/Complete.spaceshooter/images/monsters/demon_bullet.png", IMAGE);
	Resource									fallen_angel_bullet	= new Resource("res/Complete.spaceshooter/images/monsters/fallen_angel_bullet.png", IMAGE);
	Resource									devil_bullet				= new Resource("res/Complete.spaceshooter/images/monsters/devil_bullet.png", IMAGE);
	
	// PLAYER
	Resource									player							= new Resource("res/Complete.spaceshooter/images/player/player.png", IMAGE);
	Resource									playerBullet				= new Resource("res/Complete.spaceshooter/images/player/playerBullet.png", IMAGE);
	
	// BUTTONS
	Resource									playButton					= new Resource("res/Complete.spaceshooter/images/btnPlay.png", IMAGE);
	Resource									quitButton					= new Resource("res/Complete.spaceshooter/images/btnQuit.png", IMAGE);
	
	Resource									resumeButton				= new Resource("res/Complete.spaceshooter/images/btnResume.png", IMAGE);
	Resource									quitToMenuButton		= new Resource("res/Complete.spaceshooter/images/btnQuitToMenu.png", IMAGE);
	
	// STATES
	Resource									spaceBG							= new Resource("res/Complete.spaceshooter/images/spacebg.png", IMAGE);
	Resource									gameOverImage				= new Resource("res/Complete.spaceshooter/images/gameOver.png", IMAGE);
	Resource									levelCompleteImage	= new Resource("res/Complete.spaceshooter/images/level_complete.png", IMAGE);
	
	// OTHER
	Resource									crosshair						= new Resource("res/crosshair.png", IMAGE);
	Resource									logo								= new Resource("res/Complete.spaceshooter/images/logo.png", IMAGE);
	
	// SOUNDS ==================================================================
	
	// MONSTERS
	Resource									monsterHurt					= new Resource("res/Complete.spaceshooter/sounds/hurt2.ogg", SOUND);
	Resource									monsterExplosion		= new Resource("res/Complete.spaceshooter/sounds/explosion2.ogg", SOUND);
	
	// PLAYER
	Resource									playerShoot					= new Resource("res/Complete.spaceshooter/sounds/shoot.ogg", SOUND);
	Resource									playerHurt					= new Resource("res/Complete.spaceshooter/sounds/hurt.ogg", SOUND);
	Resource									playerExplosion			= new Resource("res/Complete.spaceshooter/sounds/explosion.ogg", SOUND);
	
	Resource	gameOverSound		= new Resource("res/Complete.spaceshooter/sounds/gameover.ogg", SOUND);
	Resource	levelCompleteSound	= new Resource("res/Complete.spaceshooter/sounds/levelComplete.ogg", SOUND);
	// BUTTONS
	Resource									buttonHoverSound		= new Resource("res/replicants/sfx/buttonHover.ogg", SOUND);
	
	// STATES
	Resource									pauseSong						= new Resource("res/Complete.spaceshooter/sounds/pauseSong.ogg", SOUND);
	Resource									menuSong						= new Resource("res/Complete.spaceshooter/sounds/menuThemeSong.ogg", SOUND);
	Resource									playSong						= new Resource("res/Complete.spaceshooter/sounds/menuSongTheme.ogg", SOUND);
}