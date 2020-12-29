package com.eltech.coursework.model.game;

import com.eltech.coursework.model.Figure;
import com.eltech.coursework.model.GameField;
import javafx.util.Pair;

public class PawnStrategy implements FigureStrategy {
    private boolean isPositionInsideField(int x, int y) {
        return x >= 0 && y >= 0 && x < 8 && y < 8;
    }

    @Override
    public FigureMoves getAvailableMoves(Figure figure) {
        FigureMoves result = new FigureMoves(figure);

        GameField field = figure.getField();
        int x = figure.getX();
        int y = figure.getY();
        for (int xi = -1; xi < 2; xi += 2) {
            for (int yi = -1; yi < 2; yi += 2) {
                if (isPositionInsideField(x + xi, y + yi)) {
                    Figure eatFigure = field.getFigure(x + xi, y + yi);
                    if (eatFigure == null) {
                        if (figure.getTeam() == Figure.Team.WHITE && yi < 0 || figure.getTeam() == Figure.Team.BLACK && yi > 0) {
                            result.normalMoves.add(new Pair<>(x + xi, y + yi));
                        }
                    } else if (eatFigure.getTeam() != figure.getTeam() &&
                            isPositionInsideField(x + xi * 2, y + yi * 2) &&
                            field.getFigure(x + xi * 2, y + yi * 2) == null) {
                        result.eatingMoves.add(new Pair<>(x + xi * 2, y + yi * 2));
                    }
                }
            }
        }

        return result;
    }

    @Override
    public void onMove(Figure figure, int x1, int y1, int x2, int y2) {
        if (figure.getTeam() == Figure.Team.WHITE && y2 == 0 || figure.getTeam() == Figure.Team.BLACK && y2 == 7) {
            figure.setStrategy(new QueenStrategy());
        }
    }
}
