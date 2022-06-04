package Archive.audiosurfer;

import java.awt.Graphics2D;

import EngineX.EngineX;
import EngineX.State;

public class AudioPlayerState extends State {
	public AudioPlayerState(EngineX game) {
		super(game);
	}

	public void init() {
		try {}
		catch(Exception e) {
			game.exit(e.toString());
		}
	}

	public void update() {}

	public void render(Graphics2D g) {}
}
