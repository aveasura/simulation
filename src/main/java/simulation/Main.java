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

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SimulationMap map = new SimulationMap(10, 10); // пока что затычка
        int entityQuantity = 15;

        Factory<Entity> factory = new SimulationFactory();
        NeighborsFinder neighborsFinder = new NeighborsFinder();
        PathFinder pathFinder = new BreadthFirstSearch(neighborsFinder);
        MoveRulesProvider provider = new DefaultMoveRulesProvider();
        List<Action> init = List.of(new InitialSpawnEntitiesAction(factory, entityQuantity));
        List<Action> turn = List.of(new MoveCreaturesAction(pathFinder, provider, neighborsFinder));
        ConsoleRenderer renderer = new ConsoleRenderer();
        Simulation game = new Simulation(map, init, turn, renderer);
        SimulationRunner app = new SimulationRunner(game);

        Thread simThread = new Thread(() -> app.run(20, 500), "sim-thread");
        Thread cmdThread = new Thread(() -> {
            Scanner sc = new Scanner(System.in);
            while (true) {
                System.out.println("p | c | q");
                String input = sc.nextLine();
                switch (input) {
                    case "p" -> app.pause();
                    case "c" -> app.resume();
                    case "q" -> app.stop();
                    default -> System.out.println("invalid input");
                }
            }
        });

        cmdThread.setDaemon(true);

        simThread.start();
        cmdThread.start();

        try {
            simThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
