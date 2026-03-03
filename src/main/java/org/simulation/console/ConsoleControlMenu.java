package org.simulation.console;

import org.simulation.console.renderer.ControlMenuRenderer;
import org.simulation.game.runner.Runner;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class ConsoleControlMenu implements Menu {

    private boolean stoppedByUser;

    private static final String PAUSE = "1";
    private static final String RESUME = "2";
    private static final String STOP = "3";

    private final Runner runner;
    private final InputProvider input;
    private final ControlMenuRenderer renderer;

    public ConsoleControlMenu(Runner runner,
                              InputProvider input,
                              ControlMenuRenderer renderer) {
        this.runner = Objects.requireNonNull(runner, "runner must not be null");
        this.input = Objects.requireNonNull(input, "input must not be null");
        this.renderer = Objects.requireNonNull(renderer, "renderer must not be null");
    }

    @Override
    public void start() {
        resetStopFlag();

        Map<String, Runnable> commandsByKey = createCommands();
        MenuLoop menuLoop = createMenuLoop(commandsByKey);
        menuLoop.start();
    }

    private void resetStopFlag() {
        stoppedByUser = false;
    }

    private Map<String, Runnable> createCommands() {
        Map<String, Runnable> commandByKey = new LinkedHashMap<>();
        commandByKey.put(PAUSE, this::pause);
        commandByKey.put(RESUME, this::resume);
        commandByKey.put(STOP, this::stopByUser);
        return commandByKey;
    }

    private void pause() {
        runner.pause();
        renderer.printPaused();
    }

    private void resume() {
        runner.resume();
        renderer.printResumed();
    }

    private void stopByUser() {
        stoppedByUser = true;
        runner.stop();
        renderer.printStoppedByUser();
    }

    private MenuLoop createMenuLoop(Map<String, Runnable> commandsByKey) {
        return new MenuLoop(
                input,
                commandsByKey,
                () -> {},
                renderer::printInvalidChoice,
                this::handleInputClosed,
                this::shouldExit,
                this::handleExit
        );
    }

    private void handleInputClosed() {
        runner.stop();
        renderer.printInputClosed();
    }

    private boolean shouldExit() {
        return stoppedByUser || !runner.isRunning();
    }

    private void handleExit() {
        if (!stoppedByUser) {
            renderer.printTerminalStateReached();
        }
    }
}