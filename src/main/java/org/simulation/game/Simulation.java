package org.simulation.game;

import org.simulation.action.Actions;
import org.simulation.console.renderer.HintRenderer;
import org.simulation.console.renderer.MapRenderer;
import org.simulation.sleeper.Sleeper;

import java.util.List;
import java.util.Objects;

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

        this.gameMap = Objects.requireNonNull(gameMap, "gameMap must not be null");
        this.initialActions = Objects.requireNonNull(initialActions, "initialActions must not be null");
        this.turnActions = Objects.requireNonNull(turnActions, "turnActions must not be null");
        this.mapRenderer = Objects.requireNonNull(mapRenderer, "mapRenderer must not be null");
        this.hintRenderer = Objects.requireNonNull(hintRenderer, "hintRenderer must not be null");
        this.endCondition = Objects.requireNonNull(endCondition, "endCondition must not be null");
        this.sleeper = Objects.requireNonNull(sleeper, "sleeper must not be null");
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
}
