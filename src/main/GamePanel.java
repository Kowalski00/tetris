package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{

	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	final int FPS = 60;
	Thread gameThread;
	PlayManager playManager;
	TitleManager titleManager;
	HighScoreManager highScoreManager;

	public static Sound music = new Sound();
	public static Sound soundEffect = new Sound();

	public int gameState;
	public final int titleState = 0;
	public final int playState = 1;
	public final int highScoreState = 2;
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		this.setBackground(Color.black);
		this.setLayout(null);
		this.addKeyListener(new KeyHandler(this));
		this.setFocusable(true);
		
		this.playManager = new PlayManager(this);
		this.titleManager = new TitleManager();
		this.highScoreManager = new HighScoreManager();
	}
	
	public void launchGame() {
		gameThread = new Thread(this);
		gameThread.start();

		music.play(0, true);
		music.loop();
		this.gameState = titleState;
	}

	@Override
	public void run() {
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		
		while(gameThread != null) {
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;
			lastTime = currentTime;

			if(delta >= 1) {
				update();
				repaint();
				delta--;
			}
		}
	}
	
	private void update() {
		if(!KeyHandler.pausePressed && !this.playManager.isGameOver) this.playManager.update();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		if(this.gameState == titleState) {
			this.titleManager.draw(g2);
		}

		if(this.gameState == playState) {
			this.playManager.draw(g2);
		}
		
		if(this.gameState == highScoreState) {
			this.highScoreManager.setCurrentScore(this.playManager.currentScore);
			this.highScoreManager.draw(g2);
		}
	}
	
}
