package com.eltech.coursework.controller;

import java.awt.*;

public abstract class ObjectController {
    private ControllableView view;
    private int zIndex = 0;

    public void setZIndex(int zIndex) {
        this.zIndex = zIndex;
    }

    public int getZIndex() {
        return zIndex;
    }

    public void assignToView(ControllableView view) {
        this.view = view;
    }

    public ControllableView getView() {
        return view;
    }

    public abstract void paint(Graphics2D graphics, Rectangle area);

    public boolean onClick(double x, double y) {
        return false;
    }
}
