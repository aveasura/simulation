package org.simulation.console.renderer;

public interface MenuRenderer {

    void renderMainMenu();

    void renderRules();

    void printExitMessage();

    void printInvalidMainMenuChoice();
}
