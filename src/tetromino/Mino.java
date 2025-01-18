package tetromino;

import java.awt.Color;
import java.awt.Graphics2D;

public class Mino {

	public Square squares[] = new Square[4];
	public Square tempSquares[] = new Square[4];
	
	public void create(Color color) {
		for(int i = 0; i < 4; i++) {
			squares[i] = new Square(color);
			tempSquares[i] = new Square(color);
		}
	}
	
	public void setXY(int x, int y) {
	}
	
	public void updateXY(int direction) {
	}
	
	public void update() {
	}
	
	public void draw(Graphics2D graphics2D) {
		graphics2D.setColor(squares[0].color);

		int margin = 2;
		for(int i = 0; i < 4; i++) {
			graphics2D.fillRect(squares[i].x + margin, squares[i].y + margin, Square.SIZE - (margin * 2), Square.SIZE - (margin * 2));
		}
	}
}
