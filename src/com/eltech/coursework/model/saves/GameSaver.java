package com.eltech.coursework.model.saves;

import com.eltech.coursework.model.GameField;

import java.io.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class GameSaver implements GameField.ChangeListener {
    private final File file;

    public GameSaver(File file) {
        this.file = file;
    }

    public synchronized void save(GameField field, Consumer<Exception> exceptionConsumer) {
        try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(file))) {
            stream.writeObject(field);
        } catch (IOException e) {
            if (exceptionConsumer != null) {
                exceptionConsumer.accept(e);
            }
        }
    }

    public synchronized GameField read(Supplier<GameField> fallback, Consumer<Exception> exceptionConsumer) {
        if (file.exists()) {
            try (ObjectInputStream stream = new ObjectInputStream(new FileInputStream(file))) {
                return (GameField) stream.readObject();
            } catch (IOException | ClassNotFoundException | ClassCastException e) {
                if (exceptionConsumer != null) {
                    exceptionConsumer.accept(e);
                }
            }
        }
        if (fallback != null) {
            return fallback.get();
        }
        return null;
    }

    @Override
    public void onChanged(GameField field) {
        save(field, Throwable::printStackTrace);
    }
}
