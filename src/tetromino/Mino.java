package tetromino;

import java.awt.Color;
import java.awt.Graphics2D;

import main.KeyHandler;
import main.PlayManager;

public class Mino {

	public Square squares[] = new Square[4];
	public Square tempSquares[] = new Square[4];
	int autoDropCounter = 0;
	public int direction = 1;
	
	public void create(Color color) {
		for(int i = 0; i < 4; i++) {
			squares[i] = new Square(color);
			tempSquares[i] = new Square(color);
		}
	}
	
	public void setXY(int x, int y) {
	}
	
	public void updateXY(int direction) {
		this.direction = direction;
		for(int i = 0; i < 4; i++) {
			squares[i].x = tempSquares[i].x;
			squares[i].y = tempSquares[i].y;
		}
	}

	public void getDirection1() {}	
	public void getDirection2() {}	
	public void getDirection3() {}	
	public void getDirection4() {}	
	
	public void update() {
		
		moveTetromino();

		autoDropCounter++;
		if(autoDropCounter == PlayManager.dropInterval) {
			
			for(int i = 0; i < 4; i++) {
				squares[i].y += Square.SIZE;
			}
			autoDropCounter = 0;
		}
	}
	
	public void draw(Graphics2D graphics2D) {
		graphics2D.setColor(squares[0].color);

		int margin = 2;
		for(int i = 0; i < 4; i++) {
			graphics2D.fillRect(squares[i].x + margin, squares[i].y + margin, Square.SIZE - (margin * 2), Square.SIZE - (margin * 2));
		}
	}
	
	private void moveTetromino() {

		if(KeyHandler.upPressed) {
			switch (direction) {
				case 1: {
					getDirection2();
					break;
				}
				case 2: {
					getDirection3();
					break;
				}
				case 3: {
					getDirection4();
					break;
				}
				case 4: {
					getDirection1();
					break;
				}
			}
			KeyHandler.upPressed = false;
		}
		
		if(KeyHandler.downPressed) {
			for(int i = 0; i < 4; i++) {
				squares[i].y += Square.SIZE;
			}
			autoDropCounter = 0;
			KeyHandler.downPressed = false;
		}
		
		if(KeyHandler.leftPressed) {
			for(int i = 0; i < 4; i++) {
				squares[i].x -= Square.SIZE;
			}
			KeyHandler.leftPressed = false;
		}
		
		if(KeyHandler.rightPressed) {
			for(int i = 0; i < 4; i++) {
				squares[i].x += Square.SIZE;
			}
			KeyHandler.rightPressed = false;
		}
	}
	
}
