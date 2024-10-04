package models;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static controllers.BubbleController.*;

public class BubbleModel {

    private ArrayList<Circle> circles;

    public  BubbleModel() {
        this.initCircles();
    }

    private void initCircles() {
        this.circles = new ArrayList<>();
        Random rand = new Random();
        Point pos;
        int x,y, radius;
        for(int i = 0; i < CIRCLE_COUNT; i++) {
            Circle newCirle;
            boolean overlaped;
            do {
                overlaped = false;
                radius = rand.nextInt(CIRCLE_MAX_RADIUS) + CIRCLE_MIN_RADIUS;
                x = rand.nextInt(SCREEN_WIDTH);
                y = rand.nextInt(SCREEN_HEIGHT);
                pos = new Point(x, y);
                newCirle = new Circle(i, pos, radius);
                for(Circle circle : this.circles) {
                    if(newCirle.isOverlapping(circle)) {
                        overlaped = true;
                        break;
                    }
                }
            }while(overlaped);

            this.circles.add(newCirle);
        }
    }

    public ArrayList<Circle> getCircles() {
        return circles;
    }
}
