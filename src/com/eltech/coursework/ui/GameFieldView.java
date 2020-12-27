package com.eltech.coursework.ui;

import com.eltech.coursework.controller.ControllableView;
import com.eltech.coursework.controller.ObjectController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameFieldView extends JPanel implements ControllableView {
    private final int width;
    private final int height;
    private final int padding;

    private List<ObjectController> objectControllers = new ArrayList<>();

    public GameFieldView(int width, int height, int padding) {
        super();
        this.width = width;
        this.height = height;
        this.padding = padding;
        this.setSize(width, height);
    }

    @Override
    public void addController(ObjectController controller) {
        if (!objectControllers.contains(controller)) {
            objectControllers.add(controller);
        }
        controller.assignToView(this);
    }

    @Override
    public void removeController(ObjectController controller) {
        objectControllers.remove(controller);
        controller.assignToView(null);
    }

    @Override
    public void paint(Graphics graphics) {
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);

        graphics.setColor(Color.BLACK);
        graphics.drawRect(padding, padding, width - padding * 2, height - padding * 2);

        int x1 = padding * 2;
        int y1 = padding * 2;
        int x2 = width - padding * 2;
        int y2 = height - padding * 2;
        Rectangle area = new Rectangle(x1, y1, x2 - x1, y2 - y1);

        for (ObjectController controller : objectControllers) {
            controller.paint((Graphics2D) graphics, area);
        }
    }


}
