package enginex.core;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Sound {
	Audio						audio;
	float						position	= 0f;
	float						pitch			= 1.0f;
	public float		gain			= 1.0f;
	public boolean	loop			= false;
	
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
			audio.playAsSoundEffect(pitch, gain, false);
			audio.setPosition(position);
		}
	}
	
	public void play(float gain) {
		if(!audio.isPlaying()) {
			audio.playAsSoundEffect(pitch, gain, loop);
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
	
	public void increaseVolume() {
		if(gain < 1.0f)
			gain += 0.1f;
		
		if(gain > 1.0f)
			gain = 1.0f;
		
		if(audio.isPlaying()) {
			pause();
			audio.playAsSoundEffect(pitch, gain, false);
			audio.setPosition(position);
		}
	}
	
	public void decreaseVolume() {
		if(gain > 0f)
			gain -= 0.1f;
		
		if(gain < 0f)
			gain = 0f;
		
		if(audio.isPlaying()) {
			pause();
			audio.playAsSoundEffect(1.0f, gain, false);
			audio.setPosition(position);
		}
	}
	
	public boolean isPlaying() {
		return audio.isPlaying();
	}
	
	public boolean isPaused() {
		if(position > 0 && !isPlaying())
			return true;
		
		return false;
	}
}
