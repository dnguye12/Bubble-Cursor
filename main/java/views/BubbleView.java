package views;

import fr.lri.swingstates.canvas.Canvas;
import models.BubbleModel;
import models.Circle;

import java.awt.*;

import static controllers.BubbleController.SCREEN_HEIGHT;
import static controllers.BubbleController.SCREEN_WIDTH;

public class BubbleView extends Canvas {
    private BubbleModel model;
    private Point cursorPos;
    public BubbleView(BubbleModel model) {
        this.model = model;
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));

        //Just in case. Improve input handling
        this.setFocusable(true);
        this.requestFocusInWindow();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        //Draw a black background
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        //Draw circles
        g2d.setColor(Color.DARK_GRAY);
        for (Circle cirle : this.model.getCircles()) {
            this.drawCircle(g2d, cirle);
        }

        //Draw cursor
        this.cursorPos = this.model.getCursorPosition();
        if(this.cursorPos != null) {
            this.drawCursor(g2d, this.cursorPos);
        }
    }

    private void drawCircle(Graphics2D g2d, Circle circle) {
        Point point = circle.getPosition();
        int radius = circle.getRadius();
        //If a circle is selected, draw it green otherwise draw it Dark Gray
        if(circle.isSelected()) {
            g2d.setColor(Color.GREEN);
        }
        g2d.fillOval(point.x, point.y, 2 * radius, 2 * radius);
        if(circle.isSelected()) {
            g2d.setColor(Color.DARK_GRAY);
        }
    }
    private void drawCursor(Graphics2D g2d, Point cursorPos) {
        int cursorSize = this.model.getCursorSize();

        //Add a bit of transparency to the cursor so can see circles
        g2d.setColor(new Color(0, 0, 255, 150));
        //The cursor is centered around the position (cause java swing draw from top left)
        g2d.fillOval(cursorPos.x - cursorSize / 2, cursorPos.y - cursorSize / 2, cursorSize, cursorSize);
    }
}
