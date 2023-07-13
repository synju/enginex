package SlotMachine;

import EngineX.Resource;

import javax.swing.*;
import java.awt.*;

public class Resources {
	// CONSTANTS.... (Just to make it easier typing...)
	private static final int IMAGE = Resource.IMAGE;
	private static final int SOUND = Resource.SOUND;

	// IMAGES ==================================================================
	Resource splashscreen = new Resource("res/slotmachine/images/splashscreen.png", IMAGE);

	Resource background       = new Resource("res/slotmachine/images/background.png", IMAGE);
	Resource background_full  = new Resource("res/slotmachine/images/background_full.png", IMAGE);
	Resource background_art   = new Resource("res/slotmachine/images/background_art.png", IMAGE);
	Resource gradient_overlay = new Resource("res/slotmachine/images/gradient_overlay.png", IMAGE);
	Resource text_mockup      = new Resource("res/slotmachine/images/text_mockup.png", IMAGE);
	Resource spin_button      = new Resource("res/slotmachine/images/spin_button.png", IMAGE);

	Resource lemon_img  = new Resource("res/slotmachine/images/symbols/lemon.png", IMAGE);
	Resource cherry_img = new Resource("res/slotmachine/images/symbols/cherry.png", IMAGE);
	Resource orange_img = new Resource("res/slotmachine/images/symbols/orange.png", IMAGE);
	Resource plum_img   = new Resource("res/slotmachine/images/symbols/plum.png", IMAGE);
	Resource peach_img  = new Resource("res/slotmachine/images/symbols/peach.png", IMAGE);
	Resource melon_img  = new Resource("res/slotmachine/images/symbols/melon.png", IMAGE);
	Resource grapes_img = new Resource("res/slotmachine/images/symbols/grapes.png", IMAGE);
	Resource seven_img  = new Resource("res/slotmachine/images/symbols/seven.png", IMAGE);

	Resource lemon_img_blurred  = new Resource("res/slotmachine/images/symbols/lemon_blurred.png", IMAGE);
	Resource cherry_img_blurred = new Resource("res/slotmachine/images/symbols/cherry_blurred.png", IMAGE);
	Resource orange_img_blurred = new Resource("res/slotmachine/images/symbols/orange_blurred.png", IMAGE);
	Resource plum_img_blurred   = new Resource("res/slotmachine/images/symbols/plum_blurred.png", IMAGE);
	Resource peach_img_blurred  = new Resource("res/slotmachine/images/symbols/peach_blurred.png", IMAGE);
	Resource melon_img_blurred  = new Resource("res/slotmachine/images/symbols/melon_blurred.png", IMAGE);
	Resource grapes_img_blurred = new Resource("res/slotmachine/images/symbols/grapes_blurred.png", IMAGE);
	Resource seven_img_blurred  = new Resource("res/slotmachine/images/symbols/seven_blurred.png", IMAGE);

	Resource spinButtonReady   = new Resource("res/slotmachine/images/spinButtonReady.png", IMAGE);
	Resource spinButtonInactive   = new Resource("res/slotmachine/images/spinButtonInactive.png", IMAGE);
	Resource spinButtonStop    = new Resource("res/slotmachine/images/spinButtonStop.png", IMAGE);
	Resource quickSpinButtonOn    = new Resource("res/slotmachine/images/quickSpinOn.png", IMAGE);
	Resource quickSpinButtonOff    = new Resource("res/slotmachine/images/quickSpinOff.png", IMAGE);
	Resource increaseBetButton = new Resource("res/slotmachine/images/increaseBetButton.png", IMAGE);
	Resource decreaseBetButton = new Resource("res/slotmachine/images/decreaseBetButton.png", IMAGE);
	Resource volumeOnButton    = new Resource("res/slotmachine/images/volumeOnButton.png", IMAGE);
	Resource volumeOffButton   = new Resource("res/slotmachine/images/volumeOffButton.png", IMAGE);
	Resource musicOnButton     = new Resource("res/slotmachine/images/musicOnButton.png", IMAGE);
	Resource musicOffButton    = new Resource("res/slotmachine/images/musicOffButton.png", IMAGE);
	Resource infoButton        = new Resource("res/slotmachine/images/infoButton.png", IMAGE);
	Resource autoSpinOnButton  = new Resource("res/slotmachine/images/autoSpinOn.png", IMAGE);
	Resource autoSpinOffButton = new Resource("res/slotmachine/images/autoSpinOff.png", IMAGE);

	// SOUNDS ==================================================================
	Resource themesong          = new Resource("res/slotmachine/sounds/themesong.ogg", SOUND);
	Resource spinSound          = new Resource("res/slotmachine/sounds/spinning.ogg", SOUND);
	Resource buttonSound        = new Resource("res/slotmachine/sounds/buttonSound.ogg", SOUND);
	Resource coinDropSoundShort = new Resource("res/slotmachine/sounds/coinDrop1.ogg", SOUND);
	Resource coinDropSoundLong  = new Resource("res/slotmachine/sounds/coinDrop2.ogg", SOUND);
}
