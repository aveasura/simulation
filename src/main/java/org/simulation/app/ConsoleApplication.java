package org.simulation.app;

import org.simulation.ui.console.menu.Menu;
import org.simulation.ui.console.menu.main.MainMenuController;

public class ConsoleApplication implements Application {

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
