package com.eltech.coursework;

import com.eltech.coursework.ui.MainWindow;

public class Main {
    public static void main(String[] args) {
        GameContext.getInstance().loadSavedGame();
        MainWindow window = new MainWindow("Shashki", 600, 600);
        window.setVisible(true);

    }
}
