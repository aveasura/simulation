package org.simulation;

import org.simulation.console.ConsoleControlMenu;
import org.simulation.console.ConsoleInputProvider;
import org.simulation.console.ConsoleMainMenu;
import org.simulation.console.ConsoleOutput;
import org.simulation.console.InputProvider;
import org.simulation.console.Menu;
import org.simulation.console.Output;
import org.simulation.console.renderer.ConsoleControlMenuRenderer;
import org.simulation.console.renderer.ConsoleControlsHintRenderer;
import org.simulation.console.renderer.ConsoleMainMenuRenderer;
import org.simulation.console.renderer.ConsoleMapRenderer;
import org.simulation.console.renderer.ControlMenuRenderer;
import org.simulation.console.renderer.HintRenderer;
import org.simulation.console.renderer.MainMenuRenderer;
import org.simulation.console.renderer.MapRenderer;
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
        MapRenderer mapRenderer = new ConsoleMapRenderer(output);
        EntityFactory entityFactory = new EntityFactoryImpl();
        NeighborFinder neighborFinder = new NeighborFinderFourDirs();
        HintRenderer hintRenderer = new ConsoleControlsHintRenderer(output);
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
