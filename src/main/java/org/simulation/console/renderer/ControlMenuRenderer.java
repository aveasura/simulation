package org.simulation.console.renderer;

public interface ControlMenuRenderer {

    void printPaused();

    void printResumed();

    void printStopped();

    void printInvalidChoice();

    void printInputClosed();

    void printFinished();
}
