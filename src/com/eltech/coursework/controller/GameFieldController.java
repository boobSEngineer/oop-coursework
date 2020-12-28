package com.eltech.coursework.controller;

import com.eltech.coursework.model.GameField;
import javafx.util.Pair;

import java.awt.*;

public class GameFieldController extends ObjectController {
    private final GameField field;

    public GameFieldController(GameField field) {
        this.field = field;
    }

    @Override
    public void paint(Graphics2D graphics, Rectangle area) {
        Color black = new Color(0x00, 0x77, 0);
        Color white = new Color(0xFF, 0xFF, 0xBB);
        Color selected = new Color(0xFF, 0, 0);
        Color available = new Color(0xFF, 0, 0);

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Color color = (x + y) % 2 == 0 ? white : black;
                if (field.getAvailableMoves().contains(new Pair<>(x, y))) {
                    color = available;
                }
                graphics.setColor(color);
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
        int ix = (int) (x * 8);
        int iy = (int) (y * 8);
        field.processClickOnEmptyCell(ix, iy);
        return true;
    }
}
