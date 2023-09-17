package HEC_Industries;

import EngineX.Resource;

public class Resources {
	// CONSTANTS.... (Just to make it easier typing...)
	private static final int IMAGE = Resource.IMAGE;
	private static final int SOUND = Resource.SOUND;

	// IMAGES ==================================================================
	public Resource focus_bg = new Resource("bin/HEC_Industries/res/focus_bg.png", IMAGE);
	public Resource grid_bg = new Resource("bin/HEC_Industries/res/grid_bg.png", IMAGE);
	public Resource grid_block = new Resource("bin/HEC_Industries/res/grid_block.png", IMAGE);
	public Resource block_dirt_dark = new Resource("bin/HEC_Industries/res/block_dirt_dark.png", IMAGE);
	public Resource block_dirt = new Resource("bin/HEC_Industries/res/block_dirt.png", IMAGE);
	public Resource block_iron = new Resource("bin/HEC_Industries/res/block_iron.png", IMAGE);
	public Resource block_copper = new Resource("bin/HEC_Industries/res/block_copper.png", IMAGE);
	public Resource block_coal = new Resource("bin/HEC_Industries/res/block_coal.png", IMAGE);

	public Resource block_crack1 = new Resource("bin/HEC_Industries/res/crack1.png", IMAGE);
	public Resource block_crack2 = new Resource("bin/HEC_Industries/res/crack2.png", IMAGE);
	public Resource block_crack3 = new Resource("bin/HEC_Industries/res/crack3.png", IMAGE);

	// Structures...
	public Resource coaldrill_placed = new Resource("bin/HEC_Industries/res/structures/coaldrill_placed.png", IMAGE);
	public Resource coaldrill_placing = new Resource("bin/HEC_Industries/res/structures/coaldrill_placing.png", IMAGE);
	public Resource coaldrill_invalid = new Resource("bin/HEC_Industries/res/structures/coaldrill_invalid.png", IMAGE);

	// SOUNDS ==================================================================
//	Resource spinSound = new Resource("res/Complete.megamoney/spinSound.ogg", SOUND);
}
