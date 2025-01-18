package tetromino;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Square extends Rectangle{

	public int x, y;
	public static final int SIZE = 30;
	public Color color;
	
	public Square(Color color) {
		this.color = color;
	}
	
	public void draw(Graphics2D graphics2D) {
		graphics2D.setColor(color);
		graphics2D.fillRect(x, y, SIZE, SIZE);
	}
}
