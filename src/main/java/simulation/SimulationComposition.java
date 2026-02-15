package simulation;

import simulation.action.Action;
import simulation.action.InitialSpawnEntitiesAction;
import simulation.action.MoveCreaturesAction;
import simulation.entities.Entity;
import simulation.factory.Factory;
import simulation.factory.SimulationFactory;
import simulation.map.SimulationMap;
import simulation.path.BreadthFirstSearch;
import simulation.path.NeighborsFinder;
import simulation.path.PathFinder;
import simulation.path.rules.DefaultMoveRulesProvider;
import simulation.path.rules.MoveRulesProvider;
import simulation.renderer.ConsoleRenderer;
import simulation.runner.SimulationRunner;
import simulation.ui.ConsoleOutput;
import simulation.ui.StdConsoleOutput;

import java.util.List;

public final class SimulationComposition {

    private SimulationComposition() {}

    public static AppContext createApp() {
        SimulationMap map = new SimulationMap(12, 12);
        int entityQuantity = 12;

        Factory<Entity> factory = new SimulationFactory();
        NeighborsFinder neighborsFinder = new NeighborsFinder();
        PathFinder pathFinder = new BreadthFirstSearch(neighborsFinder);
        MoveRulesProvider provider = new DefaultMoveRulesProvider();

        List<Action> init = List.of(new InitialSpawnEntitiesAction(factory, entityQuantity));
        List<Action> turn = List.of(new MoveCreaturesAction(pathFinder, provider, neighborsFinder));

        ConsoleOutput output = new StdConsoleOutput();
        ConsoleRenderer renderer = new ConsoleRenderer(output);
        Simulation game = new Simulation(map, init, turn, renderer);
        SimulationRunner runner = new SimulationRunner(game);

        return new AppContext(runner, output);
    }
}
