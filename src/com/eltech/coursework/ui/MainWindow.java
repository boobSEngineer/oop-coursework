package com.eltech.coursework.ui;

import com.eltech.coursework.GameContext;
import com.eltech.coursework.model.GameField;
import com.eltech.coursework.model.game.Shashki;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class MainWindow extends JFrame {
    private final GameFieldView gameFieldView;

    public MainWindow(String title, int width, int height) {
        super();
        setTitle(title);
        setSize(width, height);
        setMinimumSize(new Dimension(width, height));
        setLayout(new BorderLayout());

        gameFieldView = new GameFieldView(width - 100, height - 100, 10);
        add(gameFieldView);
        GameContext.getInstance().setCurrentView(gameFieldView);

        Button restart = new Button("RESTART");
        restart.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(this, "Do you really want to restart the game?") == JOptionPane.OK_OPTION) {
                GameContext.getInstance().getCurrentField().newGame();
            }
        });
        add(restart, BorderLayout.SOUTH);
    }


}
