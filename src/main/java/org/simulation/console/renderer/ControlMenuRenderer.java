package org.simulation.console.renderer;

public interface ControlMenuRenderer {

    void renderPaused();

    void renderResumed();

    void renderStoppedByUser();

    void renderInvalidChoice();

    void renderInputClosed();

    void renderTerminalStateReached();
}
