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
	
	boolean leftCollision;
	boolean rightCollision;
	boolean bottomCollision;
	
	public void create(Color color) {
		for(int i = 0; i < 4; i++) {
			squares[i] = new Square(color);
			tempSquares[i] = new Square(color);
		}
	}
	
	public void setXY(int x, int y) {
	}
	
	public void updateXY(int direction) {

		checkRotationCollision();

		if(!leftCollision && !rightCollision && !bottomCollision) {
			this.direction = direction;
			for(int i = 0; i < 4; i++) {
				squares[i].x = tempSquares[i].x;
				squares[i].y = tempSquares[i].y;
			}
		}
	}

	public void getDirection1() {}	
	public void getDirection2() {}	
	public void getDirection3() {}	
	public void getDirection4() {}	

	public void checkMovementCollision() {
		leftCollision = false;
		rightCollision = false;
		bottomCollision = false;

		for(int i = 0; i < squares.length; i++) {
			if(squares[i].x == PlayManager.left_x) leftCollision = true;
		}
		
		for(int i = 0; i < squares.length; i++) {
			if(squares[i].x + Square.SIZE == PlayManager.right_x) rightCollision = true;
		}
		
		for(int i = 0; i < squares.length; i++) {
			if(squares[i].y + Square.SIZE == PlayManager.bottom_y) bottomCollision = true;
		}
	}
	public void checkRotationCollision() {
		leftCollision = false;
		rightCollision = false;
		bottomCollision = false;

		for(int i = 0; i < squares.length; i++) {
			if(tempSquares[i].x < PlayManager.left_x) leftCollision = true;
		}
		
		for(int i = 0; i < squares.length; i++) {
			if(tempSquares[i].x + Square.SIZE > PlayManager.right_x) rightCollision = true;
		}
		
		for(int i = 0; i < squares.length; i++) {
			if(tempSquares[i].y + Square.SIZE > PlayManager.bottom_y) bottomCollision = true;
		}
	}
	
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
		
		checkMovementCollision();
		
		if(KeyHandler.downPressed) {
			if(!bottomCollision) {
				for(int i = 0; i < 4; i++) {
					squares[i].y += Square.SIZE;
				}
				autoDropCounter = 0;
			}
			KeyHandler.downPressed = false;
		}
		
		if(KeyHandler.leftPressed) {

			if(!leftCollision) {
				for(int i = 0; i < 4; i++) {
					squares[i].x -= Square.SIZE;
				}
			}
			KeyHandler.leftPressed = false;
		}
		
		if(KeyHandler.rightPressed) {
			if(!rightCollision) {
				for(int i = 0; i < 4; i++) {
					squares[i].x += Square.SIZE;
				}
			}
			KeyHandler.rightPressed = false;
		}
	}
	
}
