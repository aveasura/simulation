package org.simulation.todoMenu.controller;

import org.simulation.game.runner.Runner;
import org.simulation.ui.console.renderer.MainMenuRenderer;

public class MainMenuController {

    private boolean exitRequested;

    private final MainMenuRenderer renderer;
    private final Runner runner;

    public MainMenuController(MainMenuRenderer renderer, Runner runner) {
        this.renderer = renderer;
        this.runner = runner;
    }

    public void showRules() {
        renderer.renderRules();
    }

    public void startSimulation() {
        runner.start();
    }

    public void exit() {
        runner.stop();
        exitRequested = true;
    }

    public boolean isExitRequested() {
        return exitRequested;
    }
}
