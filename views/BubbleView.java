package views;

import models.BubbleModel;
import models.Circle;

import javax.swing.*;
import java.awt.*;

import static controllers.BubbleController.SCREEN_HEIGHT;
import static controllers.BubbleController.SCREEN_WIDTH;

public class BubbleView extends JComponent {
    private BubbleModel model;

    public BubbleView(BubbleModel model) {
        this.model = model;
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));

        this.setFocusable(true);
        this.requestFocusInWindow();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        g2d.setColor(Color.LIGHT_GRAY);
        for (Circle cirle : this.model.getCircles()) {
            this.drawCircle(g2d, cirle);
        }
    }

    private void drawCircle(Graphics2D g2d, Circle circle) {
        Point point = circle.getPosition();
        int radius = circle.getRadius();
        g2d.fillOval(point.x, point.y, 2 * radius, 2 * radius);
    }
}
