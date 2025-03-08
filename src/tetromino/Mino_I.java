package tetromino;

import java.awt.Color;

public class Mino_I extends Mino {

	public Mino_I(Color color) {
		if(color == null) create(Color.BLUE);
		else create(color);
	}
	
	public void setXY(int x, int y) {
		// 
		// o o o o   1 0 2 3
		//
		squares[0].x = x;
		squares[0].y = y;
		squares[1].x = squares[0].x - Square.SIZE;
		squares[1].y = squares[0].y;
		squares[2].x = squares[0].x + Square.SIZE;
		squares[2].y = squares[0].y;
		squares[3].x = squares[0].x + Square.SIZE * 2;
		squares[3].y = squares[0].y;
	}
	
	public void getDirection1() {
		// 
		// o o o o   1 0 2 3
		//
		tempSquares[0].x = squares[0].x;
		tempSquares[0].y = squares[0].y;
		tempSquares[1].x = squares[0].x - Square.SIZE;
		tempSquares[1].y = squares[0].y;
		tempSquares[2].x = squares[0].x + Square.SIZE;
		tempSquares[2].y = squares[0].y;
		tempSquares[3].x = squares[0].x + Square.SIZE * 2;
		tempSquares[3].y = squares[0].y;
		
		updateXY(1);
	}	
	public void getDirection2() {
		// o    1
		// o    0
		// o    2
		// o    3
		tempSquares[0].x = squares[0].x;
		tempSquares[0].y = squares[0].y;
		tempSquares[1].x = squares[0].x;
		tempSquares[1].y = squares[0].y + Square.SIZE;
		tempSquares[2].x = squares[0].x;
		tempSquares[2].y = squares[0].y - Square.SIZE;
		tempSquares[3].x = squares[0].x;
		tempSquares[3].y = squares[0].y - Square.SIZE * 2;
		
		updateXY(2);
	}	
	public void getDirection3() {
		getDirection1();
	}	
	public void getDirection4() {
		getDirection2();
	}	
}
