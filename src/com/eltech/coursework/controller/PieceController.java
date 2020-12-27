package com.eltech.coursework.controller;

import javax.swing.border.StrokeBorder;
import java.awt.*;

public class PieceController extends ObjectController {
    @Override
    public void paint(Graphics2D graphics, Rectangle area) {
        int x = 2;
        int y = 3;


        int padding = area.width / 8 / 10;
        Rectangle rect = new Rectangle(
                area.x + x * area.width / 8 + padding,
                area.y + y * area.height / 8 + padding,
                area.width / 8 - padding * 2,
                area.height / 8 - padding * 2
        );

        graphics.setColor(Color.WHITE);
        graphics.fillOval(rect.x, rect.y, rect.width, rect.height);
        graphics.setColor(Color.BLACK);
        graphics.setStroke(new BasicStroke(2));
        graphics.drawOval(rect.x, rect.y, rect.width, rect.height);
        graphics.setStroke(new BasicStroke(1));
    }
}
