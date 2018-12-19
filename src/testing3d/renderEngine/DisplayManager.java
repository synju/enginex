package testing3d.renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.*;

public class DisplayManager {
	private static final int FPS_CAP = 120;

	// Really Small
//	private static final int WIDTH = 20 * 16;
//	private static final int HEIGHT = 20 * 9;

	// Small
//	private static final int WIDTH = 50 * 16;
//	private static final int HEIGHT = 50 * 9;

	// Medium
	private static final int WIDTH = 70 * 16;
	private static final int HEIGHT = 70 * 9;

	// Large
//	private static final int WIDTH = 100 * 16;
//	private static final int HEIGHT = 100 * 9;

	private static long lastFrameTime;
	private static float delta;

	public static void createDisplay() {
		ContextAttribs attributes = new ContextAttribs(3,2).withForwardCompatible(true).withProfileCore(true);

		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create(new PixelFormat(), attributes);
			Display.setTitle("Testing 3D");
		}
		catch(LWJGLException e) {
			// Do nothing...
		}

		GL11.glViewport(0,0,WIDTH, HEIGHT);
		lastFrameTime = getCurrentTime();
	}

	public static void updateDisplay() {
		Display.sync(FPS_CAP);
		Display.update();
		long currentFrameTime = getCurrentTime();
		delta = (currentFrameTime - lastFrameTime) / 1000f;
		lastFrameTime = currentFrameTime;
	}

	public static float getFrameTimeSeconds() {
		return delta;
	}

	public static void closeDisplay() {
		Display.destroy();
	}

	private static long getCurrentTime() {
		return Sys.getTime() * 1000 / Sys.getTimerResolution();
	}
}
