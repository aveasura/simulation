package simulation;

import simulation.action.Action;
import simulation.map.SimulationMap;
import simulation.renderer.Renderer;

import java.util.List;

public class Simulation {

    private int step = 0;

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

    public void initSimulation() {
        init.forEach(a -> a.execute(map));
        renderer.renderMap(map, step);
    }

    public void step() {
        turn.forEach(a -> a.execute(map));
        step++;
        renderer.renderMap(map, step);
    }

    public int getStep() {
        return step;
    }
}
