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
import simulation.runner.ConsoleSimulationNotifier;
import simulation.runner.SimulationRunner;
import simulation.ui.ConsoleOutput;
import simulation.ui.StdConsoleOutput;

import java.util.List;

public final class SimulationComposition {

    private SimulationComposition() {}

    public static AppContext createApp(Config cfg) {
        SimulationMap map = new SimulationMap(cfg.getWidth(), cfg.getHeight());

        Factory<Entity> factory = new SimulationFactory();
        NeighborsFinder neighborsFinder = new NeighborsFinder();
        PathFinder pathFinder = new BreadthFirstSearch(neighborsFinder);
        MoveRulesProvider provider = new DefaultMoveRulesProvider();

        List<Action> init = List.of(new InitialSpawnEntitiesAction(factory, cfg.getQuantity()));
        List<Action> turn = List.of(new MoveCreaturesAction(pathFinder, provider, neighborsFinder));

        ConsoleOutput output = new StdConsoleOutput();
        ConsoleRenderer renderer = new ConsoleRenderer(output);
        ContinueCondition condition = new ReachableTargetCondition(pathFinder, provider, neighborsFinder);

        Simulation game = new Simulation(map, init, turn, renderer, condition);
        SimulationRunner runner = new SimulationRunner(game, new ConsoleSimulationNotifier(output));

        return new AppContext(runner, output);
    }
}
