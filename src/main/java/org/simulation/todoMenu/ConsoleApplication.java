package org.simulation.todoMenu;

import org.simulation.todoMenu.controller.MainMenuController;

public class ConsoleApplication {

    private final Menu mainMenu;
    private final MainMenuController mainMenuController;

    public ConsoleApplication(Menu mainMenu, MainMenuController mainMenuController) {
        this.mainMenu = mainMenu;
        this.mainMenuController = mainMenuController;
    }

    public void start() {
        while (!mainMenuController.isExitRequested()) {
            mainMenu.show();
            mainMenu.select();
        }
    }
}
