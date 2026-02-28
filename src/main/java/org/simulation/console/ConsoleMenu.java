package org.simulation.console;

import org.simulation.console.renderer.MenuRenderer;
import org.simulation.game.Simulation;

public class ConsoleMenu {

    private static final String READ_RULES = "1";
    private static final String START = "2";
    private static final String EXIT = "3";

    private final Simulation simulation;
    private final MenuRenderer menuRenderer;
    private final InputProvider inputProvider;

    public ConsoleMenu(Simulation simulation, MenuRenderer menuRenderer, InputProvider inputProvider) {
        this.simulation = simulation;
        this.menuRenderer = menuRenderer;
        this.inputProvider = inputProvider;
    }

    public void start() {
        while (true) {
            menuRenderer.renderMainMenu();

            String choice = inputProvider.nextLine();
            switch (choice) {
                case READ_RULES -> menuRenderer.renderRules();
                case START -> simulation.startSimulation();
                case EXIT -> {
                    menuRenderer.printExitMessage();
                    return;
                }
                default -> menuRenderer.printInvalidMainMenuChoice();
            }
        }
    }
}
