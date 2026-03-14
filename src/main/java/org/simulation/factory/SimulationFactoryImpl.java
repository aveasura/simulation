package org.simulation.factory;

import org.simulation.action.Action;
import org.simulation.action.MoveEntityAction;
import org.simulation.action.RandomFreePositionGenerator;
import org.simulation.action.RespawnEntitiesAction;
import org.simulation.action.SpawnInitialEntitiesAction;
import org.simulation.console.renderer.HintRenderer;
import org.simulation.console.renderer.MapRenderer;
import org.simulation.game.GameMap;
import org.simulation.game.Simulation;
import org.simulation.game.SimulationMapConfig;
import org.simulation.path.BfsPathFinder;
import org.simulation.path.PathFinder;
import org.simulation.path.neighbor.NeighborFinder;
import org.simulation.sleeper.Sleeper;
import org.simulation.sleeper.ThreadSleeper;

import java.util.List;
import java.util.Objects;
import java.util.Random;

public class SimulationFactoryImpl implements SimulationFactory {

    private final SimulationMapConfig config;
    private final MapRenderer mapRenderer;
    private final HintRenderer hintRenderer;
    private final EntityFactory entityFactory;
    private final NeighborFinder neighborFinder;

    public SimulationFactoryImpl(SimulationMapConfig config,
                                 MapRenderer mapRenderer,
                                 HintRenderer hintRenderer,
                                 EntityFactory entityFactory,
                                 NeighborFinder neighborFinder) {
        this.config = Objects.requireNonNull(config, "config must not be null");
        this.mapRenderer = Objects.requireNonNull(mapRenderer, "mapRenderer must not be null");
        this.hintRenderer = Objects.requireNonNull(hintRenderer, "hintRenderer must not be null");
        this.entityFactory = Objects.requireNonNull(entityFactory, "entityFactory must not be null");
        this.neighborFinder = Objects.requireNonNull(neighborFinder, "neighborFinder must not be null");
    }

    @Override
    public Simulation create() {
        GameMap gameMap = new GameMap(config.mapWidth(), config.mapHeight());

        Random random = new Random();
        RandomFreePositionGenerator positionGenerator = new RandomFreePositionGenerator(random);
        SpawnInitialEntitiesAction initialSpawn = new SpawnInitialEntitiesAction(entityFactory, positionGenerator);
        List<Action> initialActions = List.of(initialSpawn);

        PathFinder pathFinder = new BfsPathFinder(neighborFinder);
        MoveEntityAction moveEntityAction = new MoveEntityAction(pathFinder);
        RespawnEntitiesAction respawnEntitiesAction = new RespawnEntitiesAction(positionGenerator, entityFactory);
        List<Action> turnActions = List.of(moveEntityAction, respawnEntitiesAction);

        Sleeper sleeper = new ThreadSleeper();

        return new Simulation(
                gameMap,
                initialActions,
                turnActions,
                mapRenderer,
                hintRenderer,
                sleeper
        );
    }
}
