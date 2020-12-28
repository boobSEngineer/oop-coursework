package com.eltech.coursework.controller;

import java.awt.*;

public class GameFieldController extends ObjectController {
    @Override
    public void paint(Graphics2D graphics, Rectangle area) {
        Color black = new Color(0x00, 0x77, 0);
        Color white = new Color(0xFF, 0xFF, 0xBB);

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                graphics.setColor((x + y) % 2 == 0 ? white : black);
                graphics.fillRect(
                        area.x + x * area.width / 8,
                        area.y + y * area.height / 8,
                        area.width / 8 + 1,
                        area.height / 8 + 1
                );
            }
        }

        graphics.setColor(Color.BLACK);
        graphics.drawRect(area.x, area.y, area.width, area.height);
    }

    @Override
    public boolean onClick(double x, double y) {
        return true;
    }
}
