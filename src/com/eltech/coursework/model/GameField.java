package com.eltech.coursework.model;

import com.eltech.coursework.controller.ControllableView;
import com.eltech.coursework.controller.GameFieldController;

public class GameField {
    private final GameFieldController controller = new GameFieldController(this);

    private final Figure[] field = new Figure[64];

    public GameFieldController getController() {
        return controller;
    }

    public Figure getFigure(int x, int y) {
        if (x < 0 || y < 0 || x >= 8 || y >= 8) {
            return null;
        }
        return field[x + y * 8];
    }

    public void setFigure(int x, int y, Figure figure) {
        if (x < 0 || y < 0 || x >= 8 || y >= 8) {
            throw new IndexOutOfBoundsException(x + " " + y);
        }
        Figure last = field[x + y * 8];
        field[x + y * 8] = figure;
        if (figure != null) {
            figure.setPos(x, y);
        }

        ControllableView view = controller.getView();
        if (view != null) {
            if (last != null) {
                view.removeController(last.getController());
            }
            if (figure != null) {
                view.addController(figure.getController());
            }
        }
    }

    public void newGame() {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                this.setFigure(x, y, null);
                if ((x + y) % 2 == 1) {
                    if (y < 3) {
                        this.setFigure(x, y, new Figure(this, Figure.Team.BLACK));
                    } else if (y > 4) {
                        this.setFigure(x, y, new Figure(this, Figure.Team.WHITE));
                    }
                }
            }
        }
    }

    public void processClickOnEmptyCell(int x, int y) {
        System.out.println("empty cell click: " + x + " " + y);
    }
}
