package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class TitleManager {

    private int menuSelectionNumber;

    private final int X_DISTANCE_ARROW_MENU_SELECTION = 30;

    public TitleManager() {
        this.menuSelectionNumber = 0;
    }
    
    public void draw(Graphics2D graphics) {
    	drawTitle(graphics);
        drawMenu(graphics);
	}
    
    public void drawMenu(Graphics2D graphics) {
    	graphics.setFont(graphics.getFont().deriveFont(Font.BOLD, 48F));

        String text = "NEW GAME";
        int x = getXForCenteredText(graphics, text);
        int y = GamePanel.HEIGHT / 2;
        graphics.drawString(text, x, y);
        if(this.menuSelectionNumber == 0) {
            graphics.drawString(">", x - this.X_DISTANCE_ARROW_MENU_SELECTION, y);
        }

        text = "HIGHSCORE";
    	x = getXForCenteredText(graphics, text);
        y += 50;
        graphics.drawString(text, x, y);
        if(this.menuSelectionNumber == 1) {
            graphics.drawString(">", x - this.X_DISTANCE_ARROW_MENU_SELECTION, y);
        }

        text = "QUIT";
    	x = getXForCenteredText(graphics, text);
        y += 50;
        graphics.drawString(text, x, y);
        if(this.menuSelectionNumber == 2) {
            graphics.drawString(">", x - this.X_DISTANCE_ARROW_MENU_SELECTION, y);
        }
    }

	public void drawTitle(Graphics2D graphics) {
    	graphics.setFont(graphics.getFont().deriveFont(Font.BOLD, 96F));
        String title =  "TETRIS";
        int x = getXForCenteredText(graphics, title); 
        int y = GamePanel.HEIGHT / 3;

        graphics.setColor(Color.GRAY);
        graphics.drawString(title, x + 5, y + 5);

        graphics.setColor(Color.WHITE);
        graphics.drawString(title, x, y);
    }

    public int getXForCenteredText(Graphics2D graphics, String text) {
        int length = (int) graphics.getFontMetrics().getStringBounds(text, graphics).getWidth();
        return GamePanel.WIDTH / 2 - length / 2;
    }
    
    public int getMenuSelectionNumber() {
		return this.menuSelectionNumber;
	}

	public void setMenuSelectionNumber(int modifier) {
        int result = this.menuSelectionNumber + modifier;

        if(result < 0) result = 2;

        if(result > 2) result = 0;

		this.menuSelectionNumber = result;
	}
}
