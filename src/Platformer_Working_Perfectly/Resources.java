package Platformer_Working_Perfectly;

import enginex.Resource;

class Resources {
	// CONSTANTS.... (Just to make it easier typing...)
	private static final int	IMAGE								= Resource.IMAGE;
	private static final int	SOUND								= Resource.SOUND;

	// IMAGES ==================================================================
	Resource									grid_bg						= new Resource("res/moo/grid_bg.png", IMAGE);
	Resource									grid_block						= new Resource("res/moo/grid_block.png", IMAGE);

	// SOUNDS ==================================================================
	Resource									spinSound						= new Resource("res/megamoney/spinSound.ogg", SOUND);
}
