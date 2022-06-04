package Archive.zeldaClone;

import java.awt.*;

public class ScrollingBG {
	public Image bg;
	public float x;
	public float y;
	public int w;
	public int h;
	public float speed;
	boolean verticalScrolling = false;

	public ScrollingBG(Image image, float speed, int x, int y, int w, int h) {
		this.speed = speed;
		this.bg = image;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public void update() {
		if(verticalScrolling) {
			this.y += speed;
			if(y >= bg.getHeight(null))
				y = 0;
		}
		else {
			this.x -= speed;
			if(x <= -bg.getWidth(null))
				x = 0;
		}
	}

	public void render(Graphics2D g) {
		if(verticalScrolling) {
			g.drawImage(bg, (int) x, (int) y, null);
			g.drawImage(bg, (int) x, (int) y - bg.getHeight(null), null);
		}
		else {
			g.drawImage(bg, (int) x, (int) y, null);
			g.drawImage(bg, (int) x + bg.getWidth(null), (int) y, null);
		}
	}
}
