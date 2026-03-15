package org.simulation.factory;

import org.simulation.action.Action;
import org.simulation.action.spawn.InitSpawnAction;
import org.simulation.action.move.MoveAction;
import org.simulation.action.spawn.RandomFreePositionGenerator;
import org.simulation.action.spawn.RespawnAction;
import org.simulation.action.spawn.SpawnAction;
import org.simulation.ui.console.renderer.step.StepRenderer;
import org.simulation.ui.console.renderer.map.MapRenderer;
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
    private final StepRenderer stepRenderer;
    private final EntityFactory entityFactory;
    private final NeighborFinder neighborFinder;

    public SimulationFactoryImpl(SimulationMapConfig config,
                                 MapRenderer mapRenderer,
                                 StepRenderer stepRenderer,
                                 EntityFactory entityFactory,
                                 NeighborFinder neighborFinder) {
        this.config = Objects.requireNonNull(config, "config must not be null");
        this.mapRenderer = Objects.requireNonNull(mapRenderer, "mapRenderer must not be null");
        this.stepRenderer = Objects.requireNonNull(stepRenderer, "stepRenderer must not be null");
        this.entityFactory = Objects.requireNonNull(entityFactory, "entityFactory must not be null");
        this.neighborFinder = Objects.requireNonNull(neighborFinder, "neighborFinder must not be null");
    }

    @Override
    public Simulation create() {
        GameMap gameMap = new GameMap(config.mapWidth(), config.mapHeight());

        Random random = new Random();
        RandomFreePositionGenerator positionGenerator = new RandomFreePositionGenerator(random);
        SpawnAction initialSpawn = new InitSpawnAction(entityFactory, positionGenerator);
        List<Action> initialActions = List.of(initialSpawn);

        PathFinder pathFinder = new BfsPathFinder(neighborFinder);
        MoveAction moveAction = new MoveAction(pathFinder);
        SpawnAction respawnEntitiesAction = new RespawnAction(entityFactory, positionGenerator);
        List<Action> turnActions = List.of(moveAction, respawnEntitiesAction);

        Sleeper sleeper = new ThreadSleeper();

        return new Simulation(
                gameMap,
                initialActions,
                turnActions,
                mapRenderer,
                stepRenderer,
                sleeper
        );
    }
}
