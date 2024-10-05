package models;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static controllers.DynamicController.*;

public class DynamicModel {
    //Holding all circles on the screen
    private ArrayList<Circle> circles;
    //Holding only selected circles. Using an arraylist to handle multiple select
    private ArrayList<Circle> selectedCircles;
    private Point cursorPosition;
    private int cursorSize;

    public DynamicModel() {
        this.initCircles();
        this.selectedCircles = new ArrayList<>();
        this.cursorSize = CIRCLE_MAX_RADIUS;
        this.cursorPosition = null;
    }

    private void initCircles() {
        this.circles = new ArrayList<>();
        Random rand = new Random();
        Point pos;
        int x, y, radius;
        Circle newCirle;
        boolean overlaped;

        //Create a screen with CIRCLE_COUNT circles
        //Use this to prevent circles from overlapping each other.
        for (int i = 0; i < CIRCLE_COUNT; i++) {
            do {
                overlaped = false;
                radius = rand.nextInt(CIRCLE_MAX_RADIUS) + CIRCLE_MIN_RADIUS;
                x = rand.nextInt(SCREEN_WIDTH);
                y = rand.nextInt(SCREEN_HEIGHT);
                pos = new Point(x -radius, y - radius);
                newCirle = new Circle(i, pos, radius);
                for (Circle circle : this.circles) {
                    //If overlapped then generate a new circle instead
                    if (newCirle.isOverlapping(circle)) {
                        overlaped = true;
                        break;
                    }
                }
            } while (overlaped);

            this.circles.add(newCirle);
        }
    }

    public ArrayList<Circle> getCircles() {
        return circles;
    }

    public void addSelectedCircles(Circle circle) {
        this.selectedCircles.add(circle);
    }

    public void emptySelectedCircles() {
        for (Circle circle : this.selectedCircles) {
            circle.setSelected(false);
        }
        this.selectedCircles = new ArrayList<>();
    }

    public void removeSelectedCircles() {
        for (Circle circle : this.selectedCircles) {
            this.circles.remove(circle);
        }
    }

    public int getCursorSize() {
        return cursorSize;
    }
    public void setCursorSize(int n) {
        this.cursorSize = n;
    }

    public void setCursorPosition(Point cursorPosition) {
        this.cursorPosition = cursorPosition;
    }

    public Point getCursorPosition() {
        return cursorPosition;
    }
}
