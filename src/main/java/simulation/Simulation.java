package simulation;

import simulation.action.Action;
import simulation.map.SimulationMap;
import simulation.renderer.Renderer;

import java.util.List;


public class Simulation {

    private int stepCounter = 0;
    private final SimulationMap map;
    private final List<Action> init;
    private final List<Action> turn;
    private final Renderer renderer;

    public Simulation(SimulationMap map, List<Action> init, List<Action> turn, Renderer renderer) {
        this.map = map;
        this.init = init;
        this.turn = turn;
        this.renderer = renderer;
    }

    public void startSimulation() {
        for (Action a : init) {
            a.execute(map);
        }
        renderer.renderMap(map, stepCounter);

        // тут будет цикл
        while (true) {
            nextTurn();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void nextTurn() {
        for (Action t : turn) {
            t.execute(map);
        }
        stepCounter++;
        renderer.renderMap(map, stepCounter);
    }

    private void pauseSimulation() {

    }
}
