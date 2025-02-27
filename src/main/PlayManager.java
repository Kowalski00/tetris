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
	public static int left_x;
	public static int right_x;
	public static int top_y;
	public static int bottom_y;
	public static int dropInterval = 60;

	boolean isRemovalEffectCounterOn;
	int removalEffectCounter;
	ArrayList<Integer> linesToRemove = new ArrayList<Integer>();
	
	Mino currentMino;
	Mino nextMino;

	final int MINO_START_X;
	final int MINO_START_Y;

	final int NEXT_MINO_X;
	final int NEXT_MINO_Y;

	public static ArrayList<Square> staticSquares = new ArrayList<>();
	
	public PlayManager() {
		left_x = (GamePanel.WIDTH / 2) - (WIDTH / 2);
		right_x = left_x + WIDTH;
		top_y = 50;
		bottom_y = top_y + HEIGHT;
		
		MINO_START_X = left_x + (WIDTH / 2) - Square.SIZE;
		MINO_START_Y = top_y + Square.SIZE;

		NEXT_MINO_X = right_x + 175;
		NEXT_MINO_Y = top_y + 500;
		
		currentMino = pickRandomMino();
		nextMino = pickRandomMino();

		currentMino.setXY(MINO_START_X, MINO_START_Y);
		nextMino.setXY(NEXT_MINO_X, NEXT_MINO_Y);
	}
	
	public void update() {
		
		if(!currentMino.isMinoActive) {
			for(int i = 0; i < 4; i++) {
				staticSquares.add(currentMino.squares[i]);
			}

			if(currentMino.squares[0].x == MINO_START_X && currentMino.squares[0].y == MINO_START_Y) {
				isGameOver = true;
			}

			currentMino.isDeactivating = false;

			currentMino = nextMino;
			nextMino = pickRandomMino();

			currentMino.setXY(MINO_START_X, MINO_START_Y);
			nextMino.setXY(NEXT_MINO_X, NEXT_MINO_Y);

			validateCompletedLines();
		}
		else currentMino.update();
	}
	
	public void draw(Graphics2D graphics) {
		graphics.setColor(Color.white);
		graphics.setStroke(new BasicStroke(4f));
		graphics.drawRect(left_x - 4, top_y - 4, WIDTH + 8, HEIGHT + 8);
		drawNextTetrominoBox(graphics);

		if(currentMino != null) {
			currentMino.draw(graphics);
		}
		
		if(nextMino != null) {
			nextMino.draw(graphics);
		}
		
		for(int i = 0; i < staticSquares.size(); i++) {
			staticSquares.get(i).draw(graphics);
		}

		if(isRemovalEffectCounterOn) drawSquareRemovalEffect(graphics);

		if(isGameOver) drawGameOverWarning(graphics);

		if(KeyHandler.pausePressed) drawPauseWarning(graphics);
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
			case 0: mino = new Mino_I();break;
			case 1: mino = new Mino_J();break;
			case 2: mino = new Mino_L();break;
			case 3: mino = new Mino_O();break;
			case 4: mino = new Mino_S();break;
			case 5: mino = new Mino_T();break;
			case 6: mino = new Mino_Z();break;
		}

		return mino;
	}

	private void drawPauseWarning(Graphics2D graphics) {
		graphics.setColor(Color.yellow);
		graphics.setFont(graphics.getFont().deriveFont(50f));
		graphics.drawString("PAUSED", left_x + 70, top_y + 320);
	}

	private void validateCompletedLines() {
		int x = left_x;
		int y = top_y;
		int blockCount = 0;

		while(x < right_x && y < bottom_y) {

			for(int i = 0; i < staticSquares.size(); i++) {
				if(staticSquares.get(i).x == x && staticSquares.get(i).y == y) blockCount++;
			}

			x += Square.SIZE;

			if(x == right_x) {

				if(blockCount == 12) {
					isRemovalEffectCounterOn =  true;
					linesToRemove.add(y);
					removeStaticSquares(y);	
				}

				blockCount = 0;
				x = left_x;
				y += Square.SIZE;
			}
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

	private void drawGameOverWarning(Graphics2D graphics) {
		graphics.setColor(Color.yellow);
		graphics.setFont(graphics.getFont().deriveFont(50f));
		graphics.drawString("GAME OVER", left_x + 70, top_y + 320);
	}
}
