package org.simulation;

import org.simulation.action.Actions;
import org.simulation.action.MoveEntityAction;
import org.simulation.action.RandomFreePositionGenerator;
import org.simulation.action.RespawnEntitiesAction;
import org.simulation.action.SpawnInitialEntitiesAction;
import org.simulation.console.ConsoleInputProvider;
import org.simulation.console.ConsoleMenu;
import org.simulation.console.ConsoleOutput;
import org.simulation.console.InputProvider;
import org.simulation.console.Output;
import org.simulation.console.renderer.ConsoleMenuRenderer;
import org.simulation.console.renderer.MenuRenderer;
import org.simulation.entity.Entity;
import org.simulation.factory.EntityFactory;
import org.simulation.factory.Factory;
import org.simulation.game.GameMap;
import org.simulation.game.Simulation;
import org.simulation.game.SimulationEndCondition;
import org.simulation.path.BfsPathFinder;
import org.simulation.path.PathFinder;
import org.simulation.path.neighbor.NeighborFinder;
import org.simulation.path.neighbor.NeighborFinderFourDirs;
import org.simulation.console.renderer.ConsoleMapRenderer;
import org.simulation.console.renderer.MapRenderer;
import org.simulation.sleeper.Sleeper;
import org.simulation.sleeper.SleeperImpl;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GameMap gameMap = new GameMap();
        NeighborFinder neighborFinder = new NeighborFinderFourDirs();
        PathFinder pathFinder = new BfsPathFinder(neighborFinder);

        Factory<Entity> factory = new EntityFactory();
        Output output = new ConsoleOutput();
        Simulation simulation = getSimulation(factory, pathFinder, gameMap, output);

        MenuRenderer menuRenderer = new ConsoleMenuRenderer(output);
        Scanner scanner = new Scanner(System.in);
        try (InputProvider inputProvider = new ConsoleInputProvider(scanner)){
            ConsoleMenu menu = new ConsoleMenu(simulation,menuRenderer, inputProvider);

            menu.start();
        } catch (Exception e) {
            e.fillInStackTrace();
        }

    }

    private static Simulation getSimulation(Factory<Entity> factory, PathFinder pathFinder, GameMap gameMap, Output output) {
        Random random = new Random();

        RandomFreePositionGenerator positionGenerator = new RandomFreePositionGenerator(random);
        SpawnInitialEntitiesAction spawnInitialEntitiesAction = new SpawnInitialEntitiesAction(factory, positionGenerator);
        List<Actions> initialActions = List.of(spawnInitialEntitiesAction);
        List<Actions> turnActions = List.of(new MoveEntityAction(pathFinder), new RespawnEntitiesAction(positionGenerator, factory));


        SimulationEndCondition endCondition = new SimulationEndCondition();
        Sleeper sleeper = new SleeperImpl();
        MapRenderer mapRenderer = new ConsoleMapRenderer(output);
        return new Simulation(gameMap, initialActions, turnActions, mapRenderer, endCondition, sleeper);
    }
}
