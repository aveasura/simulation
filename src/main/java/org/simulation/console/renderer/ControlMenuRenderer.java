package org.simulation.console.renderer;

public interface ControlMenuRenderer {

    void printPaused();

    void printResumed();

    void printStoppedByUser();

    void printInvalidChoice();

    void printInputClosed();

    void printTerminalStateReached();
}
