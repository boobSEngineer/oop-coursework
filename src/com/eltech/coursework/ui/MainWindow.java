package com.eltech.coursework.ui;

import com.eltech.coursework.controller.GameFieldController;
import com.eltech.coursework.controller.PieceController;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {
    private final GameFieldView gameField;

    public MainWindow(String title, int width, int height) {
        super();
        setTitle(title);
        setSize(width, height);
        setMinimumSize(new Dimension(width, height));

        gameField = new GameFieldView(width - 100, height - 100, 10);
        gameField.addObject(new GameFieldController());
        gameField.addObject(new PieceController());
        add(gameField);
    }


}
