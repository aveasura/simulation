package org.simulation.todoMenu.controller;

import org.simulation.game.runner.Runner;
import org.simulation.ui.console.renderer.ControlMenuRenderer;

public class ControlMenuController {

    private final Runner runner;
    private final ControlMenuRenderer renderer;

    public ControlMenuController(Runner runner, ControlMenuRenderer renderer) {
        this.runner = runner;
        this.renderer = renderer;
    }

    public void pause() {
        runner.pause();
        renderer.renderPaused();
    }

    public void resume() {
        runner.resume();
        renderer.renderResumed();
    }

    public void stop() {
        runner.stop();
        renderer.renderStoppedByUser();
    }
}
