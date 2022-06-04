package Archive.miniworld_top_down_potential;

import java.awt.Color;
import java.awt.Graphics2D;

import EngineX.GameObject;
import processing.core.PApplet;

@SuppressWarnings("serial")
public class NoiseGenerator extends GameObject {
	MiniWorld	game;
	Color			c;
	PApplet		p		= new PApplet();
	
	float			inc	= 0.03125f;
	
	Color[][]	pixels1;
	Color[][]	pixels2;
	Color[][]	pixels3;
	Color[][]	pixels4;
	int[][]		noiseArray = genNoiseArray(10, 10, 10, 99);
	float offsetX = 0;
	float offsetY = 1000;
	
	int imgD = 100;
	public NoiseGenerator(MiniWorld game) {
		super(game);
		this.game = game;
		
		pixels1 = genImage(0,0,imgD,imgD);
		pixels2 = genImage(0,1,imgD,imgD);
		pixels3 = genImage(0,2,imgD,imgD);
		pixels4 = genImage(1,2,imgD,imgD);
	}
	
	void printNoiseArray() {
		for(int x = 0; x < 10; x++) {
			for(int y = 0; y < 10; y++)
				System.out.print(noiseArray[y][x]);		
			System.out.print("\n");
		}
	}
	
	int[][] genNoiseArray(int width, int height, int min, int max) {
		int[][] noiseArray = new int[width][height];
		
		float offsetX = 0;
		float offsetY = 1000;
		float inc = 0.01f;
		p.noiseDetail(4);
		
		for(int y = 0; y < height; y++) {
			offsetX = 0;
			for(int x = 0; x < width; x++) {
				float r = p.noise(offsetX, offsetY);
				
				noiseArray[x][y] = map(r, min, max);
				
				offsetX += inc;
			}
			offsetY += inc;
		}
		
		return noiseArray;
	}
	
	Color[][] genImage(float offsetX, float offsetY,int width, int height) {
		Color[][] pixels = new Color[width][height];
		float ini_offsetX = offsetX;
		float inc = 0.01f;
		p.noiseDetail(7);
		
		for(int y = 0; y < width; y++) {
//			offsetX = 0;
			offsetX = ini_offsetX;
			for(int x = 0; x < height; x++) {
				float r = p.noise(offsetX, offsetY);
				
				pixels[x][y] = new Color(r, r, r, 1f);
				
				offsetX += inc;
			}
			offsetY += inc;
		}
//		System.out.println(offsetX);
		
		return pixels;
	}
	
	float map(float v, float min, float max) {
		return PApplet.map(v, 0, 1, min, max);
	}
	
	int map(float v, int min, int max) {
		return (int)PApplet.map(v, 0, 1, min, max);
	}
	
	float genNoise(float offset, float max) {
		return PApplet.map(p.noise(offset), 0, 1, 0, max);
	}
	
	void drawPixels(Graphics2D g, Color[][] pixels, int ox, int oy, int width, int height) {
		int xWidth = pixels.length;
		int yHeight = pixels[0].length;
		for(int y = 0; y < yHeight; y++) {
			for(int x = 0; x < xWidth; x++) {
				g.setColor(pixels[x][y]);
				g.fillRect(ox+x, oy+y, 1, 1);
			}
		}
	}
	
	public void render(Graphics2D g) {
		Color[][]	pixels;
		for(int y = 0; y < 7; y++) {
			for(int x = 0; x < 7; x++) {
				pixels = genImage(x,y,imgD,imgD);
				drawPixels(g,pixels,x*imgD,y*imgD,imgD,imgD);
			}
		}
		
//		drawPixels(g,pixels1,0,0,imgD,imgD);
//		drawPixels(g,pixels2,0,imgD,imgD,imgD);
//		drawPixels(g,pixels3,0,imgD*2,imgD,imgD);
//		drawPixels(g,pixels4,imgD,imgD*2,imgD,imgD);
	}
}

