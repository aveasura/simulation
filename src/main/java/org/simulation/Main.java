package org.simulation;

import org.simulation.action.Actions;
import org.simulation.action.MoveEntityAction;
import org.simulation.action.RandomFreePositionGenerator;
import org.simulation.action.RespawnEntitiesAction;
import org.simulation.action.SpawnInitialEntitiesAction;
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
import org.simulation.renderer.ConsoleMapRenderer;
import org.simulation.renderer.MapRenderer;
import org.simulation.sleeper.Sleeper;
import org.simulation.sleeper.SleeperImpl;

import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        GameMap gameMap = new GameMap();
        NeighborFinder neighborFinder = new NeighborFinderFourDirs();
        PathFinder pathFinder = new BfsPathFinder(neighborFinder);

        Factory<Entity> factory = new EntityFactory();
        Random random = new Random();

        RandomFreePositionGenerator positionGenerator = new RandomFreePositionGenerator(random);
        SpawnInitialEntitiesAction spawnInitialEntitiesAction = new SpawnInitialEntitiesAction(factory, positionGenerator);
        List<Actions> initialActions = List.of(spawnInitialEntitiesAction);
        List<Actions> turnActions = List.of(new MoveEntityAction(pathFinder), new RespawnEntitiesAction(positionGenerator, factory));


        SimulationEndCondition endCondition = new SimulationEndCondition();
        Sleeper sleeper = new SleeperImpl();
        MapRenderer mapRenderer = new ConsoleMapRenderer();
        Simulation simulation = new Simulation(gameMap, initialActions, turnActions, mapRenderer, endCondition, sleeper);

        simulation.startSimulation();
    }
}
