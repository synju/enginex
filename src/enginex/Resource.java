package enginex;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Resource {
	public static final int	SOUND	= 0;
	public static final int	IMAGE	= 1;

	private Sound	sound;
	private Image	image;
	private String	path;

	private int type;

	public Resource(String path, int type) {
		this.type = type;
		this.path = path;

		if(type == SOUND)
			this.sound = new Sound(path);

		if(type == IMAGE)
			this.image = new ImageIcon(path).getImage();
	}

	public String getType() {
		String stringType = null;

		if(type == SOUND)
			stringType = "sound";

		if(type == IMAGE)
			stringType = "image";

		return stringType;
	}

	public String getPath() {
		return path;
	}

	public Image getImage() {
		return image;
	}

	public Sound getSound() {
		return sound;
	}
}
