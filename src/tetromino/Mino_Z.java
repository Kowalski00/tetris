package tetromino;

import java.awt.Color;

public class Mino_Z extends Mino {

	public Mino_Z() {
		create(Color.GRAY);
	}

	public void setXY(int x, int y) {
		//   o    3 
		// o o  0 2
		// o    1
		squares[0].x = x;
		squares[0].y = y;
		squares[1].x = squares[0].x;
		squares[1].y = squares[0].y - Square.SIZE;
		squares[2].x = squares[0].x - Square.SIZE;
		squares[2].y = squares[0].y;
		squares[3].x = squares[0].x - Square.SIZE;
		squares[3].y = squares[0].y + Square.SIZE;
	}
	public void getDirection1() {
		//   o    3 
		// o o  0 2
		// o    1
		tempSquares[0].x = squares[0].x;
		tempSquares[0].y = squares[0].y;
		tempSquares[1].x = squares[0].x;
		tempSquares[1].y = squares[0].y - Square.SIZE;
		tempSquares[2].x = squares[0].x - Square.SIZE;
		tempSquares[2].y = squares[0].y;
		tempSquares[3].x = squares[0].x - Square.SIZE;
		tempSquares[3].y = squares[0].y + Square.SIZE;
		
		updateXY(1);
	}	
	public void getDirection2() {
		// o o     3 2
		//   o o     0 1
		//
		tempSquares[0].x = squares[0].x;
		tempSquares[0].y = squares[0].y;
		tempSquares[1].x = squares[0].x + Square.SIZE;
		tempSquares[1].y = squares[0].y;
		tempSquares[2].x = squares[0].x;
		tempSquares[2].y = squares[0].y - Square.SIZE;
		tempSquares[3].x = squares[0].x - Square.SIZE;
		tempSquares[3].y = squares[0].y - Square.SIZE;
		
		updateXY(2);
	}	
	public void getDirection3() {
		getDirection1();
	}	
	public void getDirection4() {
		getDirection2();
	}
}
