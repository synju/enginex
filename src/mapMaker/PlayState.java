package mapMaker;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import javax.imageio.ImageIO;

import enginex.State;
import enginex.Util;

public class PlayState extends State {
	MapMaker				game;

	Block[][]				blocks;

	int							xBlocks						= 10;
	int							yBlocks						= 10;
	int							blockSize					= 32;
	int							blockWidth				= blockSize;
	int							blockHeight				= blockSize;

	int							layers						= 3;
	int							currentLayer			= 0;

	public int			ox								= 140;
	public int			oy								= 10;

	boolean					up								= false;
	boolean					down							= false;
	boolean					left							= false;
	boolean					right							= false;
	boolean					control						= false;
	boolean					shift							= false;
	boolean					pointerDown				= false;
	boolean					rightPointerDown	= false;

	int							speed							= 4;
	int							maxScale					= 6;
	int							minScale					= 1;

	int							diff							= 0;
	int							cx								= 0;
	int							cy								= 0;
	int							blockSizeScaled		= 0;
	public Block		currentBlock;

	ArrayList<Tile>	tiles							= new ArrayList<>();
	Image[]					images;
	int							currentImage			= 0;

	boolean					paintToolEnabled	= false;

	ImageLibrary		imgLib;
	int							libx							= 0;
	int							liby							= 0;

	protected PlayState(MapMaker game) {
		super(game);
		this.game = game;
	}

	public void initialize() {
		if(initialized)
			return;

		create();

		initialized = true;
	}

	public void create() {
		game.scale = 1;
		currentBlock = new Block(game, 0, 0, 0, 0, blockSize, blockSize, currentLayer);
		generateGrid();
		loadImages();
		imgLib = new ImageLibrary(game, images, 5, 0, 0, blockSize, game.getHeight());
	}

	public void loadImages() {
		ArrayList<Image> list = new ArrayList<>();

		// Add Textures
		// list.add(Textures.grass);

		// BufferedImageTextures
		BufferedImage[] rf = getImages("res/mapmaker/rocky_forest.png", blockSize);
		for(Image tx:rf)
			list.add(tx);

		images = new Image[list.size()];
		for(int i = 0; i < images.length; i++)
			images[i] = list.get(i);
	}

	public void generateGrid() {
		blocks = new Block[xBlocks][yBlocks];

		for(int y = 0; y < yBlocks; y++) {
			for(int x = 0; x < xBlocks; x++) {
				Block b = new Block(game, (int)((x * blockSize)), (int)((y * blockSize)), x, y, blockSize, blockSize, 0);
				blocks[x][y] = b;
			}
		}
	}

	public void update() {
		initialize();

		move();
		addTile();
		removeTile();
		updateTiles();
		updateCurrentBlock();

		imgLib.update();
	}

	public void updateTiles() {
		try {
			if(!tiles.isEmpty())
				for(Tile t:tiles)
					t.update();
		}
		catch(Exception e) {
			// Do Nothing
		}
	}

	public void render(Graphics2D g) {
		renderCurrentBlock(g);
		renderTiles(g);

		renderText(g);
		renderLibrary(g);
		renderCurrentImage(g);
		renderCenter(g);
	}

	public void renderLibrary(Graphics2D g) {
		imgLib.render(g);

		// int xc = 29;
		// int yc = 10;
		//
		// int index = 0;
		// for(int y = 0; y < yc; y++) {
		// for(int x = 0; x < xc; x++) {
		// AffineTransform at = new AffineTransform();
		// at.translate(ox + x * blockSize * game.scale, oy + y * blockSize *
		// game.scale);
		// at.scale(game.scale, game.scale);
		// g.drawImage(images[index], at, null);
		// if(index < images.length)
		// index++;
		// }
		// }
	}

	public void renderCenter(Graphics2D g) {
		g.setColor(Color.WHITE);
		g.fillRect(ox, oy, 2, 2);
	}

