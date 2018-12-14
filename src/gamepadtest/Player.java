package gamepadtest;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import net.java.games.input.Component;

public class Player {
	GamePadTest							game;
	
	double									x				= 0;
	double									y				= 0;
	int											w				= 18 * 2;
	int											h				= 25 * 2;
	Color										color		= Color.red;
	
	float										speed = 10;
	
	boolean									left		= false;
	boolean									right		= false;
	boolean									up			= false;
	boolean									down		= false;
	
	float moveX = 0f;
	float moveY = 0f;
	
	Component[] components;
	
	public Player(GamePadTest game, int x, int y) {
		this.game = game;
		this.x = x;
		this.y = y;
	}
	
	public void update() {
		move();
	}
	
	public PlayState getState() {
		return (PlayState)game.stateMachine.getCurrentState();
	}
	
	void showID(Object o) {
		System.out.println(System.identityHashCode(o));
	}
	
	public void render(Graphics2D g) {
		g.setColor(color);
		g.fillRect((int)x, (int)y, w, h);
	}
		
	public void move() {
		if(up)
			y -= speed;
		else if(down)
			y += speed;
		else if(left)
			x -= speed;
		else if(right)
			x += speed;
	}
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_A)
			left = true;
		if(e.getKeyCode() == KeyEvent.VK_D)
			right = true;
		if(e.getKeyCode() == KeyEvent.VK_W)
			up = true;
		if(e.getKeyCode() == KeyEvent.VK_S)
			down = true;
	}
	
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_A)
			left = false;
		if(e.getKeyCode() == KeyEvent.VK_D)
			right = false;
		if(e.getKeyCode() == KeyEvent.VK_W)
			up = false;
		if(e.getKeyCode() == KeyEvent.VK_S)
			down = false;
	}
	
	public void joystickPoll(Component[] components) {
		
		for(Component c:components) {
			String componentName = c.getName();
			float pollData = c.getPollData();
			
			// SHOW EVERYTHING
			System.out.println("[" + componentName + "]---:---[" + pollData + "]");
			
			// Y Axis
			if(componentName.equals("Y Axis")) {
				if(pollData == -1.0)
					up = true;
				else if(pollData == 1.0)
					down = true;
				else {
					up 		= false;
					down 	= false;
				}
			}
			
			// X Axis
			if(componentName.equals("X Axis")) {
				if(pollData == -1.0)
					left = true;
				else if(pollData == 1.0)
					right = true;
				else {
					left 	= false;
					right = false;
				}
			}
			
		}
	}
}
