package Archive.zeldaClone;

import EngineX.Sound;

import javax.swing.*;
import java.awt.*;

public class Resources {
	// Images
	public Image bg = new ImageIcon("src/Archive.zeldaClone/res/bg.png").getImage();
	public Image playButton = new ImageIcon("src/Archive.zeldaClone/res/playButton.png").getImage();
	public Image quitButton = new ImageIcon("src/Archive.zeldaClone/res/quitButton.png").getImage();
	public Image selector = new ImageIcon("src/Archive.zeldaClone/res/selector.png").getImage();
	public Image byebye = new ImageIcon("src/Archive.zeldaClone/res/byebye.png").getImage();
	public Image normalMouse = new ImageIcon("src/Archive.zeldaClone/res/normalMouse.png").getImage();

	// Test
	public Image testRoomBG = new ImageIcon("src/Archive.zeldaClone/res/testRoomBG.png").getImage();

	// Player Images
	public Image playerIdle = new ImageIcon("src/Archive.zeldaClone/res/playerIdle.png").getImage();

	// Bullet Images
	public Image standardBullet = new ImageIcon("src/Archive.zeldaClone/res/standardBullet.png").getImage();

	// Sounds
//	public Sound menuMusic = new Sound("src/Archive.zeldaClone/res/button_change.ogg");
	public Sound buttonChange = new Sound("src/Archive.zeldaClone/res/button_change.ogg");
	public Sound quitSound = new Sound("src/Archive.zeldaClone/res/quitSound.ogg");
}
