package com.eltech.coursework.controller;

import java.awt.*;

public abstract class ObjectController {
    private ControllableView view;

    public void assignToView(ControllableView view) {
        this.view = view;
    }

    public ControllableView getView() {
        return view;
    }

    public abstract void paint(Graphics2D graphics, Rectangle area);
}
