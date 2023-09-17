package HEC_Industries;

import EngineX.GameObject;
import HEC_Industries.structures.CoalDrill;
import HEC_Industries.structures.Structure;
import com.studiohartman.jamepad.ControllerState;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Player extends GameObject {
	Game  game;
	float offsetX;
	float offsetY;
	public float x;
	public float y;
	int   w     = Block.blockWidth - 10;
	int   h     = Block.blockHeight - 10;
	float speed = 4.85f;

	//	float jumpSpeed = 6.67f;
	//	float jumpSpeed = 6.7f;
	float jumpSpeed = 6.75f;

	float velocityX = 0;
	float velocityY = 0;
	float friction  = 0.5f;

	float   gravity         = 0.35f;
	float   maxGravity      = 12f;
	boolean gravityEnabled  = true;
	boolean infiniteJumping = false;

	int initialPlaceCoolDown = 4;
	int placeCooldown        = 0;

	int initialDigCoolDown = 15;
	int digCooldown        = 0;

	ArrayList<Chunk> levelChunks;

	static int CURSOR_UP    = 0;
	static int CURSOR_DOWN  = 1;
	static int CURSOR_LEFT  = 2;
	static int CURSOR_RIGHT = 3;
	EmptyObject playerCursor;
	int         playerCursorDirection = Player.CURSOR_RIGHT;
	boolean     renderPlayerCursor    = false;
	boolean     continuousInput       = false;

	public EmptyObject placingCursor;
	boolean   renderPlacingCursor = false;
	Structure selectedStructure   = null;

	Boolean moveUp    = false;
	Boolean moveDown  = false;
	Boolean moveLeft  = false;
	Boolean moveRight = false;

	Boolean aimUp    = false;
	Boolean aimDown  = false;
	Boolean aimLeft  = false;
	Boolean aimRight = false;

	// Keyboard
	boolean w_key     = false;
	boolean s_key     = false;
	boolean d_key     = false;
	boolean a_key     = false;
	boolean up_key    = false;
	boolean down_key  = false;
	boolean left_key  = false;
	boolean right_key = false;
	boolean shift_key = false, shift_key_just_pressed = false;
	boolean ctrl_key = false, ctrl_key_just_pressed = false;
	boolean alt_key = false, alt_key_just_pressed = false;
	boolean space_key = false, space_key_just_pressed = false;
	boolean r_key = false, r_key_just_pressed = false;
	boolean f1_key = false, f1_key_just_pressed = false;
	boolean f2_key = false, f2_key_just_pressed = false;
	boolean f3_key = false, f3_key_just_pressed = false;
	boolean f4_key = false, f4_key_just_pressed = false;
	boolean f5_key = false, f5_key_just_pressed = false;
	boolean f6_key = false, f6_key_just_pressed = false;
	boolean f7_key = false, f7_key_just_pressed = false;
	boolean f8_key = false, f8_key_just_pressed = false;
	boolean f9_key = false, f9_key_just_pressed = false;
	boolean f10_key = false, f10_key_just_pressed = false;
	boolean f11_key = false, f11_key_just_pressed = false;
	boolean f12_key = false, f12_key_just_pressed = false;

	// Controller
	ControllerState controller;
	boolean         start_btn         = false;
	boolean         back_btn          = false;
	boolean         dpadUp_btn        = false;
	boolean         dpadDown_btn      = false;
	boolean         dpadLeft_btn      = false;
	boolean         dpadRight_btn     = false;
	boolean         a_btn             = false;
	boolean         b_btn             = false;
	boolean         x_btn             = false;
	boolean         y_btn             = false;
	boolean         lb_btn            = false;
	boolean         leftTrigger_btn   = false;
	boolean         rb_btn            = false;
	boolean         rightTrigger_btn  = false;
	boolean         leftStick_btn     = false; // left stick
	boolean         leftStickY_up     = false;
	boolean         leftStickY_down   = false;
	boolean         leftStickX_left   = false;
	boolean         leftStickX_right  = false;
	boolean         rightStick_btn    = false; // right stick
	boolean         rightStickY_up    = false;
	boolean         rightStickY_down  = false;
	boolean         rightStickX_left  = false;
	boolean         rightStickX_right = false;

	// Controller Just Pressed
	boolean start_btn_just_pressed      = false;
	boolean back_btn_just_pressed       = false;
	boolean dpadUp_btn_just_pressed     = false;
	boolean dpadDown_btn_just_pressed   = false;
	boolean dpadLeft_btn_just_pressed   = false;
	boolean dpadRight_btn_just_pressed  = false;
	boolean a_btn_just_pressed          = false;
	boolean b_btn_just_pressed          = false;
	boolean x_btn_just_pressed          = false;
	boolean y_btn_just_pressed          = false;
	boolean lb_btn_just_pressed         = false;
	boolean rb_btn_just_pressed         = false;
	boolean leftStick_btn_just_pressed  = false; // left stick
	boolean rightStick_btn_just_pressed = false; // right stick

	// SelectedItem
	int DIG            = 0;
	int DIRT           = 1;
	int COALDRILL      = 2;
	int selectedAction = DIG;

	public Player(Game game, int offsetX, int offsetY) {
		super(game);
		this.game = game;

		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.x = game.ps.levelHandler.startLocation.x;
		this.y = game.ps.levelHandler.startLocation.y;

		this.levelChunks = game.ps.levelHandler.levelChunks;

		// Setup Player Cursor
		playerCursor = new EmptyObject(game, (int)(x + w + w / 2), (int)(y + h / 2), 1, 1);

		// Setup Placing Cursor
		placingCursor = new EmptyObject(game, (int)x, (int)y, Block.blockWidth, Block.blockHeight);
	}

	void resetPlayer() {
		game.ps.initialized = false;
	}

	boolean blockIsNearPlayerCursor(Block block) {
		// This works.. but its fucking ridiculous.. its like using the reverse of a solution.. how the fuck I came up with this, only god knows..
		return !(block.x < (playerCursor.x - Block.blockWidth) || block.x > (playerCursor.x + Block.blockWidth) || block.y < (playerCursor.y - Block.blockHeight) || block.y > (playerCursor.y + Block.blockHeight));
	}

	void gravity() {
		if(gravityEnabled) {
			boolean collision = false;
			for(Chunk chunk : levelChunks) {
				if(chunk.y < this.y - chunk.getHeight())
					continue;
				for(Quadrant quad : chunk.quadCollection) {
					if(quad.y < this.y - quad.getHeight())
						continue;
					for(Block block : quad.blocks) {
						if(!(block.y > this.y))
							continue;
						if((block.blockType == Block.TYPE_DIRT || block.blockType == Block.TYPE_IRON || block.blockType == Block.TYPE_COPPER || block.blockType == Block.TYPE_COAL) && block.isHidden) {
							if(Phys.collision(new Rectangle((int)this.x, (int)(this.y + this.h), this.w, 1), new Rectangle((int)block.x, (int)block.y, (int)block.w, (int)block.h))) {
								collision = true;
								break;
							}
						}
					}
					if(collision)
						break;
				}
				if(collision)
					break;
			}

			if(!collision)
				velocityY += gravity;
			if(velocityY > maxGravity)
				velocityY = maxGravity;
			if(!onGround())
				this.y += velocityY;
		}
	}

	void move() {
		if(!gravityEnabled) {
			if(moveUp) {
				if(velocityY > -speed)
					velocityY -= speed;
				if(velocityY < -speed)
					velocityY = -speed;
			}
			if(moveDown) {
				if(velocityY < speed)
					velocityY += speed;
				if(velocityY > speed)
					velocityY = speed;
			}

			if(moveUp && moveDown)
				velocityY = 0;
		}

		if(moveUp) {
			playerCursorDirection = Player.CURSOR_UP;
		}
		if(moveDown) {
			playerCursorDirection = Player.CURSOR_DOWN;
		}

		if(moveLeft) {
			if(velocityX > -speed)
				velocityX -= speed;
			if(velocityX < -speed)
				velocityX = -speed;

			playerCursorDirection = Player.CURSOR_LEFT;
		}

		if(moveRight) {
			if(velocityX < speed)
				velocityX += speed;
			if(velocityX > speed)
				velocityX = speed;

			playerCursorDirection = Player.CURSOR_RIGHT;
		}

		// Update Player Aim Cursor
		if(aimUp)
			playerCursorDirection = Player.CURSOR_UP;
		if(aimDown)
			playerCursorDirection = Player.CURSOR_DOWN;
		if(aimLeft)
			playerCursorDirection = Player.CURSOR_LEFT;
		if(aimRight)
			playerCursorDirection = Player.CURSOR_RIGHT;

		if(moveLeft && moveRight)
			velocityX = 0;
	}

	void updateVX() {
		float   difference = 0;
		boolean collision  = false;
		for(Chunk chunk : levelChunks) {
			for(Quadrant quad : chunk.quadCollection) {
				for(Block block : quad.blocks) {
					if((block.blockType == Block.TYPE_DIRT || block.blockType == Block.TYPE_IRON || block.blockType == Block.TYPE_COPPER || block.blockType == Block.TYPE_COAL) && block.isHidden) {
						Rectangle a = new Rectangle((int)(this.x + velocityX), (int)this.y, this.w, this.h);
						Rectangle b = new Rectangle((int)block.x, (int)block.y, (int)block.w, (int)block.h);
						if(Phys.collision(a, b)) {
							collision = true;
							if(velocityX < 0)
								difference = (float)(block.x + block.w) - (this.x + velocityX);
							if(velocityX > 0)
								difference = (this.x + this.w + velocityX) - (float)block.x;
							break;
						}
					}
				}
			}
		}

		if(collision) {
			if(velocityX < 0)
				this.x += (velocityX + difference);
			if(velocityX > 0)
				this.x += (velocityX - difference);
			velocityX = 0;
		}
		else {
			this.x += velocityX;
		}

		if(!moveLeft && !moveRight) {
			if(velocityX < 0) {
				velocityX += friction;
				if(velocityX > 0)
					velocityX = 0;
			}
			if(velocityX > 0) {
				velocityX -= friction;
				if(velocityX < 0)
					velocityX = 0;
			}
		}
	}

	void updateVY() {
		float   difference = 0;
		boolean collision  = false;
		for(Chunk chunk : levelChunks) {
			for(Quadrant quad : chunk.quadCollection) {
				for(Block block : quad.blocks) {
					if((block.blockType == Block.TYPE_DIRT || block.blockType == Block.TYPE_IRON || block.blockType == Block.TYPE_COPPER || block.blockType == Block.TYPE_COAL) && block.isHidden) {
						Rectangle a = new Rectangle((int)this.x, (int)(this.y + velocityY), this.w, this.h);
						Rectangle b = new Rectangle((int)block.x, (int)block.y, (int)block.w, (int)block.h);
						if(Phys.collision(a, b)) {
							collision = true;
							if(velocityY < 0)
								difference = (float)(block.y + block.h) - (this.y + velocityY);
							if(velocityY > 0)
								difference = (this.y + this.h + velocityY) - (float)block.y;
							break;
						}
					}
				}
			}
		}

		if(collision) {
			if(velocityY < 0)
				this.y += (velocityY + difference);
			if(velocityY > 0)
				this.y += (velocityY - difference);
			velocityY = 0;
		}
		else {
			this.y += velocityY;
		}

		if(!gravityEnabled) {
			if(!moveUp && !moveDown) {
				if(velocityY < 0) {
					velocityY += friction;
					if(velocityY > 0)
						velocityY = 0;
				}
				if(velocityY > 0) {
					velocityY -= friction;
					if(velocityY < 0)
						velocityY = 0;
				}
			}
		}
	}

	boolean onGround() {
		for(Chunk chunk : levelChunks) {
			for(Quadrant quad : chunk.quadCollection) {
				for(Block block : quad.blocks) {
					if((block.blockType == Block.TYPE_DIRT || block.blockType == Block.TYPE_IRON || block.blockType == Block.TYPE_COPPER || block.blockType == Block.TYPE_COAL) && block.isHidden) {
						Rectangle a = new Rectangle((int)this.x, (int)(this.y + this.h), this.w, 1);
						Rectangle b = new Rectangle((int)block.x, (int)block.y, (int)block.w, (int)block.h);
						if(Phys.collision(a, b)) {
							return true;
						}
					}
				}
			}
		}

		return false;
	}

	void jump() {
		if(infiniteJumping) {
			if(gravityEnabled)
				velocityY = -jumpSpeed;
		}
		else {
			if(onGround())
				velocityY = -jumpSpeed;
		}
	}

	void performAction() {
		if(selectedAction == DIG) {
			if(dig()) {
				System.out.println("Dig!");
			}
			else {
				System.out.println("Couldn't Dig!");
			}
		}
		if(selectedAction == DIRT) {
			if(place()) {
				System.out.println("Placed Dirt!");
			}
			else {
				System.out.println("Couldn't Place Dirt!");
			}
		}
		if(selectedAction == COALDRILL) {
			if(place()) {
				System.out.println("Placed Coal Drill!");
			}
			else {
				System.out.println("Couldn't Coal Drill!");
			}
		}
	}

	void use() {
		System.out.println("Use!");
	}

	boolean dig() {
		boolean dig = false;
		if(digCooldown == 0) {
			for(Chunk chunk : levelChunks) {
				if(chunk.containsEntity(playerCursor.x, playerCursor.y, playerCursor.w, playerCursor.h)) {
					// Get Quadrant
					for(Quadrant quad : chunk.quadCollection) {
						if(quad.containsEntity(playerCursor.x, playerCursor.y, playerCursor.w, playerCursor.h)) {
							for(Block block : quad.blocks) {
								// Only entertain blocks in close proximity and with condition greater than zero
								if(!blockIsNearPlayerCursor(block) || block.condition <= 0)
									continue;

								Rectangle a = new Rectangle(playerCursor.x, playerCursor.y, playerCursor.w, playerCursor.h);
								Rectangle b = new Rectangle((int)block.x, (int)block.y, (int)block.w, (int)block.h);
								if(Phys.collision(a, b)) {
									if(block.condition > 0)
										block.condition -= 32;

									if(block.condition <= 0) {
										block.isHidden = false;
										block.condition = 0;
									}

									dig = true;

									break;
								}
							}
							break;
						}
					}
					break;
				}
			}
			digCooldown = initialDigCoolDown;
		}
		return dig;
	}

	void updateSelectedAction() {
		// Increment..
		selectedAction++;

		// Check Min Max.. Min = Dirt, Max = COALDRILL
		if(selectedAction > COALDRILL)
			selectedAction = DIG;

		// Update Selected Action
		if(selectedAction == DIG) {
			renderPlacingCursor = false;
			selectedStructure = null;
			System.out.println("Selected Dig!");
		}
		if(selectedAction == DIRT) {
			renderPlacingCursor = false;
			selectedStructure = null;
			System.out.println("Selected Dirt!");
		}
		if(selectedAction == COALDRILL) {
			renderPlacingCursor = true;
			selectedStructure = new CoalDrill(game, placingCursor.x, placingCursor.y);
			System.out.println("Selected Coal Drill!");
		}
	}

	void updateSelectedStructure() {
		if(selectedStructure != null) {
			selectedStructure.update();
			selectedStructure.canPlace = canPlaceStructure();
		}
	}

	void updatePlayerCursor() {
		if(playerCursorDirection == Player.CURSOR_UP) {
			playerCursor.x = (int)x + w / 2;
			playerCursor.y = (int)y - h / 2;
		}
		if(playerCursorDirection == Player.CURSOR_DOWN) {
			playerCursor.x = (int)x + w / 2;
			playerCursor.y = (int)y + h + h / 2;
		}
		if(playerCursorDirection == Player.CURSOR_LEFT) {
			playerCursor.x = (int)x - w / 2;
			playerCursor.y = (int)y + h / 2;
		}
		if(playerCursorDirection == Player.CURSOR_RIGHT) {
			playerCursor.x = (int)x + w + w / 2;
			playerCursor.y = (int)y + h / 2;
		}
	}

	void updatePlacingCursor() {
		for(Chunk chunk : levelChunks) {
			if(chunk.containsEntity(playerCursor.x, playerCursor.y, playerCursor.w, playerCursor.h)) {
				for(Quadrant quad : chunk.quadCollection) {
					if(quad.containsEntity(playerCursor.x, playerCursor.y, playerCursor.w, playerCursor.h)) {
						for(Block block : quad.blocks) {
							if(block.selected) {
								placingCursor.x = (int)block.x;
								placingCursor.offsetX = block.offsetX;
								placingCursor.y = (int)block.y + Block.blockHeight;
								placingCursor.offsetY = block.offsetY + Block.blockHeight;
								break;
							}
						}
						break;
					}
				}
				break;
			}
		}
	}

	boolean place() {
		boolean place = false;

		if(selectedAction == DIRT && canPlaceBlock()) {
			if(placeCooldown == 0) {
				for(Chunk chunk : levelChunks) {
					if(chunk.containsEntity(playerCursor.x, playerCursor.y, playerCursor.w, playerCursor.h)) {
						// Get Quadrant
						for(Quadrant quad : chunk.quadCollection) {
							if(quad.containsEntity(playerCursor.x, playerCursor.y, playerCursor.w, playerCursor.h)) {
								for(Block block : quad.blocks) {
									// Only entertain blocks in close proximity
									if(!blockIsNearPlayerCursor(block))
										continue;

									Rectangle a = new Rectangle(playerCursor.x, playerCursor.y, playerCursor.w, playerCursor.h);
									Rectangle b = new Rectangle((int)block.x, (int)block.y, (int)block.w, (int)block.h);
									if(Phys.collision(a, b)) {
										a = new Rectangle((int)(x), (int)y, w, h);
										b = new Rectangle((int)block.x, (int)block.y, (int)block.w, (int)block.h);
										if(!Phys.collision(a, b)) {
											if(block.blockType == Block.TYPE_EMPTY) {
												block.blockType = Block.TYPE_DIRT;
											}
											block.condition = 100;
											block.isHidden = true;
											place = true;
										}
										break;
									}
								}
								break;
							}
						}
						break;
					}
				}
				placeCooldown = initialPlaceCoolDown;
			}
		}

		if(selectedAction == COALDRILL && canPlaceStructure()) {
			Structure structure = new CoalDrill(game, placingCursor.offsetX, placingCursor.offsetY);
			structure.state = Structure.STATE_PLACED;
			game.ps.levelHandler.levelStructures.add(structure);
			place = true;
		}
		return place;
	}

	public boolean canPlaceBlock() {
		boolean canPlaceBlock = true;

		// Check Blocks
		for(Chunk chunk : levelChunks) {
			for(Quadrant quad : chunk.quadCollection) {
				for(Block block : quad.blocks) {
					Rectangle a = new Rectangle(playerCursor.x, playerCursor.y, playerCursor.w, playerCursor.h);
					Rectangle b = new Rectangle((int)block.x, (int)block.y, (int)block.w, (int)block.h);
					if(Phys.collision(a, b)) {
						if(block.condition > 0) {
							canPlaceBlock = false;
							break;
						}
					}
				}
				if(canPlaceBlock == false)
					break;
			}
			if(canPlaceBlock == false)
				break;
		}

		return canPlaceBlock;
	}

	public boolean canPlaceStructure() {
		boolean canPlaceStructure = true;

		// Check Blocks
		for(Chunk chunk : levelChunks) {
			for(Quadrant quad : chunk.quadCollection) {
				for(Block block : quad.blocks) {
					Rectangle a = new Rectangle(selectedStructure.x, selectedStructure.y, selectedStructure.w, selectedStructure.h);
					Rectangle b = new Rectangle((int)block.x, (int)block.y, (int)block.w, (int)block.h);
					if(Phys.collision(a, b)) {
						if(block.condition > 0) {
							canPlaceStructure = false;
							break;
						}
					}
				}
				if(!canPlaceStructure)
					break;
			}
			if(!canPlaceStructure)
				break;
		}

		// Check Structures
		for(Structure structure : game.ps.levelHandler.levelStructures) {
			Rectangle a = new Rectangle(selectedStructure.x, selectedStructure.y, selectedStructure.w, selectedStructure.h);
			Rectangle b = new Rectangle(structure.x, structure.y, structure.w, structure.h);
			if(Phys.collision(a, b)) {
				canPlaceStructure = false;
				break;
			}
		}

		return canPlaceStructure;
	}

	public void update() {
		if(Config.controllerEnabled)
			checkInputState();
		resetJustPressedKeys();

		gravity();
		move();

		updateVY();
		updateVX();

		updatePlayerCursor();
		if(placeCooldown > 0)
			placeCooldown--;
		if(digCooldown > 0)
			digCooldown--;

		updatePlacingCursor();
		updateSelectedStructure();
	}

	public void render(Graphics2D g) {
		// Cursor
		if(renderPlayerCursor) {
			g.setColor(Color.white);
			g.drawRect(playerCursor.x, playerCursor.y, playerCursor.w, playerCursor.h);
		}

		if(selectedStructure != null) {
			selectedStructure.render(g);
		}

		// Player
		g.setColor(Color.red);
		g.fillRect((int)x, (int)y, w, h);
	}

	public void checkInputState() {
		moveUp = (dpadUp_btn || leftStickY_up || w_key);
		moveDown = (dpadDown_btn || leftStickY_down || s_key);
		moveLeft = (dpadLeft_btn || leftStickX_left || a_key);
		moveRight = (dpadRight_btn || leftStickX_right || d_key);

		aimUp = rightStickY_up;
		aimDown = rightStickY_down;
		aimLeft = rightStickX_left;
		aimRight = rightStickX_right;

		if(back_btn_just_pressed) {
			updateSelectedAction();
		}

		if(a_btn_just_pressed || space_key_just_pressed)
			jump();
		if(y_btn_just_pressed || r_key_just_pressed)
			resetPlayer();

		if(b_btn_just_pressed)
			use();
		if(lb_btn_just_pressed)
			use();

		if(continuousInput) {
			if(x_btn)
				performAction();
			if(rb_btn)
				performAction();
		}
		else {
			if(x_btn_just_pressed)
				performAction();
			if(rb_btn_just_pressed)
				performAction();
		}


		if(leftStick_btn_just_pressed)
			jump();
		if(rightStick_btn_just_pressed)
			jump();

		if(leftTrigger_btn) {}
		if(rightTrigger_btn) {}

		if(f1_key_just_pressed) {
			gravityEnabled = !gravityEnabled;

			if(!gravityEnabled) {
				velocityX = 0;
				velocityY = 0;
			}
		}
		if(f2_key_just_pressed)
			infiniteJumping = !infiniteJumping;

		// Reset Keys Just Pressed
		resetJustPressedKeys();
	}

	public void resetJustPressedKeys() {
		shift_key_just_pressed = false;
		ctrl_key_just_pressed = false;
		alt_key_just_pressed = false;
		space_key_just_pressed = false;
		r_key_just_pressed = false;
		f1_key_just_pressed = false;
		f2_key_just_pressed = false;
		f3_key_just_pressed = false;
		f4_key_just_pressed = false;
		f5_key_just_pressed = false;
		f6_key_just_pressed = false;
		f7_key_just_pressed = false;
		f8_key_just_pressed = false;
		f9_key_just_pressed = false;
		f10_key_just_pressed = false;
		f11_key_just_pressed = false;
		f12_key_just_pressed = false;
	}

	public void controllerUpdate(ControllerState controller) {
		// Update Controller State
		this.controller = controller;

		// Start, Back
		this.start_btn = (controller.start);
		this.back_btn = (controller.back);
		this.start_btn_just_pressed = (controller.startJustPressed);
		this.back_btn_just_pressed = (controller.backJustPressed);

		// DPad
		this.dpadUp_btn = (controller.dpadUp);
		this.dpadDown_btn = (controller.dpadDown);
		this.dpadLeft_btn = (controller.dpadLeft);
		this.dpadRight_btn = (controller.dpadRight);
		this.dpadUp_btn_just_pressed = (controller.dpadUpJustPressed);
		this.dpadDown_btn_just_pressed = (controller.dpadDownJustPressed);
		this.dpadLeft_btn_just_pressed = (controller.dpadLeftJustPressed);
		this.dpadRight_btn_just_pressed = (controller.dpadRightJustPressed);

		// A, B, X, Y
		this.a_btn = (controller.a);
		this.b_btn = (controller.b);
		this.x_btn = (controller.x);
		this.y_btn = (controller.y);
		this.a_btn_just_pressed = controller.aJustPressed;
		this.b_btn_just_pressed = controller.bJustPressed;
		this.x_btn_just_pressed = controller.xJustPressed;
		this.y_btn_just_pressed = controller.yJustPressed;

		// Left Button, Left Trigger
		this.lb_btn = (controller.lb);
		this.leftTrigger_btn = (controller.leftTrigger > 0.5);
		this.lb_btn_just_pressed = (controller.lbJustPressed);

		// Right Button, Right Trigger
		this.rb_btn = (controller.rb);
		this.rightTrigger_btn = (controller.rightTrigger > 0.5);
		this.rb_btn_just_pressed = (controller.rbJustPressed);

		// Left Stick
		this.leftStick_btn = (controller.leftStickClick);
		this.leftStick_btn_just_pressed = (controller.leftStickJustClicked);
		this.leftStickY_up = (controller.leftStickY > 0.5);
		this.leftStickY_down = (controller.leftStickY < -0.5);
		this.leftStickX_left = (controller.leftStickX < -0.1);
		this.leftStickX_right = (controller.leftStickX > 0.1);

		// Right Stick
		this.rightStick_btn = (controller.rightStickClick);
		this.rightStick_btn_just_pressed = (controller.rightStickJustClicked);
		this.rightStickY_up = (controller.rightStickY > 0.5);
		this.rightStickY_down = (controller.rightStickY < -0.5);
		this.rightStickX_left = (controller.rightStickX < -0.5);
		this.rightStickX_right = (controller.rightStickX > 0.5);
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_W) {w_key = true;}
		if(e.getKeyCode() == KeyEvent.VK_S) {s_key = true;}
		if(e.getKeyCode() == KeyEvent.VK_A) {a_key = true;}
		if(e.getKeyCode() == KeyEvent.VK_D) {d_key = true;}

		if(e.getKeyCode() == KeyEvent.VK_SHIFT) {
			shift_key = true;
			shift_key_just_pressed = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_CONTROL) {
			ctrl_key = true;
			ctrl_key_just_pressed = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_ALT) {
			alt_key = true;
			alt_key_just_pressed = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			space_key = true;
			space_key_just_pressed = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_R) {
			r_key = true;
			r_key_just_pressed = true;
		}

		if(e.getKeyCode() == KeyEvent.VK_F1) {
			f1_key = true;
			f1_key_just_pressed = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_F2) {
			f2_key = true;
			f2_key_just_pressed = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_F3) {
			f3_key = true;
			f3_key_just_pressed = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_F4) {
			f4_key = true;
			f4_key_just_pressed = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_F5) {
			f5_key = true;
			f5_key_just_pressed = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_F6) {
			f6_key = true;
			f6_key_just_pressed = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_F7) {
			f7_key = true;
			f7_key_just_pressed = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_F8) {
			f8_key = true;
			f8_key_just_pressed = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_F9) {
			f9_key = true;
			f9_key_just_pressed = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_F10) {
			f10_key = true;
			f10_key_just_pressed = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_F11) {
			f11_key = true;
			f11_key_just_pressed = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_F12) {
			f12_key = true;
			f12_key_just_pressed = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_W) {w_key = false;}
		if(e.getKeyCode() == KeyEvent.VK_S) {s_key = false;}
		if(e.getKeyCode() == KeyEvent.VK_A) {a_key = false;}
		if(e.getKeyCode() == KeyEvent.VK_D) {d_key = false;}

		if(e.getKeyCode() == KeyEvent.VK_SHIFT) {shift_key = false;}
		if(e.getKeyCode() == KeyEvent.VK_CONTROL) {ctrl_key = false;}
		if(e.getKeyCode() == KeyEvent.VK_ALT) {alt_key = false;}
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {space_key = false;}

		if(e.getKeyCode() == KeyEvent.VK_R) {r_key = false;}

		if(e.getKeyCode() == KeyEvent.VK_F1) {f1_key = false;}
		if(e.getKeyCode() == KeyEvent.VK_F2) {f2_key = false;}
		if(e.getKeyCode() == KeyEvent.VK_F3) {f3_key = false;}
		if(e.getKeyCode() == KeyEvent.VK_F4) {f4_key = false;}
		if(e.getKeyCode() == KeyEvent.VK_F5) {f5_key = false;}
		if(e.getKeyCode() == KeyEvent.VK_F6) {f6_key = false;}
		if(e.getKeyCode() == KeyEvent.VK_F7) {f7_key = false;}
		if(e.getKeyCode() == KeyEvent.VK_F8) {f8_key = false;}
		if(e.getKeyCode() == KeyEvent.VK_F9) {f9_key = false;}
		if(e.getKeyCode() == KeyEvent.VK_F10) {f10_key = false;}
		if(e.getKeyCode() == KeyEvent.VK_F11) {f11_key = false;}
		if(e.getKeyCode() == KeyEvent.VK_F12) {f12_key = false;}
	}

	public void mousePressed(MouseEvent e) {
		//		whatever.mousePressed(e);
	}

	public void mouseReleased(MouseEvent e) {
		//		whatever.mousePressed(e);
	}
}
