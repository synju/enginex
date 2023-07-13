package Archive.zeldaClone;

import EngineX.Sound;

import javax.swing.*;
import java.awt.*;

public class Resources {
	// Images
	public Image bg = new ImageIcon("bin/archive/zeldaClone/res/bg.png").getImage();
	public Image playButton = new ImageIcon("bin/archive/zeldaClone/res/playButton.png").getImage();
	public Image quitButton = new ImageIcon("bin/archive/zeldaClone/res/quitButton.png").getImage();
	public Image selector = new ImageIcon("bin/archive/zeldaClone/res/selector.png").getImage();
	public Image byebye = new ImageIcon("bin/archive/zeldaClone/res/byebye.png").getImage();
	public Image normalMouse = new ImageIcon("bin/archive/zeldaClone/res/normalMouse.png").getImage();

	// Test
	public Image testRoomBG = new ImageIcon("bin/archive/zeldaClone/res/testRoomBG.png").getImage();

	// Player Images
	public Image playerIdle = new ImageIcon("bin/archive/zeldaClone/res/playerIdle.png").getImage();

	// Bullet Images
	public Image standardBullet = new ImageIcon("bin/archive/zeldaClone/res/standardBullet.png").getImage();

	// Sounds
//	public Sound menuMusic = new Sound("bin/archive/zeldaClone/res/button_change.ogg");
	public Sound buttonChange = new Sound("bin/archive/zeldaClone/res/button_change.ogg");
	public Sound quitSound = new Sound("bin/archive/zeldaClone/res/quitSound.ogg");
}
