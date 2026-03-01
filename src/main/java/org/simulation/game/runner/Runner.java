package org.simulation.game.runner;

public interface Runner {

    void start();

    void pause();

    void resume();

    void stop();

    boolean isRunning();
}
