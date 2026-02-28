package org.simulation.game;

import org.simulation.action.Actions;
import org.simulation.renderer.MapRenderer;
import org.simulation.sleeper.Sleeper;

import java.util.List;

public class Simulation {

    private boolean running = true;
    private int step = 0;

    private final GameMap gameMap;
    private final List<Actions> initialActions;
    private final List<Actions> turnActions;
    private final MapRenderer mapRenderer;
    private final SimulationEndCondition endCondition;
    private final Sleeper sleeper;

    public Simulation(GameMap gameMap,
                      List<Actions> initialActions,
                      List<Actions> turnActions,
                      MapRenderer mapRenderer,
                      SimulationEndCondition endCondition,
                      Sleeper sleeper) {
        this.gameMap = gameMap;
        this.initialActions = initialActions;
        this.turnActions = turnActions;
        this.mapRenderer = mapRenderer;
        this.endCondition = endCondition;
        this.sleeper = sleeper;
    }

    public void startSimulation() {
        executeActions(initialActions);

        while (running) {
            nextTurn();
        }
    }

    public void nextTurn() {
        mapRenderer.render(gameMap, step);

        boolean turnHadChanged = executeActions(turnActions);

        step++;
        sleeper.sleep(1500);

        if (endCondition.isFinished(gameMap, turnHadChanged)) {
            mapRenderer.render(gameMap, step);
            running = false;
        }
    }

    // todo добавить поток
    public void pauseSimulation() {

    }

    private boolean executeActions(List<Actions> actions) {
        for (Actions action : actions) {
            return action.execute(gameMap);
        }
        return false;
    }
}
