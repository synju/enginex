package menuexp;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import enginex.Button;
import enginex.EngineX;
import enginex.State;

public class MenuState extends State {
	ArrayList<Button> buttons;

	Image	blue_sky;
	Image	upButton;
	Image	hoverButton;
	Image	downButton;

	public MenuState(EngineX game) {
		super(game);
	}

	public void init() {
		try {
			blue_sky = new ImageIcon("res/blue_sky.png").getImage();
			upButton = new ImageIcon("res/upButton.png").getImage();
			hoverButton = new ImageIcon("res/hoverButton.png").getImage();
			downButton = new ImageIcon("res/downButton.png").getImage();

			buttons = new ArrayList<>();

			setupButtons();
		}
		catch(Exception e) {}
	}

	public void update() {
		for(Button b:buttons) {
			b.update();
		}
	}

	public void render(Graphics2D g) {
		g.drawImage(blue_sky, 0, 0, null);
		for(Button b:buttons) {
			b.render(g);
		}
	}

	public void setupButtons() {
//		// Play Button
//		Button b = new Button(game, upButton, hoverButton, downButton);
//		b.init(game.getWidth() / 2 - b.getWidth() / 2, game.getHeight() / 2 - b.getHeight() / 2);
//		b.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				System.out.println("PlayButton Clicked!");
//			}
//		});

//		buttons.add(b);
	}

	public void mousePressed(MouseEvent e) {
		super.mousePressed(e);

		for(Button b:buttons) {
			b.mousePressed(e);
		}
	}

	public void mouseReleased(MouseEvent e) {
		super.mouseReleased(e);

		for(Button b:buttons) {
			b.mouseReleased(e);
		}
	}
}
