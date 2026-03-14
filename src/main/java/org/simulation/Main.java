package org.simulation;

import org.simulation.ui.console.menu.ConsoleControlMenu;
import org.simulation.ui.console.input.ConsoleInputProvider;
import org.simulation.ui.console.menu.ConsoleMainMenu;
import org.simulation.ui.console.output.ConsoleOutput;
import org.simulation.ui.console.renderer.DefaultEntitySymbolProvider;
import org.simulation.ui.console.renderer.EntitySymbolProvider;
import org.simulation.ui.console.input.InputProvider;
import org.simulation.ui.console.menu.Menu;
import org.simulation.ui.console.output.Output;
import org.simulation.ui.console.renderer.ConsoleControlMenuRenderer;
import org.simulation.ui.console.renderer.ConsoleControlHintRenderer;
import org.simulation.ui.console.renderer.ConsoleMainMenuRenderer;
import org.simulation.ui.console.renderer.ConsoleMapRenderer;
import org.simulation.ui.console.renderer.ControlMenuRenderer;
import org.simulation.ui.console.renderer.HintRenderer;
import org.simulation.ui.console.renderer.MainMenuRenderer;
import org.simulation.ui.console.renderer.MapRenderer;
import org.simulation.factory.EntityFactory;
import org.simulation.factory.EntityFactoryImpl;
import org.simulation.factory.SimulationFactory;
import org.simulation.factory.SimulationFactoryImpl;
import org.simulation.game.SimulationMapConfig;
import org.simulation.game.runner.Runner;
import org.simulation.game.runner.SimulationRunner;
import org.simulation.path.neighbor.NeighborFinder;
import org.simulation.path.neighbor.NeighborFinderFourDirs;

import java.util.Scanner;

public class Main {

    private static final int DEFAULT_MAP_WIDTH = 10;
    private static final int DEFAULT_MAP_HEIGHT = 10;

    public static void main(String[] args) {

        Output output = new ConsoleOutput();
        EntitySymbolProvider symbolProvider = new DefaultEntitySymbolProvider();
        MapRenderer mapRenderer = new ConsoleMapRenderer(output, symbolProvider);
        EntityFactory entityFactory = new EntityFactoryImpl();
        NeighborFinder neighborFinder = new NeighborFinderFourDirs();
        HintRenderer hintRenderer = new ConsoleControlHintRenderer(output);
        SimulationMapConfig simulationMapConfig = new SimulationMapConfig(DEFAULT_MAP_WIDTH, DEFAULT_MAP_HEIGHT);
        SimulationFactory simulationFactory
                = new SimulationFactoryImpl(simulationMapConfig, mapRenderer, hintRenderer, entityFactory, neighborFinder);

        Menu menu = getMenu(simulationFactory, output);
        menu.start();
    }

    private static Menu getMenu(SimulationFactory simulationFactory, Output output) {
        Runner runner = new SimulationRunner(simulationFactory);

        MainMenuRenderer mainMenuRenderer = new ConsoleMainMenuRenderer(output);
        ControlMenuRenderer controlMenuRenderer = new ConsoleControlMenuRenderer(output);

        Scanner scanner = new Scanner(System.in);
        InputProvider inputProvider = new ConsoleInputProvider(scanner);

        Menu menu = new ConsoleControlMenu(runner, inputProvider, controlMenuRenderer);
        return new ConsoleMainMenu(runner, menu, mainMenuRenderer, inputProvider);
    }
}
