package com.eltech.coursework.model;

import com.eltech.coursework.controller.FigureController;
import com.eltech.coursework.model.game.FigureStrategy;
import com.eltech.coursework.model.game.PawnStrategy;

public class Figure {
    public enum Team {
        WHITE,
        BLACK
    }

    private final FigureController controller = new FigureController(this);
    private final GameField field;

    private final Team team;
    private FigureStrategy strategy = new PawnStrategy();
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
        int lastX = this.x;
        int lastY = this.y;
        this.field.setFigure(this.x, this.y, null);
        this.field.setFigure(x, y, this);
        strategy.onMove(this, lastX, lastY, x, y);
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

    public FigureStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(FigureStrategy strategy) {
        this.strategy = strategy;
    }

    public boolean processClick() {
        if (field.getSelectedFigure() == null && field.getAvailableFigures().contains(this)) {
            field.processClickOnAvailableFigure(this);
            return true;
        }
        return false;
    }


}
