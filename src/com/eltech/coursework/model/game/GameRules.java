package com.eltech.coursework.model.game;

import com.eltech.coursework.model.Figure;
import com.eltech.coursework.model.GameField;
import javafx.util.Pair;

import java.util.List;

public interface GameRules {
    void setupNewGame(GameField field);
    List<Figure> getAvailableFigures();
    List<Pair<Integer, Integer>> getAvailableMoves(Figure figure);
    void doMove(Figure figure, int x, int y);
}
