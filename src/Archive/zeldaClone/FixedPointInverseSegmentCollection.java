package Archive.zeldaClone;

import com.badlogic.gdx.math.Vector2;

import java.awt.*;
import java.util.ArrayList;

public class FixedPointInverseSegmentCollection {
	Game game;
	ArrayList<FixedPointInverseSegment> segments = new ArrayList<>();
	Vector2 position;
	Vector2 base;
	int len;
	int count;

	FixedPointInverseSegmentCollection(Game game, int len, int count, Vector2 base, Vector2 position) {
		this.game = game;
		this.len = len;
		this.count = count;
		this.position = position;
		this.base = base;

		for(int i = 0; i < count; i++) {
			if(i == 0) segments.add(new FixedPointInverseSegment(position.x, position.y, len, (float) Math.toRadians(0), game));
			else segments.add(new FixedPointInverseSegment(segments.get(i - 1), len, (float) Math.toRadians(0), game));
		}
	}

	public void update() {
		for(FixedPointInverseSegment s : segments)
			s.update();

//		segments.get(segments.size() - 1).setA(base);
//		for(int i = segments.size() - 2; i >= 0; i--)
//			segments.get(i).setA(segments.get(i).child.b);
	}

	public void render(Graphics2D g) {
		for(FixedPointInverseSegment s : segments)
			s.render(g);
	}


}
