package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Random;

import tetromino.Mino;
import tetromino.Mino_I;
import tetromino.Mino_J;
import tetromino.Mino_L;
import tetromino.Mino_O;
import tetromino.Mino_S;
import tetromino.Mino_T;
import tetromino.Mino_Z;
import tetromino.Square;

public class PlayManager {

	final int WIDTH = 360;
	final int HEIGHT = 600;
	final int REMOVAL_EFFECT_FRAMES = 10;
    private final int X_DISTANCE_ARROW_MENU_SELECTION = 30;
	public static int left_x;
	public static int right_x;
	public static int top_y;
	public static int bottom_y;
	public static int dropInterval = 60;
	public static boolean isGameOver = false;
	int level = 1;
	int lines = 0;
	int currentScore = 0;
	private static int gameOverWarningPosY = 450;
    private int menuSelectionNumber;
	
	public static int highScore = -1;
	private Boolean isNewHighScore = false;

	boolean isRemovalEffectCounterOn;
	int removalEffectCounter;
	ArrayList<Integer> linesToRemove = new ArrayList<Integer>();
	
	Mino currentMino;
	Mino nextMino;

	private int minoICounter = 0;
	private int minoJCounter = 0;
	private int minoLCounter = 0;
	private int minoOCounter = 0;
	private int minoSCounter = 0;
	private int minoTCounter = 0;
	private int minoZCounter = 0;

	final int MINO_START_X;
	final int MINO_START_Y;

	final int NEXT_MINO_X;
	final int NEXT_MINO_Y;

	GamePanel gamePanel;

	public static ArrayList<Square> staticSquares = new ArrayList<>();
	
	public PlayManager(GamePanel gamePanel) {

		this.gamePanel = gamePanel;
		this.menuSelectionNumber = 0;

		left_x = (GamePanel.WIDTH / 2) - (WIDTH / 2);
		right_x = left_x + WIDTH;
		top_y = 75;
		bottom_y = top_y + HEIGHT;
		
		MINO_START_X = left_x + (WIDTH / 2) - Square.SIZE;
		MINO_START_Y = top_y + Square.SIZE;

		NEXT_MINO_X = right_x + 175;
		NEXT_MINO_Y = top_y + 500;
		
		currentMino = pickRandomMino();
		this.getCurrentMinoCounter(currentMino);
		nextMino = pickRandomMino();

		currentMino.setXY(MINO_START_X, MINO_START_Y);
		nextMino.setXY(NEXT_MINO_X, NEXT_MINO_Y);
	}
	
	public void update() {

		if(this.gamePanel.gameState != this.gamePanel.playState) return;
		
		if(!currentMino.isMinoActive) {
			for(int i = 0; i < 4; i++) {
				staticSquares.add(currentMino.squares[i]);
			}

			if(currentMino.squares[0].x == MINO_START_X && currentMino.squares[0].y == MINO_START_Y) {
				isGameOver = true;
				GamePanel.soundEffect.play(2, false);
			}

			currentMino.isDeactivating = false;

			currentMino = nextMino;
			this.getCurrentMinoCounter(currentMino);
			nextMino = this.pickRandomMino();

			currentMino.setXY(MINO_START_X, MINO_START_Y);
			nextMino.setXY(NEXT_MINO_X, NEXT_MINO_Y);

			this.validateCompletedLines();
		}
		else currentMino.update();
	}
	
	public void draw(Graphics2D graphics) {
		
		this.drawMainGameBox(graphics);
		this.drawNextTetrominoBox(graphics);
		this.drawHighscoreBox(graphics);
		this.drawTetrominosCountingBox(graphics);

		if(currentMino != null) {
			currentMino.draw(graphics);
		}
		
		if(nextMino != null) {
			nextMino.draw(graphics);
		}
		
		for(int i = 0; i < staticSquares.size(); i++) {
			staticSquares.get(i).draw(graphics);
		}

		if(isRemovalEffectCounterOn) {
			this.drawSquareRemovalEffect(graphics);
		}
	
		if(isGameOver) {
			this.checkHighScore();
			this.drawGameOverWarning(graphics);
			GamePanel.music.stop();
		}

		if(KeyHandler.pausePressed) this.drawPauseWarning(graphics);
	}

	private void drawMainGameBox(Graphics2D graphics) {
		graphics.setColor(Color.white);
		graphics.setStroke(new BasicStroke(4f));
		graphics.drawRect(left_x - 4, top_y - 4, WIDTH + 8, HEIGHT + 8);
	}
	
