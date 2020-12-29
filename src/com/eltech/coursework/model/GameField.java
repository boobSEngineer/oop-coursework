package com.eltech.coursework.model;

import com.eltech.coursework.controller.ControllableView;
import com.eltech.coursework.controller.GameFieldController;
import com.eltech.coursework.controller.VictoryMessageController;
import com.eltech.coursework.model.game.GameRules;
import javafx.util.Pair;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameField implements Serializable {
    public interface ChangeListener {
        void onChanged(GameField field);
    }


    private transient GameFieldController controller;
    private transient VictoryMessageController victoryMessageController;
    private transient ChangeListener listener = null;

    private GameRules gameRules;

    private final Figure[] field = new Figure[64];

    public void setListener(ChangeListener listener) {
        this.listener = listener;
    }

    public GameFieldController getController() {
        if (controller == null) {
            controller = new GameFieldController(this);
        }
        return controller;
    }

    public VictoryMessageController getVictoryMessageController() {
        if (victoryMessageController == null) {
            victoryMessageController = new VictoryMessageController(this);
        }
        return victoryMessageController;
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

        ControllableView view = getController().getView();
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

    public void reAddAllControllers(ControllableView view) {
        view.addController(getController());
        for (Figure figure : getAllFigures()) {
            view.addController(figure.getController());
        }
        checkForVictory();
    }

    public void clearAllFigures() {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                setFigure(x, y, null);
            }
        }
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
        onChanged();
        startNewMove();
    }

    public void checkForVictory() {
        gameRules.checkForVictory(this);
    }

    private void startNewMove() {
        availableFigures = gameRules.getAvailableFigures(this);
        getController().refreshView();
        onChanged();
    }

    public void processClickOnAvailableFigure(Figure figure) {
        availableMoves = gameRules.getAvailableMoves(figure);
        selectedFigure = figure;
        getController().refreshView();
        onChanged();
    }

    public void processClickOnEmptyCell(int x, int y) {
        Pair<Integer, Integer> pos = new Pair<>(x, y);
        if (availableMoves.contains(pos)) {
            gameRules.doMove(selectedFigure, x, y);
            gameRules.checkForVictory(this);
            startNewMove();
        }
        availableMoves = new ArrayList<>();
        selectedFigure = null;
        getController().refreshView();
        onChanged();
    }

    public void reportVictory(String message) {
        VictoryMessageController victoryMessageController = getVictoryMessageController();
        ControllableView view = getController().getView();
        if (view != null) {
            victoryMessageController.setMessage(message);
            view.addController(victoryMessageController);
        }
        onChanged();
    }

    public void restartGame() {
        ControllableView view = getController().getView();
        view.removeController(getVictoryMessageController());
        newGame();
    }

    private void onChanged() {
        if (listener != null) {
            listener.onChanged(this);
        }
    }

}
