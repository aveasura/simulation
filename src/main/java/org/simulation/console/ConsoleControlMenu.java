package org.simulation.console;

import org.simulation.console.renderer.ControlMenuRenderer;
import org.simulation.game.runner.Runner;

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
        this.runner = runner;
        this.provider = provider;
        this.renderer = renderer;
    }

    @Override
    public void start() {
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
    }
}
