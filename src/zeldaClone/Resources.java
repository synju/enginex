package zeldaClone;

import enginex.Sound;

import javax.swing.*;
import java.awt.*;

public class Resources {
	// Images
	public Image bg = new ImageIcon("src/zeldaClone/res/bg.png").getImage();
	public Image playButton = new ImageIcon("src/zeldaClone/res/playButton.png").getImage();
	public Image quitButton = new ImageIcon("src/zeldaClone/res/quitButton.png").getImage();
	public Image selector = new ImageIcon("src/zeldaClone/res/selector.png").getImage();
	public Image byebye = new ImageIcon("src/zeldaClone/res/byebye.png").getImage();
	public Image normalMouse = new ImageIcon("src/zeldaClone/res/normalMouse.png").getImage();

	// Test
	public Image testRoomBG = new ImageIcon("src/zeldaClone/res/testRoomBG.png").getImage();

	// Player Images
	public Image playerIdle = new ImageIcon("src/zeldaClone/res/playerIdle.png").getImage();

	// Bullet Images
	public Image standardBullet = new ImageIcon("src/zeldaClone/res/standardBullet.png").getImage();

	// Sounds
//	public Sound menuMusic = new Sound("src/zeldaClone/res/button_change.ogg");
	public Sound buttonChange = new Sound("src/zeldaClone/res/button_change.ogg");
	public Sound quitSound = new Sound("src/zeldaClone/res/quitSound.ogg");
}
