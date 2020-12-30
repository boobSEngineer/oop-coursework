package com.eltech.coursework.ui;

import com.eltech.coursework.controller.ControllableView;
import com.eltech.coursework.controller.ObjectController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GameFieldView extends JPanel implements ControllableView {
    private final int width;
    private final int height;
    private final int padding;

    private final List<ObjectController> objectControllers = new ArrayList<>();
    private Rectangle area;

    public GameFieldView(int width, int height, int padding) {
        super();
        this.width = width;
        this.height = height;
        this.padding = padding;
        this.setSize(width, height);

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                double x = e.getX();
                double y = e.getY();
                x = (x - area.x) / area.width;
                y = (y - area.y) / area.height;

                for (int i = objectControllers.size() - 1; i >= 0; i--) {
                    ObjectController controller = objectControllers.get(i);
                    if (controller.onClick(x, y)) {
                        break;
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    @Override
    public void addController(ObjectController controller) {
        if (!objectControllers.contains(controller)) {
            objectControllers.add(controller);
            objectControllers.sort(Comparator.comparingInt(ObjectController::getZIndex));
            controller.assignToView(this);
            queueRepaint();
        }
    }

    @Override
    public void removeController(ObjectController controller) {
        objectControllers.remove(controller);
        controller.assignToView(null);
        queueRepaint();
    }

    private boolean isRepaintQueued = false;

    private void queueRepaint() {
        isRepaintQueued = true;
        SwingUtilities.invokeLater(() -> {
            if (isRepaintQueued) {
                isRepaintQueued = false;
                repaint();
            }
        });
    }

    @Override
    public void refresh() {
        queueRepaint();
    }

    @Override
    public void paint(Graphics graphics) {
        ((Graphics2D) graphics).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);

        graphics.setColor(Color.BLACK);
        graphics.drawRect(padding, padding, width - padding * 2, height - padding * 2);

        int x1 = padding * 2;
        int y1 = padding * 2;
        int x2 = width - padding * 2;
        int y2 = height - padding * 2;
        area = new Rectangle(x1, y1, x2 - x1, y2 - y1);

        for (ObjectController controller : objectControllers) {
            controller.paint((Graphics2D) graphics, area);
        }
    }

}