	public void updateCurrentBlock() {
		if(paintToolEnabled) {
			try {
				Point m = game.getMousePosition();
				blockSizeScaled = blockSize * (int)game.scale;

				if(m.x > ox) {
					diff = m.x - ox;
					cx = (int)(Math.floor(diff / blockSizeScaled));
				}
				if(ox > m.x) {
					diff = ox - m.x;
					cx = -(int)(Math.floor(diff / blockSizeScaled)) - 1;
				}
				if(m.y > oy) {
					diff = m.y - oy;
					cy = (int)(Math.floor(diff / blockSizeScaled));
				}
				if(oy > m.y) {
					diff = oy - m.y;
					cy = -(int)(Math.floor(diff / blockSizeScaled)) - 1;
				}

				currentBlock.x = cx * blockSize;
				currentBlock.y = cy * blockSize;
				currentBlock.xl = cx;
				currentBlock.yl = cy;
				currentBlock.layer = currentLayer;
			}
			catch(Exception e) {
			}
		}
	}

	public void renderCurrentBlock(Graphics2D g) {
		if(paintToolEnabled)
			currentBlock.render(g);
	}

	public void renderCurrentImage(Graphics2D g) {
		// int previewScale = game.scale;
		int previewScale = 3;
		int xPos = (int)(imgLib.x - (previewScale * blockSize));
		int yPos = 0;

		// g.setColor(Color.BLACK);
		g.setColor(new Color(0f, 0f, 0f, 0.5f));
		g.fillRect(xPos, yPos, blockWidth * previewScale, blockHeight * previewScale);

		AffineTransform at = new AffineTransform();
		at.translate(xPos, yPos);
		at.scale(previewScale, previewScale);
		g.drawImage(images[currentImage], at, null);

		// g.setColor(Color.DARK_GRAY);
		// g.setColor(Color.WHITE);
		g.setColor(new Color(1f, 1f, 1f, 0.5f));
		g.drawRect(xPos, yPos, blockWidth * previewScale, blockHeight * previewScale);
	}

	public void renderTiles(Graphics2D g) {
		try {
			for(Tile t:tiles)
				t.render(g);
		}
		catch(Exception e) {
		}
	}

	public void renderText(Graphics2D g) {
		int x = 10;
		int y = 10;
		int winW = 115;
		int winH = 150;
		g.setColor(new Color(0f, 0f, 0f, 0.5f));
		g.fillRect(x, y, winW, winH);
		g.setColor(new Color(1f, 1f, 1f, 0.5f));
		g.drawRect(x, y, winW, winH);

		Util.drawText(x + 10, 1 * 20 + 10, "Scale: " + game.scale, g);
		Util.drawText(x + 10, 2 * 20 + 10, "Speed: " + speed, g);
		Util.drawText(x + 10, 3 * 20 + 10, "Layer: " + currentLayer, g);
		Util.drawText(x + 10, 4 * 20 + 10, "xBlocks: " + xBlocks, g);
		Util.drawText(x + 10, 5 * 20 + 10, "yBlocks: " + yBlocks, g);
		Util.drawText(x + 10, 6 * 20 + 10, "Block Size: " + blockSize + "x" + blockSize, g);
		Util.drawText(x + 10, 7 * 20 + 10, "Tiles: " + tiles.size(), g);
	}

	public void move() {
		if(up)
			oy += speed * game.scale;
		if(down)
			oy -= speed * game.scale;
		if(left)
			ox += speed * game.scale;
		if(right)
			ox -= speed * game.scale;
	}

	public void addTile() {
		if(pointerDown && paintToolEnabled && !imgLib.hasMouse()) {
			int x = (int)currentBlock.x;
			int y = (int)currentBlock.y;
			int xl = (int)currentBlock.xl;
			int yl = (int)currentBlock.yl;
			int w = blockSize;
			int h = blockSize;
			int layer = currentLayer;
			Image image = images[currentImage];
			Tile t = new Tile(game, x, y, xl, yl, w, h, layer, image);

			// Check if Tile Exists at same location
			for(int i = tiles.size() - 1; i >= 0; i--) {
				Tile tile = tiles.get(i);
				if(tile.xl == t.xl && tile.yl == t.yl && tile.layer == t.layer)
					tiles.remove(i);
			}

			// Insert Tile
			tiles.add(t);

			// Sort Tiles
			Collections.sort(tiles, (t1, t2) -> t1.getLayer() - t2.getLayer());
		}
	}

	public void removeTile() {
		if(rightPointerDown) {
			for(int i = tiles.size() - 1; i >= 0; i--) {
				Tile tile = tiles.get(i);
				if(tile.xl == currentBlock.xl && tile.yl == currentBlock.yl && tile.layer == currentLayer)
					tiles.remove(i);
			}
		}
	}

	public void refreshCanvas() {
		resetOXOY();
		tiles = new ArrayList<Tile>();
	}

