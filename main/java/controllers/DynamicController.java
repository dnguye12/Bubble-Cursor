package controllers;

import models.DynamicModel;
import views.DynamicView;

public class DynamicController {
    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 960;
    public static final int CIRCLE_COUNT = 30;
    public static final int CIRCLE_MAX_RADIUS = 50;
    public static final int CIRCLE_MIN_RADIUS = 5;
    public static final int CURSOR_STEP = 5; //how much the size of the cursor change each time when using '+' or '-'
    private DynamicModel model;
    private DynamicView view;

    public DynamicController() {
        this.model = new DynamicModel();
        this.view = new DynamicView(this.model);
    }

    public DynamicView getView() {
        return view;
    }
}
