package enginex.audiosurfer;

import enginex.core.EngineX;

public class AudioSurfer extends EngineX {
	AudioPlayerState aps;

	protected AudioSurfer(String gameName) {
		super(gameName);
	}

	public void init() {
		aps = new AudioPlayerState(this);
		stateMachine.pushState(aps);
		stateMachine.getCurrentState();
		run();
	}
}
