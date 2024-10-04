package controllers;

import models.BubbleModel;
import views.BubbleView;

public class BubbleController {
    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 960;
    public static final int CIRCLE_COUNT = 30;
    public static final int CIRCLE_MAX_RADIUS = 50;
    public static final int CIRCLE_MIN_RADIUS = 5;
    private BubbleModel model;
    private BubbleView view;

    public BubbleController(){
        this.model = new BubbleModel();
        this.view = new BubbleView(this.model);
    }
    public BubbleView getView() {
        return view;
    }
}
