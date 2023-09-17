package HEC_Industries;

public class Camera extends EmptyObject {
	static int cameraBoxWidth = 3 * Block.blockWidth;
	static int cameraBoxHeight = 3 * Block.blockHeight;
	static int cameraBoxExtraHeight = 15;
	boolean cameraCorrection;
	boolean sideToSideCamera;
	boolean upAndDownCamera;
	boolean renderCameraBox;

	Player player;

	Camera(Game game, int offsetX, int offsetY, int width, int height) {
		super(game, offsetX, offsetY, width, height);

		cameraCorrection = true;
		sideToSideCamera = true;
		upAndDownCamera = true;
		renderCameraBox = false;

		player = game.ps.levelHandler.player;
	}

	void cameraCorrection() {
		if(cameraCorrection) {
			int difference;

			// Side to Side
			if(sideToSideCamera) {
				// Left Side
				if(player.x < x) {
					difference = (x - (int)player.x);
					game.ps.levelHandler.worldX += difference;
					player.x += difference;
				}

				// Right Side
				if((player.x + player.w) > (x + w)) {
					difference = (((int)player.x + player.w) - (x + w));
					game.ps.levelHandler.worldX -= difference;
					player.x -= difference;
				}
			}

			// Up and Down
			if(upAndDownCamera) {// Upper Side
				if(player.y < (y - cameraBoxExtraHeight)) {
					difference = ((y - cameraBoxExtraHeight) - (int)player.y);
					game.ps.levelHandler.worldY += difference;
					player.y += difference;
				}

				// Bottom Side
				if(player.y + player.h > (y + h)) {
					difference = (int)(player.y + player.h) - (y + h);
					game.ps.levelHandler.worldY -= difference;
					player.y -= difference;
				}
			}
		}
	}
}
