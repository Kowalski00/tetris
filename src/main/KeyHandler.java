package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

	public static boolean upPressed, downPressed, leftPressed, rightPressed, pausePressed = false;
	
	private GamePanel gamePanel;

	public KeyHandler(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
	
		int code = e.getKeyCode();
		if(this.gamePanel.gameState == this.gamePanel.titleState) {
			switch (code) {
				case KeyEvent.VK_UP: {
					this.gamePanel.titleManager.setMenuSelectionNumber(-1);
					break;
				}
				case KeyEvent.VK_DOWN: {
					this.gamePanel.titleManager.setMenuSelectionNumber(1);
					leftPressed = true;
					break;
				}
				case KeyEvent.VK_ENTER: {
					if(this.gamePanel.titleManager.getMenuSelectionNumber() == 0) {
						this.gamePanel.gameState = 1;
					}
					if(this.gamePanel.titleManager.getMenuSelectionNumber() == 2) {
						System.exit(0);
					}
					break;
				}
			}
		}
		
		if(this.gamePanel.gameState == this.gamePanel.playState) {
			switch (code) {
				case KeyEvent.VK_UP: {
					upPressed = true;
					break;
				}
				case KeyEvent.VK_LEFT: {
					leftPressed = true;
					break;
				}
				case KeyEvent.VK_DOWN: {
					downPressed = true;
					break;
				}
				case KeyEvent.VK_RIGHT: {
					rightPressed = true;
					break;
				}
				case KeyEvent.VK_SPACE: {
					if(pausePressed) {
						GamePanel.music.play(0, true);
						GamePanel.music.loop();
					} else {
						GamePanel.music.stop();
					}
					pausePressed = !pausePressed;
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}

}
