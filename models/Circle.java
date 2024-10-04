package models;

import java.awt.*;

public class Circle {
    private int idx;
    private Point position;
    private int radius;
    private boolean isSelected;

    public Circle(int idx, Point position, int radius) {
        this.idx = idx;
        this.position = position;
        this.radius = radius;
        this.isSelected = false;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isOverlapping(Circle other) {
        int dx = this.position.x - other.position.x;
        int dy = this.position.y - other.position.y;
        int distanceSquared = dx * dx + dy * dy;
        int radiusSum = this.radius + other.radius;
        return distanceSquared < radiusSum * radiusSum;
    }
}
