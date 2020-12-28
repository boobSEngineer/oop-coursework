package com.eltech.coursework.model.game;

import com.eltech.coursework.model.Figure;
import com.eltech.coursework.model.GameField;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Shashki implements GameRules {
    private Figure.Team currentTeam = Figure.Team.WHITE;

    @Override
    public void setupNewGame(GameField field) {
        currentTeam = Figure.Team.WHITE;
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                field.setFigure(x, y, null);
                if ((x + y) % 2 == 1) {
                    if (y < 3) {
                        field.setFigure(x, y, new Figure(field, Figure.Team.BLACK));
                    } else if (y > 4) {
                        field.setFigure(x, y, new Figure(field, Figure.Team.WHITE));
                    }
                }
            }
        }
    }

    @Override
    public List<Figure> getAvailableFigures(GameField field) {
        System.out.println(currentTeam);
        return field.getAllFigures().stream().filter(figure -> figure.getTeam() == currentTeam).collect(Collectors.toList());
    }

    @Override
    public List<Pair<Integer, Integer>> getAvailableMoves(Figure figure) {
        List<Pair<Integer, Integer>> moves = new ArrayList<>();

        GameField field = figure.getField();
        int x = figure.getX();
        int y = figure.getY();
        for (int xi = -1; xi < 2; xi += 2) {
            for (int yi = -1; yi < 2; yi += 2) {
                if (field.getFigure(x + xi, y + yi) == null) {
                    moves.add(new Pair<>(x + xi, y + yi));
                } else if (field.getFigure(x + xi * 2, y + yi * 2) == null) {
                    moves.add(new Pair<>(x + xi * 2, y + yi * 2));
                }
            }
        }

        return moves;
    }

    @Override
    public void doMove(Figure figure, int x, int y) {
        figure.move(x, y);
        currentTeam = (currentTeam == Figure.Team.WHITE ? Figure.Team.BLACK : Figure.Team.WHITE);
    }
}
