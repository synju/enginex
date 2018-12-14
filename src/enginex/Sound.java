package enginex;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Sound {
	Audio						audio;
	float						position	= 0f;
	float						pitch			= 1.0f;
	public float		gain			= 1.0f;
	public boolean	loop			= false;
	public int			group;
	public String path;
	
	public Sound(String path) {
		this.path = path;
		setAudio(path);
	}
	public void setAudio(String path) {
		for(int i = 0; i < 5; i++) {
		try {
			audio = AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream(path));
				break;
		}
		catch(Exception e) {
				if(i == 0)
					System.out.println("Failed loading audio 1 time, trying again");
				else
					System.out.println("Failed loading audio " + i + 1 + " times, trying again");
				setAudio(path);
		}
	}
	}
	public void setGroup(int group) {
		this.group = group;
	}
	
	public void setPosition(float position) {
		audio.setPosition(position);
}
	
	public float getPosition() {
			return audio.getPosition();
	}
	
	public void playSong() {
		audio.playAsSoundEffect(pitch, gain, true);
		audio.setPosition(position);
	}
	
	public void playSound() {
			audio.playAsSoundEffect(pitch, gain, false);
			audio.setPosition(position);
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
