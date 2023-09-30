package GrappleMines;

public class Camera extends EmptyObject {
	static int cameraBoxWidth = Config.width;
	static int cameraBoxHeight = Config.height;
	boolean cameraCorrection;
	boolean upAndDownCamera;
	boolean renderCameraBox;

	Player  player;

	Camera(Game game, int offsetX, int offsetY, int width, int height) {
		super(game, offsetX, offsetY, width, height);

		cameraBoxWidth = width;
		cameraBoxHeight = height;

		cameraCorrection = true;
		upAndDownCamera = true;
		renderCameraBox = false;

		player = game.ps.player;
	}

	void cameraCorrection() {
		if(cameraCorrection) {
			int difference;

			// Up and Down
			if(upAndDownCamera) {// Upper Side
				if(player.y < y) {
					difference = y - player.y;
					game.ps.worldY += difference;
					player.y += difference;
				}

				// Bottom Side
				if(player.y + player.h > (y + h)) {
					difference = (player.y + player.h) - (y + h);
					game.ps.worldY -= difference;
					player.y -= difference;
				}
			}
		}
	}
}
