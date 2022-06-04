package Complete.megamoney;

import EngineX.Resource;

class Resources {
	// CONSTANTS.... (Just to make it easier typing...)
	private static final int	IMAGE								= Resource.IMAGE;
	private static final int	SOUND								= Resource.SOUND;
	
	// IMAGES ==================================================================
	Resource									crosshair						= new Resource("res/crosshair.png", IMAGE);
	Resource									logo								= new Resource("res/Complete.spaceshooter/images/logo.png", IMAGE);
	
	// SOUNDS ==================================================================
	Resource									spinSound						= new Resource("res/Complete.megamoney/spinSound.ogg", SOUND);
	Resource									doodadSound					= new Resource("res/Complete.megamoney/doodad.ogg", SOUND);
	Resource									jobSound						= new Resource("res/Complete.megamoney/job.ogg", SOUND);
	Resource									opportunitySound		= new Resource("res/Complete.megamoney/opportunity.ogg", SOUND);
	Resource									charitySound				= new Resource("res/Complete.megamoney/charity.ogg", SOUND);
	Resource									giftSound						= new Resource("res/Complete.megamoney/gift.ogg", SOUND);

	Resource									phoneSound					= new Resource("res/Complete.megamoney/phone.ogg", SOUND);
}
