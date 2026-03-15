package org.simulation.todoMenu;

public interface Menu {

    void add(String text, Runnable action);

    void show();

    void select();
}
