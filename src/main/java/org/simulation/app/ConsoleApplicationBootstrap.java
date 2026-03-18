package org.simulation.app;

import org.simulation.factory.EntityFactory;
import org.simulation.factory.RegistryEntityFactory;
import org.simulation.factory.SimulationFactory;
import org.simulation.factory.SimulationFactoryImpl;
import org.simulation.game.SimulationMapConfig;
import org.simulation.game.runner.Runner;
import org.simulation.game.runner.SimulationRunner;
import org.simulation.path.neighbor.NeighborFinder;
import org.simulation.path.neighbor.NeighborFinderFourDirs;
import org.simulation.ui.console.input.ConsoleInputProvider;
import org.simulation.ui.console.input.InputProvider;
import org.simulation.ui.console.menu.ConsoleMenu;
import org.simulation.ui.console.menu.Menu;
import org.simulation.ui.console.menu.MenuDefinition;
import org.simulation.ui.console.menu.control.ConsoleControlMenuRenderer;
import org.simulation.ui.console.menu.control.ControlMenuController;
import org.simulation.ui.console.menu.control.ControlMenuRenderer;
import org.simulation.ui.console.menu.main.ConsoleMainMenuRenderer;
import org.simulation.ui.console.menu.main.MainMenuController;
import org.simulation.ui.console.menu.main.MainMenuRenderer;
import org.simulation.ui.console.output.ConsoleOutput;
import org.simulation.ui.console.output.Output;
import org.simulation.ui.console.renderer.map.ConsoleMapRenderer;
import org.simulation.ui.console.renderer.map.EntityEmojiProvider;
import org.simulation.ui.console.renderer.map.EntitySymbolProvider;
import org.simulation.ui.console.renderer.map.MapRenderer;
import org.simulation.ui.console.renderer.step.ConsoleControlStepRenderer;
import org.simulation.ui.console.renderer.step.ConsoleMenuViewRenderer;
import org.simulation.ui.console.renderer.step.MapWithControlMenuRenderer;
import org.simulation.ui.console.renderer.step.MenuViewRenderer;
import org.simulation.ui.console.renderer.step.StepRenderer;

import java.util.List;
import java.util.Scanner;

public final class ConsoleApplicationBootstrap {

    private static final int DEFAULT_MAP_WIDTH = 16;
    private static final int DEFAULT_MAP_HEIGHT = 8;

    public Application create() {
        SimulationMapConfig simulationMapConfig =
                new SimulationMapConfig(DEFAULT_MAP_WIDTH, DEFAULT_MAP_HEIGHT);

        Scanner scanner = new Scanner(System.in);
        Output output = new ConsoleOutput();
        InputProvider inputProvider = new ConsoleInputProvider(scanner);

        MenuDefinition mainMenuDefinition = new MenuDefinition(
                "Главное меню",
                "Выберите пункт меню:",
                "Неверный ввод!",
                List.of(
                        "Прочитать правила",
                        "Запустить симуляцию",
                        "Выход"
                )
        );

        MenuDefinition controlMenuDefinition = new MenuDefinition(
                "Управление симуляцией",
                "Выберите пункт меню:",
                "Неверный ввод!",
                List.of(
                        "Приостановить",
                        "Продолжить",
                        "Остановить"
                )
        );

        MenuViewRenderer menuViewRenderer = new ConsoleMenuViewRenderer(output);

        EntityFactory entityFactory = new RegistryEntityFactory();
        NeighborFinder neighborFinder = new NeighborFinderFourDirs();

        EntitySymbolProvider symbolProvider = new EntityEmojiProvider();

        MapRenderer baseMapRenderer = new ConsoleMapRenderer(output, symbolProvider);
        MapRenderer mapRenderer = new MapWithControlMenuRenderer(
                baseMapRenderer,
                () -> menuViewRenderer.render(controlMenuDefinition)
        );

        StepRenderer stepRenderer = new ConsoleControlStepRenderer(output);

        SimulationFactory simulationFactory = new SimulationFactoryImpl(
                simulationMapConfig,
                mapRenderer,
                stepRenderer,
                entityFactory,
                neighborFinder
        );

        Runner runner = new SimulationRunner(simulationFactory);

        MainMenuRenderer mainMenuRenderer = new ConsoleMainMenuRenderer(output);
        ControlMenuRenderer controlMenuRenderer = new ConsoleControlMenuRenderer(output);

        MainMenuController mainMenuController = new MainMenuController(mainMenuRenderer, runner);
        ControlMenuController controlMenuController = new ControlMenuController(runner, controlMenuRenderer);

        Menu controlMenu = new ConsoleMenu(
                inputProvider,
                output,
                controlMenuDefinition,
                menuViewRenderer,
                List.of(
                        controlMenuController::pause,
                        controlMenuController::resume,
                        controlMenuController::stop
                )
        );

        SimulationSession simulationSession =
                new SimulationSession(controlMenu, runner);

        Menu mainMenu = new ConsoleMenu(
                inputProvider,
                output,
                mainMenuDefinition,
                menuViewRenderer,
                List.of(
                        mainMenuController::showRules,
                        simulationSession::start,
                        mainMenuController::exit
                )
        );

        return new ConsoleApplication(mainMenu, mainMenuController);
    }
}