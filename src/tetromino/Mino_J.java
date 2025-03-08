package tetromino;

import java.awt.Color;

public class Mino_J extends Mino {

	public Mino_J(Color color) {
		create(Color.YELLOW);
		if(color == null) create(Color.YELLOW);
		else create(color);
	}
	
	public void setXY(int x, int y) {
		//   o
		//   o
		// o o
		squares[0].x = x;
		squares[0].y = y;
		squares[1].x = squares[0].x;
		squares[1].y = squares[0].y - Square.SIZE;
		squares[2].x = squares[0].x;
		squares[2].y = squares[0].y + Square.SIZE;
		squares[3].x = squares[0].x - Square.SIZE;
		squares[3].y = squares[0].y + Square.SIZE;
	}
	public void getDirection1() {
		
		tempSquares[0].x = squares[0].x;
		tempSquares[0].y = squares[0].y;
		tempSquares[1].x = squares[0].x;
		tempSquares[1].y = squares[0].y - Square.SIZE;
		tempSquares[2].x = squares[0].x;
		tempSquares[2].y = squares[0].y + Square.SIZE;
		tempSquares[3].x = squares[0].x - Square.SIZE;
		tempSquares[3].y = squares[0].y + Square.SIZE;
		
		updateXY(1);
	}	
	public void getDirection2() {
		// o    
		// o o o    
		//
		tempSquares[0].x = squares[0].x;
		tempSquares[0].y = squares[0].y;
		tempSquares[1].x = squares[0].x + Square.SIZE;
		tempSquares[1].y = squares[0].y;
		tempSquares[2].x = squares[0].x - Square.SIZE;
		tempSquares[2].y = squares[0].y;
		tempSquares[3].x = squares[0].x - Square.SIZE;
		tempSquares[3].y = squares[0].y - Square.SIZE;
		
		updateXY(2);
	}	
	public void getDirection3() {
		// o o     
		// o    
		// o
		tempSquares[0].x = squares[0].x;
		tempSquares[0].y = squares[0].y;
		tempSquares[1].x = squares[0].x;
		tempSquares[1].y = squares[0].y + Square.SIZE;
		tempSquares[2].x = squares[0].x;
		tempSquares[2].y = squares[0].y - Square.SIZE;
		tempSquares[3].x = squares[0].x + Square.SIZE;
		tempSquares[3].y = squares[0].y - Square.SIZE;
		
		updateXY(3);
	}	
	public void getDirection4() {
		//         
		// o o o
		//     o
		tempSquares[0].x = squares[0].x;
		tempSquares[0].y = squares[0].y;
		tempSquares[1].x = squares[0].x - Square.SIZE;
		tempSquares[1].y = squares[0].y;
		tempSquares[2].x = squares[0].x + Square.SIZE;
		tempSquares[2].y = squares[0].y;
		tempSquares[3].x = squares[0].x + Square.SIZE;
		tempSquares[3].y = squares[0].y + Square.SIZE;
		
		updateXY(4);
	}	
}
