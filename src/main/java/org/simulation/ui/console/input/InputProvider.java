package org.simulation.ui.console.input;

public interface InputProvider extends AutoCloseable {

    String nextLine();

    @Override
    void close();
}
