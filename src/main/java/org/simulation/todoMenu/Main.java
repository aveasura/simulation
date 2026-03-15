package org.simulation.todoMenu;

import org.simulation.todoMenu.controller.ControlMenuController;
import org.simulation.todoMenu.controller.MainMenuController;
import org.simulation.factory.EntityFactory;
import org.simulation.factory.EntityFactoryImpl;
import org.simulation.factory.SimulationFactory;
import org.simulation.factory.SimulationFactoryImpl;
import org.simulation.game.SimulationMapConfig;
import org.simulation.game.runner.Runner;
import org.simulation.game.runner.SimulationRunner;
import org.simulation.path.neighbor.NeighborFinder;
import org.simulation.path.neighbor.NeighborFinderFourDirs;
import org.simulation.ui.console.input.ConsoleInputProvider;
import org.simulation.ui.console.input.InputProvider;
import org.simulation.ui.console.output.ConsoleOutput;
import org.simulation.ui.console.output.Output;
import org.simulation.ui.console.renderer.ConsoleControlHintRenderer;
import org.simulation.ui.console.renderer.ConsoleControlMenuRenderer;
import org.simulation.ui.console.renderer.ConsoleMainMenuRenderer;
import org.simulation.ui.console.renderer.ConsoleMapRenderer;
import org.simulation.ui.console.renderer.ControlMenuRenderer;
import org.simulation.ui.console.renderer.DefaultEntitySymbolProvider;
import org.simulation.ui.console.renderer.EntitySymbolProvider;
import org.simulation.ui.console.renderer.HintRenderer;
import org.simulation.ui.console.renderer.MainMenuRenderer;
import org.simulation.ui.console.renderer.MapRenderer;

import java.util.Scanner;

public class Main {


    private static final int DEFAULT_MAP_WIDTH = 10;
    private static final int DEFAULT_MAP_HEIGHT = 10;

    public static void main(String[] args) {

        SimulationMapConfig simulationMapConfig = new SimulationMapConfig(DEFAULT_MAP_WIDTH, DEFAULT_MAP_HEIGHT);
        Scanner scanner = new Scanner(System.in);
        Output output = new ConsoleOutput();

        InputProvider inputProvider = new ConsoleInputProvider(scanner);

        EntityFactory entityFactory = new EntityFactoryImpl();

        NeighborFinder neighborFinder = new NeighborFinderFourDirs();

        EntitySymbolProvider symbolProvider = new DefaultEntitySymbolProvider();
        MapRenderer mapRenderer = new ConsoleMapRenderer(output, symbolProvider);
        HintRenderer hintRenderer = new ConsoleControlHintRenderer(output);

        SimulationFactory simulationFactory
                = new SimulationFactoryImpl(simulationMapConfig, mapRenderer, hintRenderer, entityFactory, neighborFinder);

        Runner runner = new SimulationRunner(simulationFactory);

        MainMenuRenderer mainMenuRenderer = new ConsoleMainMenuRenderer(output);
        ControlMenuRenderer controlMenuRenderer = new ConsoleControlMenuRenderer(output);

        MainMenuController mainMenuController = new MainMenuController(mainMenuRenderer, runner);
        ControlMenuController controlMenuController = new ControlMenuController(runner, controlMenuRenderer);

        Menu controlMenu = createControlMenu(inputProvider, output, controlMenuController);

        SimulationSession simulationSession = new SimulationSession(mainMenuController, controlMenu, runner);

        Menu mainMenu = createMainMenu(inputProvider, output, mainMenuController, simulationSession);

        ConsoleApplication application = new ConsoleApplication(mainMenu, mainMenuController);
        application.start();
    }

    private static Menu createMainMenu(InputProvider inputProvider,
                                       Output output,
                                       MainMenuController controller,
                                       SimulationSession simulationSession) {
        Menu menu = new ConsoleMenu(
                inputProvider,
                output,
                "Главное меню",
                "Выберите пункт меню:",
                "Неверный ввод!");

        menu.add("Прочитать правила", controller::showRules);
        menu.add("Запустить симуляцию", simulationSession::start);
        menu.add("Выход", controller::exit);

        return menu;
    }

    private static Menu createControlMenu(InputProvider inputProvider,
                                          Output output,
                                          ControlMenuController controller) {
        Menu menu = new ConsoleMenu(
                inputProvider,
                output,
                "Управление симуляцией",
                "Выберите пункт меню:",
                "Неверный ввод!");

        menu.add("Приостановить", controller::pause);
        menu.add("Продолжить", controller::resume);
        menu.add("Остановить", controller::stop);

        return menu;
    }
}
