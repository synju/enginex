package Archive.audiosurfer;

import EngineX.EngineX;

public class AudioSurfer extends EngineX {
	AudioPlayerState aps;

	protected AudioSurfer(String gameName) {
		super(gameName);
	}

	public void init() {
		aps = new AudioPlayerState(this);
		stateMachine.pushState(aps);
		stateMachine.getCurrentState();
		window.setVisible(true);
		run();
	}
	
	public static void main(String[] args) {
		new AudioSurfer("Audio Surfer").init();
	}
}
