package miniworld;

import enginex.Animation;
import enginex.Sprite;

public class AnimationTextures {
	public static String floors = "res/miniworld/floors.png";
	public static Animation GRASS = new Animation(Sprite.getSprite(1, 9, 32, 32, floors));
	public static Animation STONE = new Animation(Sprite.getSprite(1, 10, 32, 32, floors));
}
