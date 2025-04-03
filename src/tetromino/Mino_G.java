package tetromino;

import java.awt.Color;

public class Mino_G extends Mino {

	public Mino_G() {
		createGameOver(Color.PINK);
	}

	public void setXY(int x, int y) {
		// 0 o o o 
		// o
		// o   o o o
		// o       o
		// 7 o o o 11
		squaresGO[0].x = x;
		squaresGO[0].y = y;
		squaresGO[1].x = squaresGO[0].x + Square.SIZE;
		squaresGO[1].y = squaresGO[0].y;
		squaresGO[2].x = squaresGO[0].x + Square.SIZE * 2;
		squaresGO[2].y = squaresGO[0].y;
		squaresGO[3].x = squaresGO[0].x + Square.SIZE * 3;
		squaresGO[3].y = squaresGO[0].y;
		squaresGO[4].x = squaresGO[0].x;
		squaresGO[4].y = squaresGO[0].y + Square.SIZE;
		squaresGO[5].x = squaresGO[0].x;
		squaresGO[5].y = squaresGO[0].y + Square.SIZE * 2;
		squaresGO[6].x = squaresGO[0].x;
		squaresGO[6].y = squaresGO[0].y + Square.SIZE * 3;
		squaresGO[7].x = squaresGO[0].x;
		squaresGO[7].y = squaresGO[0].y + Square.SIZE * 4;

		squaresGO[8].x = squaresGO[0].x + Square.SIZE;
		squaresGO[8].y = squaresGO[0].y + Square.SIZE * 4;
		squaresGO[9].x = squaresGO[0].x + Square.SIZE * 2;
		squaresGO[9].y = squaresGO[0].y + Square.SIZE * 4;
		squaresGO[10].x = squaresGO[0].x + Square.SIZE * 3;
		squaresGO[10].y = squaresGO[0].y + Square.SIZE * 4;
		squaresGO[11].x = squaresGO[0].x + Square.SIZE * 4;
		squaresGO[11].y = squaresGO[0].y + Square.SIZE * 4;

		squaresGO[12].x = squaresGO[0].x + Square.SIZE * 4;
		squaresGO[12].y = squaresGO[0].y + Square.SIZE * 3;
		squaresGO[13].x = squaresGO[0].x + Square.SIZE * 4;
		squaresGO[13].y = squaresGO[0].y + Square.SIZE * 2;

		squaresGO[14].x = squaresGO[0].x + Square.SIZE * 3;
		squaresGO[14].y = squaresGO[0].y + Square.SIZE * 2;
		squaresGO[15].x = squaresGO[0].x + Square.SIZE * 2;
		squaresGO[15].y = squaresGO[0].y + Square.SIZE * 2;
	}
}
