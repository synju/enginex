package Complete.megamoney;

import EngineX.GameObject;

import javax.swing.*;
import java.awt.*;

public class Spinner extends GameObject {
	Game game;

	// Images
	Image spinningImage = new ImageIcon("res/megamoney/spinner_blurred.png").getImage();
	Image clearImage = new ImageIcon("res/megamoney/spinner_clear.png").getImage();
	Image leftArrowImage = new ImageIcon("res/megamoney/left_arrow.png").getImage();
	Image rightArrowImage = new ImageIcon("res/megamoney/right_arrow.png").getImage();
	Image topFadeImage = new ImageIcon("res/megamoney/top_fade.png").getImage();
	Image bottomFadeImage = new ImageIcon("res/megamoney/bottom_fade.png").getImage();

	// Location
	int x = 0;
	int y = 0;

	// Dimensions
	int w = 248;
	int h = 299;

	// Spinning Properties
	boolean spinning = false;
	int spinOffset = 0;
	int spinOffsetMax = 299;
	int spinSpeed = 30;

	// Spin CountDown Config
	int countDownMin = 30;
	int countDownMax = 60;
	int countdown = Util.generateRandom(countDownMin, countDownMax);

	// Position Config
	int position = 0;

	// ActionPanel CountDown Config
	int actionPanelDelay = 30;
	int actionPanelCountDown = 0;
	int actionPanelType = 0;

	// Sounds
	boolean spinSoundPlaying = false;

	public Spinner(Game game) {
		super(game);
		this.game = game;
	}

	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getWidth() {
		return this.w;
	}

	public int getHeight() {
		return this.h;
	}

	public void update() {
		if(spinning) {
			// Spin Time...
			countdown--;
			if(countdown <= 0) {
				spinning = false;
				countdown = Util.generateRandom(countDownMin, countDownMax);

				// Position Notification
				// --- Doodad
				if(position == 0) {
					// Disable Spin Button
					game.ps.p.spinButtonEnabled = false;

					// Doodad Sound
					if(Config.soundEnabled) {
						game.res.doodadSound.getSound().playSound();
					}

					// Generate Doodad
					startActionPanelCountdown(ActionPanel.DOODAD);
				}

				// --- Job
				if(position == 1) {
					// Disable Spin Button
					game.ps.p.spinButtonEnabled = false;

					// Job Sound
					if(Config.soundEnabled) {
						game.res.jobSound.getSound().playSound();
					}

					// Generate Job
					startActionPanelCountdown(ActionPanel.JOB);
				}

				// --- Gift
				if(position == 4) {
					// Disable Spin Button
					game.ps.p.spinButtonEnabled = false;

					// Gift Sound
					if(Config.soundEnabled) {
						game.res.giftSound.getSound().playSound();
					}

					// Generate Gift
					startActionPanelCountdown(ActionPanel.GIFT);
				}

				// --- Charity
				if(position == 3) {
					// Disable Spin Button
					game.ps.p.spinButtonEnabled = false;

					// Charity Sound
					if(Config.soundEnabled) {
						game.res.charitySound.getSound().playSound();
					}

					// Generate Charity
					startActionPanelCountdown(ActionPanel.CHARITY);
				}

				// --- Opportunity
				if(position == 2) {
					// Disable Spin Button
					game.ps.p.spinButtonEnabled = false;

					// Opportunity Sound
					if(Config.soundEnabled) {
						game.res.opportunitySound.getSound().playSound();
					}

					// Generate Opportunity
					startActionPanelCountdown(ActionPanel.OPPORTUNITY);
				}

				// Position Sound
				if(Config.soundEnabled) {
					if(position == 2)
						game.res.opportunitySound.getSound().playSound(); // Opportunity
				}

				// Spin Sound
				spinSoundPlaying = false;
				game.res.spinSound.getSound().stop();
			}
			else {
				// Stop Other Sounds
				game.res.doodadSound.getSound().stop(); // Doodad
				game.res.jobSound.getSound().stop(); // Job
				game.res.opportunitySound.getSound().stop(); // Opportunity
				game.res.charitySound.getSound().stop(); // Charity
				game.res.giftSound.getSound().stop(); // Gift
			}

			// Spinner Sound
			if(spinning && !spinSoundPlaying) {
				game.res.spinSound.getSound().stop();
				spinSoundPlaying = true;
				if(Config.soundEnabled) {
					game.res.spinSound.getSound().playSong();
				}
			}
		}

		// Action Panel Counter
		if(actionPanelCountDown > 0) {
			actionPanelCountDown--;
			if(actionPanelCountDown <= 0) {
				game.ps.p.generateActionPanel(actionPanelType);
				actionPanelType = 0;
			}
		}
	}

	public void startActionPanelCountdown(int actionPanelType) {
		actionPanelCountDown = actionPanelDelay;
		this.actionPanelType = actionPanelType;
	}

	public void render(Graphics2D g) {
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

		// Check if Spinning or Not !!!
		if(spinning) {
			// Render Spinning Images
			renderSpinningImages(g);
		}
		else {
			// Render Clear Image
			renderClearImage(g);
		}

		// Render Cover
		renderCover(g);

		// Render Arrows
		g.drawImage(leftArrowImage, x-13, y+133, null);
		g.drawImage(rightArrowImage, x+230, y+133, null);
	}

	public void renderClearImage(Graphics2D g) {
		int positionOffset = 57;
		g.drawImage(clearImage, x, (y+120)+(position*positionOffset), null);
		g.drawImage(clearImage, x, (y-171)+(position*positionOffset), null);
		g.drawImage(clearImage, x, (y-463)+(position*positionOffset), null);
	}

	public void renderSpinningImages(Graphics2D g) {
		g.drawImage(spinningImage, x, y + spinOffset, null);
		g.drawImage(spinningImage, x, (y - spinOffsetMax) + spinOffset, null);

		spinOffset += spinSpeed;
		if(spinOffset >= spinOffsetMax) {
			spinOffset = 0;
		}
	}

	public void renderCover(Graphics2D g) {
		// Covers
		g.setColor(Color.BLACK);

		// Top Bottom
		g.fillRect(0, 0, 800, 156);
		g.fillRect(0, 450, 800, 150);

		// Left Right
		g.fillRect(0, 0, 400 - 114, 600);
		g.fillRect(530, 0, 400 - 114, 600);

		// Fades
		g.drawImage(topFadeImage, x-5, y, null);
		g.drawImage(bottomFadeImage, x-5, y+getHeight()-36, null);
	}

	public void start() {
		spinning = true;
		this.position = generatePosition();
	}

	public void stop() {
		spinning = false;
	}

	public int generatePosition() {
		int position = 111;

		int landed = Util.generateRandom(1, 100);
		if(landed >= 1 && landed <= 14) 	position = 0; // Doodad 			14% of the time
		if(landed >= 15 && landed <= 42) 	position = 1; // Job 					28% of the time
		if(landed >= 43 && landed <= 70) 	position = 2; // Opportunity 	28% of the time
		if(landed >= 71 && landed <= 84) 	position = 3; // Charity			14% of the time
		if(landed >= 85 && landed <= 100) position = 4; // Gift					14% of the time

		// Skip Doodad if Available Money is Less than 1000
		if(position == 0 && game.ps.p.availableMoney < 1000) {
			System.out.println("Skipping Doodad");
			position = generatePosition();
		}

		// Skip Job if huntForJobs is false
		if(position == 1 && !game.ps.p.huntForJobs && !game.ps.p.canLandOnJob) {
			System.out.println("Skipping Job");
			position = generatePosition();
		}

		return position;
	}
}
