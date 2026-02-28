package org.simulation;

import org.simulation.action.Actions;
import org.simulation.action.MoveEntityAction;
import org.simulation.action.SpawnEntitiesAction;
import org.simulation.entity.Entity;
import org.simulation.factory.EntityFactory;
import org.simulation.factory.Factory;
import org.simulation.game.GameMap;
import org.simulation.game.Simulation;
import org.simulation.path.BfsPathFinder;
import org.simulation.path.neighbor.NeighborFinder;
import org.simulation.path.neighbor.NeighborFinderFourDirs;
import org.simulation.path.PathFinder;
import org.simulation.renderer.ConsoleRenderer;
import org.simulation.renderer.Renderer;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Renderer renderer = new ConsoleRenderer();
        GameMap gameMap = new GameMap();
        Factory<Entity> factory = new EntityFactory();
        Random random = new Random();
        Actions init = new SpawnEntitiesAction(factory, random);

        NeighborFinder neighborFinder = new NeighborFinderFourDirs();
        PathFinder pathFinder = new BfsPathFinder(neighborFinder);

        Actions turn = new MoveEntityAction(pathFinder);
        Simulation simulation = new Simulation(gameMap, init, turn, renderer);

        simulation.startSimulation();
    }
}
