package org.simulation.game;

import org.simulation.action.Actions;
import org.simulation.console.renderer.HintRenderer;
import org.simulation.console.renderer.MapRenderer;
import org.simulation.sleeper.Sleeper;

import java.util.List;

public class Simulation {

    private volatile boolean running = true;
    private volatile boolean isPaused = false;

    private int step = 0;

    private final GameMap gameMap;
    private final List<Actions> initialActions;
    private final List<Actions> turnActions;
    private final MapRenderer mapRenderer;
    private final HintRenderer hintRenderer;
    private final SimulationEndCondition endCondition;
    private final Sleeper sleeper;

    public Simulation(GameMap gameMap,
                      List<Actions> initialActions,
                      List<Actions> turnActions,
                      MapRenderer mapRenderer,
                      HintRenderer hintRenderer,
                      SimulationEndCondition endCondition,
                      Sleeper sleeper) {
        this.gameMap = gameMap;
        this.initialActions = initialActions;
        this.turnActions = turnActions;
        this.mapRenderer = mapRenderer;
        this.hintRenderer = hintRenderer;
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
        while (running && isPaused) {
            sleeper.sleep(200);
            if (!running) {
                return;
            }
        }

        mapRenderer.render(gameMap, step);
        hintRenderer.render();

        boolean turnHadChanged = executeActions(turnActions);

        step++;
        sleeper.sleep(1500);

        if (endCondition.isFinished(gameMap, turnHadChanged)) {
            mapRenderer.render(gameMap, step);
            running = false;
        }
    }

    private boolean executeActions(List<Actions> actions) {
        boolean hadChanges = false;

        for (Actions action : actions) {
            boolean changed = action.execute(gameMap);
            if (changed) {
                hadChanges = true;
            }
        }

        return hadChanges;
    }

    public void pauseSimulation() {
        isPaused = true;
    }

    public void resumeSimulation() {
        isPaused = false;
    }

    public void stopSimulation() {
        running = false;
        isPaused = false;
    }
}
