package com.eltech.coursework.model.game;

import com.eltech.coursework.model.Figure;
import javafx.util.Pair;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public interface FigureStrategy extends Serializable {
    class FigureMoves {
        public final Figure figure;
        public final List<Pair<Integer, Integer>> eatingMoves = new ArrayList<>();
        public final List<Pair<Integer, Integer>> normalMoves = new ArrayList<>();

        FigureMoves(Figure figure) {
            this.figure = figure;
        }
    }

    FigureMoves getAvailableMoves(Figure figure);

    default void onMove(Figure figure, int x1, int y1, int x2, int y2) {

    }

    default void doAdditionalDrawing(Graphics2D graphics, Rectangle rect) {

    }
}
