package simulation;

import simulation.action.Action;
import simulation.map.SimulationMap;
import simulation.renderer.Renderer;

import java.util.List;

public class Simulation {

    private int tick = 0;

    private final SimulationMap map;
    private final List<Action> init;
    private final List<Action> turn;
    private final Renderer renderer;
    private final ContinueCondition continueCondition;


    public Simulation(SimulationMap map, List<Action> init, List<Action> turn, Renderer renderer, ContinueCondition continueCondition) {
        this.map = map;
        this.init = init;
        this.turn = turn;
        this.renderer = renderer;
        this.continueCondition = continueCondition;
    }

    public void initSimulation() {
        init.forEach(a -> a.execute(map));
        renderer.renderMap(map, tick);
    }

    public void step() {
        turn.forEach(a -> a.execute(map));
        tick++;
        renderer.renderMap(map, tick);
    }

    public boolean shouldContinue() {
        return continueCondition.shouldContinue(map);
    }
}
