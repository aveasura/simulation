package org.simulation.console;

import org.simulation.console.renderer.MainMenuRenderer;
import org.simulation.game.runner.Runner;

import java.util.Objects;

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
        this.runner = Objects.requireNonNull(runner, "runner must not be null");
        this.controlMenu = Objects.requireNonNull(controlMenu, "controlMenu must not be null");
        this.mainMenuRenderer = Objects.requireNonNull(mainMenuRenderer, "mainMenuRenderer must not be null");
        this.inputProvider = Objects.requireNonNull(inputProvider, "inputProvider must not be null");
    }

    public void start() {
        try {
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
        } catch (IllegalStateException e) {
            runner.stop();
            mainMenuRenderer.printInputClosed();
        }
    }
}
