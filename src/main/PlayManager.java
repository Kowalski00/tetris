package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import tetromino.Mino;
import tetromino.Mino_L;
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
	final int MINO_START_X;
	final int MINO_START_Y;
	
	public PlayManager() {
		left_x = (GamePanel.WIDTH / 2) - (WIDTH / 2);
		right_x = left_x + WIDTH;
		top_y = 50;
		bottom_y = top_y + HEIGHT;
		
		MINO_START_X = left_x + (WIDTH / 2) - Square.SIZE;
		MINO_START_Y = top_y + Square.SIZE;
		
		currentMino = new Mino_L();
		currentMino.setXY(MINO_START_X, MINO_START_Y);
	}
	
	public void update() {
		currentMino.update();
	}
	
	public void draw(Graphics2D graphics) {
		graphics.setColor(Color.white);
		graphics.setStroke(new BasicStroke(4f));
		graphics.drawRect(left_x - 4, top_y - 4, WIDTH + 8, HEIGHT + 8);
		drawNextTetrominoBox(graphics);

		if(currentMino != null) {
			currentMino.draw(graphics);
		}
	}
	
	private void drawNextTetrominoBox(Graphics2D graphics) {
		int x = right_x + 100;
		int y = bottom_y - 200;
		graphics.drawRect(x, y, 200, 200);
		graphics.setFont(new Font("Arial", Font.PLAIN, 30));
		graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		graphics.drawString("NEXT", x + 60, y + 60);
	}
}
