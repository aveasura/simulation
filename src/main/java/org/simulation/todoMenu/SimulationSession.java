package org.simulation.todoMenu;

import org.simulation.todoMenu.controller.MainMenuController;
import org.simulation.game.runner.Runner;

public class SimulationSession {

    private final MainMenuController mainMenuController;
    private final Menu controlMenu;
    private final Runner runner;

    public SimulationSession(MainMenuController mainMenuController,
                             Menu controlMenu,
                             Runner runner) {
        this.mainMenuController = mainMenuController;
        this.controlMenu = controlMenu;
        this.runner = runner;
    }

    public void start() {
        mainMenuController.startSimulation();

        while (runner.isRunning()) {
            controlMenu.show();
            controlMenu.select();
        }
    }
}