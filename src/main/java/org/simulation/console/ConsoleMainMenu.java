package org.simulation.console;

import org.simulation.game.runner.Runner;
import org.simulation.console.renderer.MainMenuRenderer;

public class ConsoleMainMenu implements Menu {

    private static final String READ_RULES = "1";
    private static final String START = "2";
    private static final String EXIT = "3";

    private final Runner runner;
    private final Menu controlMenu;
    private final MainMenuRenderer mainMenuRenderer;
    private final InputProvider inputProvider;

    public ConsoleMainMenu(Runner runner,
                           Menu controlMenu,
                           MainMenuRenderer mainMenuRenderer,
                           InputProvider inputProvider) {
        this.runner = runner;
        this.controlMenu = controlMenu;
        this.mainMenuRenderer = mainMenuRenderer;
        this.inputProvider = inputProvider;
    }

    public void start() {
        while (true) {
            mainMenuRenderer.renderMenu();

            String choice = inputProvider.nextLine();
            switch (choice) {
                case READ_RULES -> mainMenuRenderer.renderRules();
                case START -> {
                    runner.start();
                    controlMenu.start();
                }
                case EXIT -> {
                    runner.stop();
                    mainMenuRenderer.printExitMessage();
                    return;
                }
                default -> mainMenuRenderer.printInvalidChoice();
            }
        }
    }
}
