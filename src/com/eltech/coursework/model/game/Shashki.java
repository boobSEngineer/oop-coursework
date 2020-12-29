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


    class FigureMoves {
        public final Figure figure;
        public final List<Pair<Integer, Integer>> eatingMoves = new ArrayList<>();
        public final List<Pair<Integer, Integer>> normalMoves = new ArrayList<>();

        FigureMoves(Figure figure) {
            this.figure = figure;
        }
    }

    private boolean isPositionInsideField(int x, int y) {
        return x >= 0 && y >= 0 && x < 8 && y < 8;
    }

    private FigureMoves getAvailableMovesData(Figure figure) {
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
        FigureMoves moves = getAvailableMovesData(figure);
        return moves.eatingMoves.size() > 0 ? moves.eatingMoves : moves.normalMoves;
    }

    @Override
    public void doMove(Figure figure, int x, int y) {
        boolean hasExtraMove = false;
        if (Math.abs(x - figure.getX()) > 1 || Math.abs(y - figure.getY()) > 1) {
            int eatX = (figure.getX() + x) / 2;
            int eatY = (figure.getY() + y) / 2;
            figure.move(x, y);
            if (figure.getField().getFigure(eatX, eatY) != null) {
                figure.getField().setFigure(eatX, eatY, null);
                if (getAvailableMovesData(figure).eatingMoves.size() > 0) {
                    hasExtraMove = true;
                }
            }
        } else {
            figure.move(x, y);
        }
        if (!hasExtraMove) {
            currentTeam = (currentTeam == Figure.Team.WHITE ? Figure.Team.BLACK : Figure.Team.WHITE);
        }
        checkForVictory(figure.getField());
    }

    private void checkForVictory(GameField field) {
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
