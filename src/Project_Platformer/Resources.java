package Project_Platformer;

import enginex.Resource;

class Resources {
	// CONSTANTS.... (Just to make it easier typing...)
	private static final int IMAGE = Resource.IMAGE;
	private static final int SOUND = Resource.SOUND;

	// IMAGES ==================================================================
	Resource grid_bg = new Resource("bin/project_platformer/res/grid_bg.png", IMAGE);
	Resource grid_block = new Resource("bin/project_platformer/res/grid_block.png", IMAGE);

	// SOUNDS ==================================================================
	Resource spinSound = new Resource("res/megamoney/spinSound.ogg", SOUND);
}
