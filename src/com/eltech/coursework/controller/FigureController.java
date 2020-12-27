package com.eltech.coursework.controller;

import com.eltech.coursework.model.Figure;

import java.awt.*;

public class FigureController extends ObjectController {
    private final Figure figure;

    public FigureController(Figure figure) {
        this.figure = figure;
    }

    @Override
    public void paint(Graphics2D graphics, Rectangle area) {
        int x = figure.getX();
        int y = figure.getY();
        Figure.Team team = figure.getTeam();

        int padding = area.width / 8 / 10;
        Rectangle rect = new Rectangle(
                area.x + x * area.width / 8 + padding,
                area.y + y * area.height / 8 + padding,
                area.width / 8 - padding * 2,
                area.height / 8 - padding * 2
        );

        graphics.setColor(team == Figure.Team.WHITE ? Color.WHITE : Color.BLACK);
        graphics.fillOval(rect.x, rect.y, rect.width, rect.height);
        graphics.setColor(team == Figure.Team.WHITE ? Color.BLACK : Color.WHITE);
        graphics.setStroke(new BasicStroke(2));
        graphics.drawOval(rect.x, rect.y, rect.width, rect.height);
        graphics.setStroke(new BasicStroke(1));
    }
}
