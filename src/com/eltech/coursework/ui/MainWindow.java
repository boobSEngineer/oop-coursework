package com.eltech.coursework.ui;

import com.eltech.coursework.controller.GameFieldController;
import com.eltech.coursework.controller.FigureController;
import com.eltech.coursework.model.GameField;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private final GameFieldView gameField;

    public MainWindow(String title, int width, int height) {
        super();
        setTitle(title);
        setSize(width, height);
        setMinimumSize(new Dimension(width, height));

        GameField field = new GameField();
        gameField = new GameFieldView(width - 100, height - 100, 10);
        gameField.addController(field.getController());
        add(gameField);

        field.newGame();
    }


}
