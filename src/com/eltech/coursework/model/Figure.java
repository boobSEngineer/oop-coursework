package com.eltech.coursework.model;

import com.eltech.coursework.controller.FigureController;

public class Figure {
    public enum Team {
        WHITE,
        BLACK
    }

    private final FigureController controller = new FigureController(this);
    private final GameField field;

    private final Team team;
    private int x;
    private int y;

    public Figure(GameField field, Team team) {
        this.field = field;
        this.team = team;
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move(int x, int y) {
        this.field.setFigure(this.x, this.y, null);
        this.field.setFigure(x, y, this);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Team getTeam() {
        return team;
    }

    public GameField getField() {
        return field;
    }

    public FigureController getController() {
        return controller;
    }

    public void processClick() {
        System.out.println("i was clicked");
    }
}