	public void resetOXOY() {
		ox = 140;
		oy = 10;
	}

	public BufferedImage[] getImages(String path, int dim) {
		BufferedImage[] images = null;
		try {
			BufferedImage spriteSheet = ImageIO.read(new File(path));
			int xc = spriteSheet.getWidth() / dim;
			int yc = spriteSheet.getHeight() / dim;
			images = new BufferedImage[yc * xc];

			int i = 0;
			for(int y = 0; y < yc; y++) {
				for(int x = 0; x < xc; x++) {
					images[i] = spriteSheet.getSubimage(x * dim, y * dim, dim, dim);
					i++;
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}

		return images;
	}

	public void zoomIn() {
		if(game.scale < maxScale) {
			game.scale += 1f;
			if(game.scale > maxScale)
				game.scale = maxScale;
		}
	}

	public void zoomOut() {
		if(game.scale > minScale) {
			game.scale -= 1f;
			if(game.scale < minScale)
				game.scale = minScale;
		}
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
			game.exit();

		if(e.getKeyCode() == KeyEvent.VK_F5)
			refreshCanvas();

		if(e.getKeyCode() == KeyEvent.VK_W)
			up = true;

		if(e.getKeyCode() == KeyEvent.VK_S)
			down = true;

		if(e.getKeyCode() == KeyEvent.VK_A)
			left = true;

		if(e.getKeyCode() == KeyEvent.VK_D)
			right = true;

		if(e.getKeyCode() == KeyEvent.VK_CONTROL)
			control = true;

		if(e.getKeyCode() == KeyEvent.VK_SHIFT)
			shift = true;

		if(e.getKeyCode() == KeyEvent.VK_R) {
			resetOXOY();
		}

		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			xBlocks++;
			generateGrid();
		}

		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			if(xBlocks > 1)
				xBlocks--;
			generateGrid();
		}

		if(e.getKeyCode() == KeyEvent.VK_UP) {
			if(yBlocks > 1)
				yBlocks--;
			generateGrid();
		}

		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			yBlocks++;
			generateGrid();
		}
	}

	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_P) {
			if(paintToolEnabled)
				paintToolEnabled = false;
			else
				paintToolEnabled = true;
		}

		if(e.getKeyCode() == KeyEvent.VK_W)
			up = false;

		if(e.getKeyCode() == KeyEvent.VK_S)
			down = false;

		if(e.getKeyCode() == KeyEvent.VK_A)
			left = false;

		if(e.getKeyCode() == KeyEvent.VK_D)
			right = false;

		if(e.getKeyCode() == KeyEvent.VK_ADD)
			speed++;

		if(e.getKeyCode() == KeyEvent.VK_SUBTRACT)
			if(speed > 1)
				speed--;

		if(e.getKeyCode() == KeyEvent.VK_CONTROL)
			control = false;

		if(e.getKeyCode() == KeyEvent.VK_SHIFT)
			shift = false;

		if(e.getKeyCode() == KeyEvent.VK_EQUALS) {
			if(control)
				if(game.scale < maxScale)
					game.scale++;
		}

		if(e.getKeyCode() == KeyEvent.VK_MINUS) {
			if(control)
				if(game.scale > minScale)
					game.scale--;
		}

		if(e.getKeyCode() == KeyEvent.VK_F1) {
			if(currentLayer > 0)
				currentLayer--;
		}

		if(e.getKeyCode() == KeyEvent.VK_F2) {
			if(currentLayer < 3)
				currentLayer++;
		}

	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		super.mouseWheelMoved(e);
		boolean direction = e.getWheelRotation() < 0; // true = up.. false = down..
		if(direction) {
			if(control) {
				zoomIn();
			}
			else if(shift) {
				if(currentImage < images.length)
					currentImage++;
			}
		}
		else {
			if(control) {
				zoomOut();
			}
			else if(shift) {
				if(currentImage > 0)
					currentImage--;
			}
		}

		if(!control && !shift && imgLib.hasMouse())
			imgLib.mouseWheelMoved(e);
	}

	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			pointerDown = true;
			imgLib.mousePressed(e);
		}

		if(e.getButton() == MouseEvent.BUTTON3)
			rightPointerDown = true;
	}

	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1)
			pointerDown = false;

		if(e.getButton() == MouseEvent.BUTTON3)
			rightPointerDown = false;
	}
}
