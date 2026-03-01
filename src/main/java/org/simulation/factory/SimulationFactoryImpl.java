package org.simulation.factory;

import org.simulation.action.Actions;
import org.simulation.action.MoveEntityAction;
import org.simulation.action.RandomFreePositionGenerator;
import org.simulation.action.RespawnEntitiesAction;
import org.simulation.action.SpawnInitialEntitiesAction;
import org.simulation.console.renderer.HintRenderer;
import org.simulation.console.renderer.MapRenderer;
import org.simulation.game.GameMap;
import org.simulation.game.Simulation;
import org.simulation.game.SimulationEndCondition;
import org.simulation.path.BfsPathFinder;
import org.simulation.path.PathFinder;
import org.simulation.path.neighbor.NeighborFinder;
import org.simulation.sleeper.Sleeper;
import org.simulation.sleeper.SleeperImpl;

import java.util.List;
import java.util.Random;

public class SimulationFactoryImpl implements SimulationFactory {

    private final MapRenderer mapRenderer;
    private final HintRenderer hintRenderer;
    private final EntityFactory entityFactory;
    private final NeighborFinder neighborFinder;

    public SimulationFactoryImpl(MapRenderer mapRenderer,
                                 HintRenderer hintRenderer,
                                 EntityFactory entityFactory,
                                 NeighborFinder neighborFinder) {
        this.mapRenderer = mapRenderer;
        this.hintRenderer = hintRenderer;
        this.entityFactory = entityFactory;
        this.neighborFinder = neighborFinder;
    }

    @Override
    public Simulation create() {
        GameMap gameMap = new GameMap();

        Random random = new Random();
        RandomFreePositionGenerator positionGenerator = new RandomFreePositionGenerator(random);
        SpawnInitialEntitiesAction initialSpawn = new SpawnInitialEntitiesAction(entityFactory, positionGenerator);
        List<Actions> initialActions = List.of(initialSpawn);

        PathFinder pathFinder = new BfsPathFinder(neighborFinder);
        MoveEntityAction moveEntityAction = new MoveEntityAction(pathFinder);
        RespawnEntitiesAction respawnEntitiesAction = new RespawnEntitiesAction(positionGenerator, entityFactory);
        List<Actions> turnActions = List.of(moveEntityAction, respawnEntitiesAction);

        SimulationEndCondition endCondition = new SimulationEndCondition();
        Sleeper sleeper = new SleeperImpl();

        return new Simulation(
                gameMap,
                initialActions,
                turnActions,
                mapRenderer,
                hintRenderer,
                endCondition,
                sleeper
        );
    }
}
