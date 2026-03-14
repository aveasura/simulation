package org.simulation.game;

import org.simulation.action.Action;
import org.simulation.console.renderer.HintRenderer;
import org.simulation.console.renderer.MapRenderer;
import org.simulation.sleeper.Sleeper;

import java.util.List;
import java.util.Objects;

public class Simulation {

    private static final int PAUSE_POLL_DELAY_MS = 100;
    private static final int TURN_DELAY_MS = 1500;

    private volatile boolean running = true;
    private volatile boolean isPaused = false;

    private int step = 0;

    private final GameMap gameMap;
    private final List<Action> initialActions;
    private final List<Action> turnActions;
    private final MapRenderer mapRenderer;
    private final HintRenderer hintRenderer;
    private final SimulationEndCondition endCondition;
    private final Sleeper sleeper;

    public Simulation(GameMap gameMap,
                      List<Action> initialActions,
                      List<Action> turnActions,
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
        waitWhilePaused();
        if (!running) {
            return;
        }

        renderCurrentState();

        boolean turnChanged = executeActions(turnActions);
        step++;

        boolean finished = finishIfNeeded(turnChanged);
        if (finished) {
            return;
        }

        sleeper.sleep(TURN_DELAY_MS);
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

    private void waitWhilePaused() {
        while (running && isPaused) {
            sleeper.sleep(PAUSE_POLL_DELAY_MS);
        }
    }

    private boolean finishIfNeeded(boolean turnChanged) {
        if (endCondition.isFinished(gameMap, turnChanged)) {
            mapRenderer.render(gameMap, step);
            running = false;
            return true;
        }
        return false;
    }

    private void renderCurrentState() {
        mapRenderer.render(gameMap, step);
        hintRenderer.render();
    }

    private boolean executeActions(List<Action> actions) {
        boolean hadChanges = false;

        for (Action action : actions) {
            boolean changed = true; // TODO: стоит временная заглушка
            action.execute(gameMap);
            if (changed) {
                hadChanges = true;
            }
        }

        return hadChanges;
    }
}
