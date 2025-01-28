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
	public static int left_x;
	public static int right_x;
	public static int top_y;
	public static int bottom_y;
	public static int dropInterval = 60;
	
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

			currentMino.isDeactivating = false;

			currentMino = nextMino;
			nextMino = pickRandomMino();

			currentMino.setXY(MINO_START_X, MINO_START_Y);
			nextMino.setXY(NEXT_MINO_X, NEXT_MINO_Y);
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
}
