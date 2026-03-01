package org.simulation.console.renderer;

public interface MainMenuRenderer {

    void renderMenu();

    void renderRules();

    void printExitMessage();

    void printInvalidChoice();

    void printInputClosed();
}
