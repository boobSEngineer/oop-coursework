package com.eltech.coursework;

import com.eltech.coursework.controller.ControllableView;
import com.eltech.coursework.model.GameField;
import com.eltech.coursework.model.game.Shashki;
import com.eltech.coursework.model.saves.GameSaver;
import com.eltech.coursework.ui.GameFieldView;

import java.io.File;

public class GameContext {
    private static final GameContext instance = new GameContext();

    public static GameContext getInstance() {
        return instance;
    }


    private final GameSaver gameSaver = new GameSaver(new File("current_game.bin"));
    private ControllableView currentView;
    private GameField currentField;

    private GameContext() {

    }

    public ControllableView getCurrentView() {
        return currentView;
    }

    public GameField getCurrentField() {
        return currentField;
    }

    public GameSaver getGameSaver() {
        return gameSaver;
    }

    public void setCurrentView(ControllableView currentView) {
        this.currentView = currentView;
        if (currentField != null) {
            currentField.reAddAllControllers(currentView);
        }
    }

    public void setCurrentField(GameField currentField) {
        this.currentField = currentField;
        currentField.setListener(gameSaver);
        if (currentView != null) {
            currentField.reAddAllControllers(currentView);
        }
    }

    public GameField newShahkiGame() {
        GameField field = new GameField();
        field.setGameRules(new Shashki());
        field.newGame();
        return field;
    }

    public void loadSavedGame() {
        setCurrentField(gameSaver.read(this::newShahkiGame, Throwable::printStackTrace));
    }
}
