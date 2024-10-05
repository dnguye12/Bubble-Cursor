package controllers;

import fr.lri.swingstates.canvas.CStateMachine;
import fr.lri.swingstates.sm.State;
import fr.lri.swingstates.sm.StateMachine;
import fr.lri.swingstates.sm.Transition;
import fr.lri.swingstates.sm.transitions.KeyPress;
import fr.lri.swingstates.sm.transitions.Move;
import fr.lri.swingstates.sm.transitions.Press;
import models.Circle;
import models.DynamicModel;
import views.DynamicView;

import java.awt.*;
import java.awt.event.KeyEvent;

public class DynamicController {
    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 960;
    public static final int CIRCLE_COUNT = 30;
    public static final int CIRCLE_MAX_RADIUS = 50;
    public static final int CIRCLE_MIN_RADIUS = 5;
    public static final int CURSOR_STEP = 5; //how much the size of the cursor change each time when using '+' or '-'
    private DynamicModel model;
    private DynamicView view;
    private StateMachine stateMachine;
    //The circle that is closest to the cursor
    private Circle closestCircle;
    //Distance to the closest circle
    private double closestDistance;
    public DynamicController() {
        this.model = new DynamicModel();
        this.view = new DynamicView(this.model);
        this.closestCircle = null;
        this.closestDistance = Double.MAX_VALUE;
        //setup the State Machine
        this.initStateMachine();
    }
    public DynamicView getView() {
        return view;
    }

    private void initStateMachine() {
        //Use CStateMachine to connect listeners to a Canvas (this.view)
        this.stateMachine = new CStateMachine(this.view) {
            public State start = new State() {

                //Similar to mouseMoved Listener, set the position of the cursor
                //Check which circle is the closest then set up the cursor size according to that
                Transition move = new Move() {
                    public void action() {
                        Point mousePos = (Point) getPoint();
                        closestCircle = findClosestCirlce(mousePos);
                        if(closestCircle != null) {
                            model.setCursorSize((int)closestDistance * 2);
                        }
                        model.setCursorPosition((Point) getPoint());
                        view.repaint();
                    }
                };

                //Left click will select the closest circle
                Transition select = new Press(BUTTON1) {
                    public void action() {
                        model.emptySelectedCircles();
                        //If nothing is in the region, nothing is selected
                        if (closestCircle != null) {
                            closestCircle.setSelected(true);
                            model.addSelectedCircles(closestCircle);
                            view.repaint();
                        }
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

    //Find the closest circle
    private Circle findClosestCirlce(Point mousePos) {
        Circle helper = null;
        double minDistance = Double.MAX_VALUE;

        //Check for all circles
        for (Circle circle : this.model.getCircles()) {
            Point circlePos = circle.getPosition();
            //Quick maths goes brrrrrr
            double distance = Math.sqrt(Math.pow(circlePos.x - mousePos.x, 2) + Math.pow(circlePos.y - mousePos.y, 2));

            //Only select a circle that is closest to the cursor
            if (distance <= minDistance) {
                helper = circle;
                minDistance = distance;
            }
        }
        //The distance from the cursor to the circle plus its radius (add the radius for the cursor sizing purpose)
        if(helper != null) {
            this.closestDistance = minDistance + helper.getRadius();
        }
        return helper;
    }
}