	private void drawNextTetrominoBox(Graphics2D graphics) {
		int x = right_x + 100;
		int y = bottom_y - 200;
		graphics.drawRect(x, y, 200, 200);
		graphics.setFont(new Font("Arial", Font.PLAIN, 30));
		graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		graphics.drawString("NEXT", x + 60, y + 60);
	}

	private Mino pickRandomMino() {

		Mino mino = null;

		int i = new Random().nextInt(7);

		switch(i){
			case 0: mino = new Mino_I(null);break;
			case 1: mino = new Mino_J(null);break;
			case 2: mino = new Mino_L(null);break;
			case 3: mino = new Mino_O(null);break;
			case 4: mino = new Mino_S(null);break;
			case 5: mino = new Mino_T(null);break;
			case 6: mino = new Mino_Z(null);break;
		}

		return mino;
	}

	private void drawPauseWarning(Graphics2D graphics) {
		graphics.setColor(Color.yellow);
		graphics.setFont(graphics.getFont().deriveFont(50f));
		String warningText= "PAUSED";
		graphics.drawString(warningText, 930, top_y + 320);
	}

	private void validateCompletedLines() {
		int x = left_x;
		int y = top_y;
		int blockCount = 0;
		int lineCount = 0;

		while(x < right_x && y < bottom_y) {

			for(int i = 0; i < staticSquares.size(); i++) {
				if(staticSquares.get(i).x == x && staticSquares.get(i).y == y) blockCount++;
			}

			x += Square.SIZE;

			if(x == right_x) {

				if(blockCount == 12) {
					isRemovalEffectCounterOn =  true;
					linesToRemove.add(y);
					this.removeStaticSquares(y);	
					lineCount++;
					lines++;
					this.increaseDifficulty();
				}

				blockCount = 0;
				x = left_x;
				y += Square.SIZE;
			}
		}

		if(lineCount > 0) {
			int singleLineScore = 10 * level;
			currentScore += singleLineScore * lineCount;
		}
	}

	private void increaseDifficulty() {
		if(lines % 10 == 0 && dropInterval > 1) {
			level++;
			if(dropInterval > 10) dropInterval -= 10;
			else dropInterval -= 1;
		}
	}

	private void removeStaticSquares(int y) {
		for(int i = staticSquares.size() - 1; i > -1; i--) {
			if(staticSquares.get(i).y == y) {
				staticSquares.remove(i);
			}
		}

		for(int i = 0; i < staticSquares.size(); i++) {
			if(staticSquares.get(i).y < y) {
				staticSquares.get(i).y += Square.SIZE;
			}
		}
	}

	private void drawSquareRemovalEffect(Graphics2D graphics) {

		removalEffectCounter++;

		graphics.setColor(Color.WHITE);
		for(int i = 0; i < linesToRemove.size(); i++) {
			for(int j = 0; j < 12; j++) {
				graphics.fillRect(left_x + (j * Square.SIZE), linesToRemove.get(i), Square.SIZE - 2, Square.SIZE);
			}
		}

		if(removalEffectCounter == REMOVAL_EFFECT_FRAMES) {
			isRemovalEffectCounterOn = false;
			removalEffectCounter = 0;
			linesToRemove.clear();
		}
	}
	
	private void checkHighScore() {
		this.isNewHighScore = true;
		if(currentScore <= highScore) {
			this.isNewHighScore = false;
			this.menuSelectionNumber = 1;
		}
	}

	private void drawGameOverWarning(Graphics2D graphics) {
		graphics.setColor( new Color(255,255,255,50) );
		graphics.fillRect(left_x - 4, top_y - 4, WIDTH + 8, HEIGHT + 8);
		graphics.setColor(Color.yellow);
		graphics.setFont(graphics.getFont().deriveFont(50f));
		graphics.drawString("GAME OVER", left_x + 30, gameOverWarningPosY);
		if(gameOverWarningPosY != top_y + HEIGHT) gameOverWarningPosY++;
		this.drawMenu(graphics);
	}
	
	private void drawHighscoreBox(Graphics2D graphics) {
		int x = right_x + 100;
		int y = bottom_y - 200;
		graphics.drawRect(x, top_y, 200, 200);

		graphics.setFont(new Font("Arial", Font.PLAIN, 30));
		graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		x += 20;
		y = top_y + 35;
		graphics.drawString("LEVEL: " + level, x, y);
		y += 50;
		graphics.drawString("LINES: " + lines, x, y);
		y += 50;
		graphics.drawString("TOP: " + 0000, x, y);
		y += 50;
		graphics.drawString("SCORE: " + currentScore, x, y);
	}

