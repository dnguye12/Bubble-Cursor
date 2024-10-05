package controllers;

import fr.lri.swingstates.canvas.CStateMachine;
import fr.lri.swingstates.sm.State;
import fr.lri.swingstates.sm.StateMachine;
import fr.lri.swingstates.sm.Transition;
import fr.lri.swingstates.sm.transitions.KeyPress;
import fr.lri.swingstates.sm.transitions.Move;
import fr.lri.swingstates.sm.transitions.Press;
import models.BubbleModel;
import models.Circle;
import views.BubbleView;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class BubbleController {
    //constants for constants purposes
    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 960;
    public static final int CIRCLE_COUNT = 30;
    public static final int CIRCLE_MAX_RADIUS = 50;
    public static final int CIRCLE_MIN_RADIUS = 5;
    public static final int CURSOR_STEP = 5; //how much the size of the cursor change each time when using '+' or '-'
    private BubbleModel model;
    private BubbleView view;
    private StateMachine stateMachine;

    public BubbleController() {
        this.model = new BubbleModel();
        this.view = new BubbleView(this.model);
        //setup the State Machine
        this.initStateMachine();
    }

    public BubbleView getView() {
        return view;
    }

    private void initStateMachine() {
        //Use CStateMachine to connect listeners to a Canvas (this.view)
        this.stateMachine = new CStateMachine(this.view) {
            public State start = new State() {
                //When '+' button is pressed, increase the cursor size.
                Transition resizePlus = new KeyPress('+') {
                    public void action() {
                        model.increaseCursorSize();
                        view.repaint();
                    }
                };

                //When '-' button is pressed, increase the cursor size.
                Transition resizeMinus = new KeyPress('-') {
                    public void action() {
                        model.decreaseCursorSize();
                        view.repaint();
                    }
                };

                //Similar to mouseMoved Listener, this is used to draw the cursor moving along the mouse.
                Transition move = new Move() {
                    public void action() {
                        model.setCursorPosition((Point) getPoint());
                        view.repaint();
                    }
                };

                //Left click will select the closest circle inside the cursor region
                Transition select = new Press(BUTTON1) {
                    public void action() {
                        Point mousePos = (Point) getPoint();
                        //Unselect the last selected circles
                        model.emptySelectedCircles();
                        Circle closestCircle = findClosestCirlce(mousePos);
                        //If nothing is in the region, nothing is selected
                        if (closestCircle != null) {
                            closestCircle.setSelected(true);
                            model.addSelectedCircles(closestCircle);
                            view.repaint();
                        }
                    }
                };

                //Right click to select multiple circles instead
                Transition selectMultiple = new Press(BUTTON3) {
                    public void action() {
                        Point mousePos = (Point) getPoint();
                        model.setSelectedCircle(findMultipleCircles(mousePos));
                        view.repaint();
                    }
                };

                //Enter to confirm selection and delete all selected items
                Transition enter = new KeyPress(KeyEvent.VK_ENTER) {
                    public void action() {
                        model.removeSelectedCircles();
                        view.repaint();
                    }
                };
            };
        };
    }

    //Find the closest circle inside the cursor region
    private Circle findClosestCirlce(Point mousePos) {
        Circle helper = null;
        double minDistance = Double.MAX_VALUE;

        //Check for all circles
        //Might be better to have 4 arraylists dividing the screen instead. But not worth it for 30 circles, might try this when there are 30,000 circles.
        for (Circle circle : this.model.getCircles()) {
            Point circlePos = circle.getPosition();
            //Quick maths goes brrrrrr
            double distance = Math.sqrt(Math.pow(circlePos.x - mousePos.x, 2) + Math.pow(circlePos.y - mousePos.y, 2));

            //Only select a circle that is inside cursor region and is closest to the cursor
            if (distance <= model.getCursorSize() / 2.0 && distance <= minDistance) {
                helper = circle;
                minDistance = distance;
            }
        }
        return helper;
    }

    //Find all circles inside the cursor region
    private ArrayList<Circle> findMultipleCircles(Point mousePos) {
        ArrayList<Circle> helper = new ArrayList<>();

        for(Circle circle : this.model.getCircles()) {
            Point circlePos = circle.getPosition();
            double distance = Math.sqrt(Math.pow(circlePos.x - mousePos.x, 2) + Math.pow(circlePos.y - mousePos.y, 2));
            if(distance <= model.getCursorSize() / 2.0) {
                circle.setSelected(true);
                helper.add(circle);
            }
        }
        return helper;
    }
}
