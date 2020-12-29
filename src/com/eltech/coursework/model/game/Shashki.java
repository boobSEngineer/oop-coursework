package com.eltech.coursework.model.game;

import com.eltech.coursework.model.Figure;
import com.eltech.coursework.model.GameField;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

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

    public FigureStrategy.FigureMoves getAvailableMovesData(Figure figure) {
        return figure.getStrategy().getAvailableMoves(figure);
    }

    @Override
    public List<Figure> getAvailableFigures(GameField field) {
        List<Figure> allFiguresOfCurrentTeam = new ArrayList<>();
        List<Figure> allFiguresWithEatingMoves = new ArrayList<>();

        for (Figure figure : field.getAllFigures()) {
            if (figure.getTeam() == currentTeam) {
                allFiguresOfCurrentTeam.add(figure);
                if (getAvailableMovesData(figure).eatingMoves.size() > 0) {
                    allFiguresWithEatingMoves.add(figure);
                }
            }
        }

        return allFiguresWithEatingMoves.size() > 0 ? allFiguresWithEatingMoves : allFiguresOfCurrentTeam;
    }

    @Override
    public List<Pair<Integer, Integer>> getAvailableMoves(Figure figure) {
        FigureStrategy.FigureMoves moves = getAvailableMovesData(figure);
        return moves.eatingMoves.size() > 0 ? moves.eatingMoves : moves.normalMoves;
    }

    private List<Pair<Integer, Integer>> getEatingPositions(Figure figure, int x2, int y2) {
        GameField field = figure.getField();
        List<Pair<Integer, Integer>> eatingPositions = new ArrayList<>();
        for (int x = figure.getX(), y = figure.getY(); x != x2 && y != y2;) {
            Figure eatingFigure = field.getFigure(x, y);
            if (eatingFigure != null && eatingFigure.getTeam() != figure.getTeam()) {
                eatingPositions.add(new Pair<>(x, y));
            }
            if (x < x2) x++;
            else x--;
            if (y < y2) y++;
            else y--;
        }

        return eatingPositions;
    }

    @Override
    public void doMove(Figure figure, int x, int y) {
        boolean hasExtraMove = false;
        List<Pair<Integer, Integer>> eatingPositions = getEatingPositions(figure, x, y);
        if (eatingPositions.size() > 0) {
            for (Pair<Integer, Integer> eatPos : eatingPositions) {
                figure.getField().setFigure(eatPos.getKey(), eatPos.getValue(), null);
            }
            figure.move(x, y);
            if (getAvailableMovesData(figure).eatingMoves.size() > 0) {
                hasExtraMove = true;
            }
        } else {
            figure.move(x, y);
        }
        if (!hasExtraMove) {
            currentTeam = (currentTeam == Figure.Team.WHITE ? Figure.Team.BLACK : Figure.Team.WHITE);
        }
    }

    @Override
    public void checkForVictory(GameField field) {
        int whiteCount = 0;
        int blackCount = 0;
        for (Figure figure : field.getAllFigures()) {
            if (figure.getTeam() == Figure.Team.WHITE) {
                whiteCount++;
            } else if (figure.getTeam() == Figure.Team.BLACK) {
                blackCount++;
            }
        }
        if (blackCount == 0) {
            field.reportVictory("White wins!");
        } else if (whiteCount == 0) {
            field.reportVictory("Black wins!");
        }
    }
}
