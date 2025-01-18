package tetromino;

import java.awt.Color;

public class Mino_L extends Mino {

	public Mino_L() {
		create(Color.ORANGE);
	}
	
	public void setXY(int x, int y) {
		// o    1
		// o    0
		// o o  2 3
		squares[0].x = x;
		squares[0].y = y;
		squares[1].x = squares[0].x;
		squares[1].y = squares[0].y - Square.SIZE;
		squares[2].x = squares[0].x;
		squares[2].y = squares[0].y + Square.SIZE;
		squares[3].x = squares[0].x + Square.SIZE;
		squares[3].y = squares[0].y + Square.SIZE;
	}
}