	public void drawMenu(Graphics2D graphics) {

		graphics.setColor(Color.WHITE);
    	graphics.setFont(graphics.getFont().deriveFont(Font.BOLD, 36F));

        String text = "SET SCORE";
        int x = getXForCenteredText(graphics, text);
        int y = GamePanel.HEIGHT * 1 / 3;
        if(this.isNewHighScore) {
        	graphics.drawString(text, x, y);
        	if(this.menuSelectionNumber == 0) {
        		graphics.drawString(">", x - this.X_DISTANCE_ARROW_MENU_SELECTION, y);
        	}
        }

        text = "MAIN MENU";
    	x = getXForCenteredText(graphics, text);
        y += 50;
        graphics.drawString(text, x, y);
        if(this.menuSelectionNumber == 1) {
            graphics.drawString(">", x - this.X_DISTANCE_ARROW_MENU_SELECTION, y);
        }
    }
	
	public int getXForCenteredText(Graphics2D graphics, String text) {
        int length = (int) graphics.getFontMetrics().getStringBounds(text, graphics).getWidth();
        return GamePanel.WIDTH / 2 - length / 2;
    }
	
	public int getMenuSelectionNumber() {
		return this.menuSelectionNumber;
	}

	public void setMenuSelectionNumber(int modifier) {
        int result = this.menuSelectionNumber + modifier;

        if(result < 0) result = 1;

        if(result > 1) result = 0;
        
        if(!this.isNewHighScore) result = 1;

		this.menuSelectionNumber = result;
	}


	private void drawTetrominosCountingBox(Graphics2D graphics) {

		graphics.drawRect(left_x - 300, bottom_y - 575, 200, 575);

		int counterX = left_x - 250;
		int counterY = bottom_y - 50;
		int ySpreader = 5;
		
		graphics.setFont(new Font("Arial", Font.PLAIN, 30));
		graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		Mino mino = new Mino_I(Color.GRAY);
		mino.setXY(counterX, counterY);mino.draw(graphics);
		graphics.drawString(String.valueOf(minoICounter), counterX + 100, counterY + 25);

		mino = new Mino_T(Color.GRAY);
		counterY -= (Square.SIZE) + ySpreader;
		mino.setXY(counterX, counterY);mino.draw(graphics);
		graphics.drawString(String.valueOf(minoTCounter), counterX + 100, counterY);

		mino = new Mino_J(Color.GRAY);
		counterY -= (Square.SIZE * 3) + ySpreader;
		mino.setXY(counterX, counterY);mino.draw(graphics);
		graphics.drawString(String.valueOf(minoJCounter), counterX + 100, counterY + 25);

		mino = new Mino_L(Color.GRAY);
		counterY -= (Square.SIZE * 3) + ySpreader;
		mino.setXY(counterX, counterY);mino.draw(graphics);
		graphics.drawString(String.valueOf(minoLCounter), counterX + 100, counterY + 25);

		mino = new Mino_O(Color.GRAY);
		counterY -= (Square.SIZE * 3) + ySpreader;
		mino.setXY(counterX, counterY);mino.draw(graphics);
		graphics.drawString(String.valueOf(minoOCounter), counterX + 100, counterY + 25);

		mino = new Mino_S(Color.GRAY);
		counterY -= (Square.SIZE * 2) + ySpreader;
		mino.setXY(counterX, counterY);mino.draw(graphics);
		graphics.drawString(String.valueOf(minoSCounter), counterX + 100, counterY + 25);

		mino = new Mino_Z(Color.GRAY);
		counterY -= (Square.SIZE * 3) + ySpreader;
		mino.setXY(counterX, counterY);mino.draw(graphics);
		graphics.drawString(String.valueOf(minoZCounter), counterX + 100, counterY + 25);
	}

	private void getCurrentMinoCounter(Mino currentMino) {
		String className = currentMino.getClass().getName();
		
		if(className.contains("_I")) minoICounter++;
		if(className.contains("_J")) minoJCounter++;
		if(className.contains("_L")) minoLCounter++;
		if(className.contains("_S")) minoSCounter++;
		if(className.contains("_Z")) minoZCounter++;
		if(className.contains("_T")) minoTCounter++;
		if(className.contains("_O")) minoOCounter++;
	}
}
