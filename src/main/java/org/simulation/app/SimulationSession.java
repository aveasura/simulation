package org.simulation.app;

import org.simulation.game.runner.Runner;
import org.simulation.ui.console.menu.Menu;

public class SimulationSession {

    private final Menu controlMenu;
    private final Runner runner;

    public SimulationSession(Menu controlMenu, Runner runner) {
        this.controlMenu = controlMenu;
        this.runner = runner;
    }

    public void start() {
        runner.start();

        while (runner.isRunning()) {
            controlMenu.show();
            controlMenu.select();
        }
    }
}