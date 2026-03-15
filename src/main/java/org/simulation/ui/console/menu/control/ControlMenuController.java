package org.simulation.ui.console.menu.control;

import org.simulation.game.runner.Runner;

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
