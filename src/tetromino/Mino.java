package tetromino;

import java.awt.Color;
import java.awt.Graphics2D;

import main.GamePanel;
import main.KeyHandler;
import main.PlayManager;

public class Mino {

	public Square squares[] = new Square[4];
	public Square tempSquares[] = new Square[4];

	public Square squaresGO[] = new Square[16];
	public Square tempSquaresGO[] = new Square[16];

	int autoDropCounter = 0;
	public int direction = 1;
	public boolean isDeactivating;
	public int deactivateCounter = 0;
	
	boolean hasLeftCollision, hasRightCollision, hasBottomCollision;
	public boolean isMinoActive = true;
	public int gameOverIndex;
	
	public void create(Color color) {
		for(int i = 0; i < 4; i++) {
			squares[i] = new Square(color);
			tempSquares[i] = new Square(color);
		}
	}
	
	public void createGameOver(Color color) {
		for(int i = 0; i < 16; i++) {
			squaresGO[i] = new Square(color);
			tempSquaresGO[i] = new Square(color);
		}
	}
	
	public void setXY(int x, int y) {
	}
	
	public void updateXY(int direction) {

		checkRotationCollision();

		if(!hasLeftCollision && !hasRightCollision && !hasBottomCollision) {
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
		hasLeftCollision = false;
		hasRightCollision = false;
		hasBottomCollision = false;
		
		checkStaticCollision();

		for(int i = 0; i < squares.length; i++) {
			if(squares[i].x == PlayManager.left_x) hasLeftCollision = true;
		}
		
		for(int i = 0; i < squares.length; i++) {
			if(squares[i].x + Square.SIZE == PlayManager.right_x) hasRightCollision = true;
		}
		
		for(int i = 0; i < squares.length; i++) {
			if(squares[i].y + Square.SIZE == PlayManager.bottom_y) hasBottomCollision = true;
		}
	}
	public void checkRotationCollision() {
		hasLeftCollision = false;
		hasRightCollision = false;
		hasBottomCollision = false;

		for(int i = 0; i < squares.length; i++) {
			if(tempSquares[i].x < PlayManager.left_x) hasLeftCollision = true;
		}
		
		for(int i = 0; i < squares.length; i++) {
			if(tempSquares[i].x + Square.SIZE > PlayManager.right_x) hasRightCollision = true;
		}
		
		for(int i = 0; i < squares.length; i++) {
			if(tempSquares[i].y + Square.SIZE > PlayManager.bottom_y) hasBottomCollision = true;
		}
	}
	
	private void checkStaticCollision() {
		
		for(int i = 0; i < PlayManager.staticSquares.size(); i++) {
			int targetX = PlayManager.staticSquares.get(i).x;
			int targetY = PlayManager.staticSquares.get(i).y;

			for(int j = 0; j < squares.length; j++) {
				if(squares[j].y + Square.SIZE == targetY && squares[j].x == targetX) hasBottomCollision = true;
			}
			
			for(int j = 0; j < squares.length; j++) {
				if(squares[j].x - Square.SIZE == targetX && squares[j].y == targetY) hasLeftCollision = true;
			}
			
			for(int j = 0; j < squares.length; j++) {
				if(squares[j].x + Square.SIZE == targetX && squares[j].y == targetY) hasRightCollision = true;
			}
		}
	}
	
	private void checkGOStaticCollision() {
		
		for(int i = 0; i < PlayManager.goStaticSquares.size(); i++) {
			int targetX = PlayManager.goStaticSquares.get(i).x;
			int targetY = PlayManager.goStaticSquares.get(i).y;

			for(int j = 0; j < squaresGO.length; j++) {
				if(squaresGO[j].y + Square.SIZE == targetY && squaresGO[j].x == targetX) hasBottomCollision = true;
			}
			
			for(int j = 0; j < squaresGO.length; j++) {
				if(squaresGO[j].x - Square.SIZE == targetX && squaresGO[j].y == targetY) hasLeftCollision = true;
			}
			
			for(int j = 0; j < squaresGO.length; j++) {
				if(squaresGO[j].x + Square.SIZE == targetX && squaresGO[j].y == targetY) hasRightCollision = true;
			}
		}
	}
	
	public void update() {

		if(isDeactivating) deactivateMino();
		
		moveTetromino();

		if(hasBottomCollision) {
			if(!isDeactivating) GamePanel.soundEffect.play(4, false);
			isDeactivating = true;	
			return;
		}

		autoDropCounter++;
		if(autoDropCounter == PlayManager.dropInterval) {
			
			for(int i = 0; i < 4; i++) {
				squares[i].y += Square.SIZE;
			}
			autoDropCounter = 0;
		}
	}
	
	public void updateGameOver() {

		if(isDeactivating) {
			deactivateCounter++;

			if(deactivateCounter == 45) {
				deactivateCounter = 0;

				if(hasBottomCollision) isMinoActive = false;
			}
		}
		
		hasBottomCollision = false;
		
		checkGOStaticCollision();
		
		for(int i = 0; i < squaresGO.length; i++) {
			if(squaresGO[i].y + Square.SIZE == PlayManager.bottom_y) hasBottomCollision = true;
		}
		
		if(!hasBottomCollision) {
			for(int i = 0; i < 16; i++) {
				squaresGO[i].y += Square.SIZE;
			}
			autoDropCounter = 0;
		}

		if(hasBottomCollision) {
			if(!isDeactivating) GamePanel.soundEffect.play(4, false);
			isDeactivating = true;	
			return;
		}

		autoDropCounter++;
		if(autoDropCounter == PlayManager.dropInterval) {
			
			for(int i = 0; i < 16; i++) {
				squaresGO[i].y += Square.SIZE;
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
	
	public void drawForGO(Graphics2D graphics2D) {
		if(squaresGO[0] == null) return;
		graphics2D.setColor(squaresGO[0].color);

		int margin = 2;
		for(int i = 0; i < 16; i++) {
			graphics2D.fillRect(squaresGO[i].x + margin, squaresGO[i].y + margin, Square.SIZE - (margin * 2), Square.SIZE - (margin * 2));
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
			GamePanel.soundEffect.play(3, false);
		}
		
		checkMovementCollision();
		
		if(KeyHandler.downPressed) {
			if(!hasBottomCollision) {
				for(int i = 0; i < 4; i++) {
					squares[i].y += Square.SIZE;
				}
				autoDropCounter = 0;
			}
			KeyHandler.downPressed = false;
		}
		
		if(KeyHandler.leftPressed) {

			if(!hasLeftCollision) {
				for(int i = 0; i < 4; i++) {
					squares[i].x -= Square.SIZE;
				}
			}
			KeyHandler.leftPressed = false;
		}
		
		if(KeyHandler.rightPressed) {
			if(!hasRightCollision) {
				for(int i = 0; i < 4; i++) {
					squares[i].x += Square.SIZE;
				}
			}
			KeyHandler.rightPressed = false;
		}
	}

	private void deactivateMino() {

		deactivateCounter++;

		if(deactivateCounter == 45) {
			deactivateCounter = 0;
			checkMovementCollision();

			if(hasBottomCollision) isMinoActive = false;
		}
	}
	
}
