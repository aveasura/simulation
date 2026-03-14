package org.simulation.console;

import org.simulation.console.renderer.MainMenuRenderer;
import org.simulation.game.runner.Runner;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class ConsoleMainMenu implements Menu {

    private static final String READ_RULES = "1";
    private static final String START = "2";
    private static final String EXIT = "3";

    private boolean exitRequested;

    private final Runner runner;
    private final Menu controlMenu;
    private final MainMenuRenderer renderer;
    private final InputProvider input;

    public ConsoleMainMenu(Runner runner,
                           Menu controlMenu,
                           MainMenuRenderer renderer,
                           InputProvider input) {
        this.runner = Objects.requireNonNull(runner, "runner must not be null");
        this.controlMenu = Objects.requireNonNull(controlMenu, "controlMenu must not be null");
        this.renderer = Objects.requireNonNull(renderer, "renderer must not be null");
        this.input = Objects.requireNonNull(input, "input must not be null");
    }

    public void start() {
        resetExitFlag();

        Map<String, Runnable> commandsByKey = createCommands();
        MenuLoop menuLoop = createMenuLoop(commandsByKey);
        menuLoop.start();
    }

    private void resetExitFlag() {
        exitRequested = false;
    }

    private Map<String, Runnable> createCommands() {
        Map<String, Runnable> commandsByKey = new LinkedHashMap<>();
        commandsByKey.put(READ_RULES, renderer::renderRules);
        commandsByKey.put(START, this::startSimulation);
        commandsByKey.put(EXIT, this::exit);
        return commandsByKey;
    }

    private void startSimulation() {
        runner.start();
        controlMenu.start();
    }

    private void exit() {
        runner.stop();
        renderer.renderExitMessage();
        exitRequested = true;
    }

    private MenuLoop createMenuLoop(Map<String, Runnable> commandsByKey) {
        return new MenuLoop(
                input,
                commandsByKey,
                renderer::renderMenu,
                renderer::renderInvalidChoice,
                this::handleInputClosed,
                () -> exitRequested,
                () -> {
                }
        );
    }

    private void handleInputClosed() {
        runner.stop();
        renderer.renderInputClosed();
    }
}
