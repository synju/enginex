package Complete.megamoney;

import EngineX.Resource;

class Resources {
	// CONSTANTS.... (Just to make it easier typing...)
	private static final int	IMAGE								= Resource.IMAGE;
	private static final int	SOUND								= Resource.SOUND;
	
	// IMAGES ==================================================================
//	Resource									crosshair						= new Resource("res/crosshair.png", IMAGE);
//	Resource									logo								= new Resource("res/spaceshooter/images/logo.png", IMAGE);
	
	// SOUNDS ==================================================================
	Resource									spinSound						= new Resource("res/megamoney/spinSound.ogg", SOUND);
	Resource									doodadSound					= new Resource("res/megamoney/doodad.ogg", SOUND);
	Resource									jobSound						= new Resource("res/megamoney/job.ogg", SOUND);
	Resource									opportunitySound		= new Resource("res/megamoney/opportunity.ogg", SOUND);
	Resource									charitySound				= new Resource("res/megamoney/charity.ogg", SOUND);
	Resource									giftSound						= new Resource("res/megamoney/gift.ogg", SOUND);
	Resource									phoneSound					= new Resource("res/megamoney/phone.ogg", SOUND);
}
