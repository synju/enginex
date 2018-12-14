package enginex;

import java.util.ArrayList;

public class SoundMachine {
	public EngineX game;

	public ArrayList<Sound> sounds = new ArrayList<Sound>();

	public static final int	PLAY			= 0;
	public static final int	PAUSE			= 1;
	public static final int	STOP			= 2;
	public static final int	INCREASEVOLUME	= 3;
	public static final int	DECREASEVOLUME	= 4;

	public SoundMachine(EngineX game) {
		this.game = game;
	}

	public void add(Sound sound, int group) {
		new Thread(() -> {
			sound.setGroup(group);
			sounds.add(sound);
		}).start();
	}

	public void add(Sound sound) {
		new Thread(() -> {
			boolean hasSound = false;

			for(Sound s:sounds)
				if(s.path == sound.path)
					hasSound = true;

			if(!hasSound)
				sounds.add(sound);
		}).start();
	}

	public float getPosition(String path) {
		for(Sound s:sounds)
			if(s.path == path)
				return s.getPosition();

		return 0;
	}

	public void setPosition(String path, float position) {
		for(Sound s:sounds)
			if(s.path == path)
				s.setPosition(position);
	}

	public void playSong(String path) {
		new Thread(() -> {
			for(Sound s:sounds)
				if(s.path == path)
					s.playSong();
		}).start();
				}
	
	public void playSongResource(Resource songResource) {
		songResource.getSound().playSong();
	}

	public void playSound(String path) {
		new Thread(() -> {
			for(Sound s:sounds) {
				if(s.path == path) {
					s.playSound();
				}
			}
		}).start();
	}

	public void play(int group) {
		new Thread(() -> {
			for(Sound s:sounds)
				if(s.group == group)
					s.play();
		}).start();
	}

	public void pause(int group) {
		new Thread(() -> {
			for(Sound s:sounds)
				if(s.group == group)
					s.pause();
		}).start();
	}

	public void stop(int group) {
		new Thread(() -> {
			for(Sound s:sounds)
				if(s.group == group)
					s.stop();
		}).start();
	}

	public void increaseVolume(int group) {
		new Thread(() -> {
			for(Sound s:sounds)
				if(s.group == group)
					s.increaseVolume();
		}).start();
	}

	public void decreaseVolume(int group) {
		new Thread(() -> {
			for(Sound s:sounds)
				if(s.group == group)
					s.decreaseVolume();
		}).start();
	}
}
