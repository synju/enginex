package enginex.core;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Sound {
	Audio		soundEffect;
	String	soundPath;
	float		position;
	
	public Sound(String path) {
		this.soundPath = path;
		
		try {
			soundEffect = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream(path));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void play() {
		soundEffect.playAsSoundEffect(1.0f, 1.0f, false);
	}
	
	public void play(float pitch, float gain, boolean loop) {
		soundEffect.playAsMusic(pitch, gain, loop);
	}
	
	public void pause() {
		if(soundEffect.isPlaying()) {
			position = soundEffect.getPosition();
			soundEffect.stop();
		}
	}
	
	public void resume() {
		if(!soundEffect.isPlaying()) {
			soundEffect.setPosition(position);
			play();
		}
	}
}
