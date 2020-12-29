package com.eltech.coursework.model.game;

import com.eltech.coursework.model.Figure;
import com.eltech.coursework.model.GameField;
import javafx.util.Pair;

import java.awt.*;

public class QueenStrategy implements FigureStrategy {
    private void addMovesInDirection(Figure figure, FigureMoves moves, int dx, int dy) {
        GameField field = figure.getField();
        int x = figure.getX() + dx;
        int y = figure.getY() + dy;
        boolean eat = false;
        while (x >= 0 && y >= 0 && x < 8 && y < 8) {
            Figure figureOnPath = field.getFigure(x, y);
            if (figureOnPath != null) {
                if (eat) {
                    break;
                }
                if (figureOnPath.getTeam() == figure.getTeam()) {
                    break;
                } else {
                    eat = true;
                }
            } else {
                if (eat) {
                    moves.eatingMoves.add(new Pair<>(x, y));
                } else {
                    moves.normalMoves.add(new Pair<>(x, y));
                }
            }
            x += dx;
            y += dy;
        }
    }

    @Override
    public FigureMoves getAvailableMoves(Figure figure) {
        FigureMoves moves = new FigureMoves(figure);
        for (int xi = -1; xi < 2; xi += 2) {
            for (int yi = -1; yi < 2; yi += 2) {
                addMovesInDirection(figure, moves, xi, yi);
            }
        }
        return moves;
    }

    @Override
    public void doAdditionalDrawing(Graphics2D graphics, Rectangle rect) {
        int p = 5;
        graphics.drawOval(rect.x + p, rect.y + p, rect.width - p * 2, rect.height - p * 2);
    }
}
