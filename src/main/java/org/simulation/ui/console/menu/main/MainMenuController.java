package org.simulation.ui.console.menu.main;

import org.simulation.game.runner.Runner;

public class MainMenuController {

    private boolean exitRequested;

    private final MainMenuRenderer renderer;
    private final Runner runner;

    public MainMenuController(Runner runner, MainMenuRenderer renderer) {
        this.runner = runner;
        this.renderer = renderer;
    }

    public void showRules() {
        renderer.renderRules();
    }

    public void exit() {
        runner.stop();
        exitRequested = true;
    }

    public boolean isExitRequested() {
        return exitRequested;
    }
}
