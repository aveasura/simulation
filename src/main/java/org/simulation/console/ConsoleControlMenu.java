package org.simulation.console;

import org.simulation.console.renderer.ControlMenuRenderer;
import org.simulation.game.runner.Runner;

import java.util.Objects;

public class ConsoleControlMenu implements Menu {

    private static final String PAUSE = "1";
    private static final String RESUME = "2";
    private static final String STOP = "3";

    private final Runner runner;
    private final InputProvider provider;
    private final ControlMenuRenderer renderer;

    public ConsoleControlMenu(Runner runner,
                              InputProvider provider,
                              ControlMenuRenderer renderer) {
        this.runner = Objects.requireNonNull(runner, "runner must not be null");
        this.provider = Objects.requireNonNull(provider, "provider must not be null");
        this.renderer = Objects.requireNonNull(renderer, "renderer must not be null");
    }

    @Override
    public void start() {
        try {
            while (true) {
                String choice = provider.nextLine();
                switch (choice) {
                    case PAUSE -> {
                        runner.pause();
                        renderer.printPaused();
                    }
                    case RESUME -> {
                        runner.resume();
                        renderer.printResumed();
                    }
                    case STOP -> {
                        runner.stop();
                        renderer.printStopped();
                        return;
                    }
                    default -> renderer.printInvalidChoice();
                }
            }
        } catch (IllegalStateException e) {
            runner.stop();
            renderer.printInputClosed();
            throw  e;
        }
    }
}
