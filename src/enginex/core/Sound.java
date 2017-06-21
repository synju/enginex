package enginex.core;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Sound {
	Audio	audio;
	float	position	= 0;
	
	public Sound(String path) {
		try {
			audio = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream(path));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void play() {
		if(!audio.isPlaying()) {
			audio.playAsSoundEffect(1.0f, 1.0f, false);
			audio.setPosition(position);
		}
	}
	
	public void play(float pitch, float gain, boolean loop) {
		if(!audio.isPlaying()) {
			audio.playAsSoundEffect(pitch, gain, loop);
			audio.setPosition(position);
		}
	}
	
	public void pause() {
		if(audio.isPlaying()) {
			position = audio.getPosition();
			audio.stop();
		}
	}
	
	public void stop() {
		audio.stop();
		position = 0;
	}
}
