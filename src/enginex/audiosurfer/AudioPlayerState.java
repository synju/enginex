package enginex.audiosurfer;

import java.awt.Graphics2D;

import enginex.core.EngineX;
import enginex.core.State;

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

	protected void update() {}

	protected void render(Graphics2D g) {}
}
