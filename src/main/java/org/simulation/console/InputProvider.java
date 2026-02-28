package org.simulation.console;

public interface InputProvider extends AutoCloseable {

    String nextLine();

    @Override
    void close();
}
