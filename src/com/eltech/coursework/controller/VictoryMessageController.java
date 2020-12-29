package com.eltech.coursework.controller;

import com.eltech.coursework.model.GameField;

import java.awt.*;

public class VictoryMessageController extends ObjectController {
    private final GameField field;
    private String message = "White Wins!";

    public VictoryMessageController(GameField field) {
        this.field = field;
        this.setZIndex(2);
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void paint(Graphics2D graphics, Rectangle area) {
        int cx = (int) area.getCenterX();
        int cy = (int) area.getCenterY();
        int w = (int) (area.width / 1.5);
        int h = (int) (area.height / 3.0);
        graphics.setStroke(new BasicStroke(4));
        graphics.setColor(Color.RED);
        graphics.fillRect(cx - w / 2, cy - h / 2, w, h);
        graphics.setColor(Color.YELLOW);
        graphics.drawRect(cx - w / 2, cy - h / 2, w, h);
        graphics.setStroke(new BasicStroke(1));

        int size = area.width / message.length();
        Font font = new Font(graphics.getFont().getFontName(), Font.BOLD, size);
        graphics.setFont(font);
        graphics.setColor(Color.YELLOW);
        FontMetrics metrics = graphics.getFontMetrics();
        graphics.drawString(message, cx - metrics.stringWidth(message) / 2, cy + metrics.getHeight() / 4);
    }

    @Override
    public boolean onClick(double x, double y) {
        field.restartGame();
        return true;
    }
}
