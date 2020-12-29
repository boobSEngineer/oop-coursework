package com.eltech.coursework.model;

import com.eltech.coursework.controller.ControllableView;
import com.eltech.coursework.controller.GameFieldController;
import com.eltech.coursework.controller.VictoryMessageController;
import com.eltech.coursework.model.game.GameRules;
import javafx.util.Pair;

import javax.swing.text.View;
import java.util.ArrayList;
import java.util.List;

public class GameField {
    private final GameFieldController controller = new GameFieldController(this);
    private final VictoryMessageController victoryMessageController = new VictoryMessageController(this);
    private GameRules gameRules;

    private final Figure[] field = new Figure[64];

    public GameFieldController getController() {
        return controller;
    }

    public void setGameRules(GameRules gameRules) {
        this.gameRules = gameRules;
    }

    public GameRules getGameRules() {
        return gameRules;
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

    public List<Figure> getAllFigures() {
        List<Figure> list = new ArrayList<>();
        for (int i = 0; i < 64; i++) {
            if (field[i] != null) {
                list.add(field[i]);
            }
        }
        return list;
    }


    private List<Figure> availableFigures = new ArrayList<>();
    private List<Pair<Integer, Integer>> availableMoves = new ArrayList<>();
    private Figure selectedFigure = null;

    public List<Figure> getAvailableFigures() {
        return availableFigures;
    }

    public List<Pair<Integer, Integer>> getAvailableMoves() {
        return availableMoves;
    }

    public Figure getSelectedFigure() {
        return selectedFigure;
    }

    public void newGame() {
        availableFigures = new ArrayList<>();
        availableMoves = new ArrayList<>();
        selectedFigure = null;

        gameRules.setupNewGame(this);
        startNewMove();
    }

    private void startNewMove() {
        availableFigures = gameRules.getAvailableFigures(this);
        controller.refreshView();
    }

    public void processClickOnAvailableFigure(Figure figure) {
        availableMoves = gameRules.getAvailableMoves(figure);
        selectedFigure = figure;
        controller.refreshView();
    }

    public void processClickOnEmptyCell(int x, int y) {
        Pair<Integer, Integer> pos = new Pair<>(x, y);
        if (availableMoves.contains(pos)) {
            gameRules.doMove(selectedFigure, x, y);
            startNewMove();
        }
        availableMoves = new ArrayList<>();
        selectedFigure = null;
        controller.refreshView();
    }

    public void reportVictory(String message) {
        ControllableView view = controller.getView();
        victoryMessageController.setMessage(message);
        view.addController(victoryMessageController);
    }

    public void restartGame() {
        ControllableView view = controller.getView();
        view.removeController(victoryMessageController);
        newGame();
    }
}
