package org.simulation.game;

import org.simulation.action.Action;
import org.simulation.sleeper.Sleeper;
import org.simulation.ui.console.renderer.map.MapRenderer;
import org.simulation.ui.console.renderer.step.StepRenderer;

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
    private final StepRenderer stepRenderer;
    private final Sleeper sleeper;

    public Simulation(GameMap gameMap,
                      List<Action> initialActions,
                      List<Action> turnActions,
                      MapRenderer mapRenderer,
                      StepRenderer stepRenderer,
                      Sleeper sleeper) {

        this.gameMap = Objects.requireNonNull(gameMap, "gameMap must not be null");
        this.initialActions = Objects.requireNonNull(initialActions, "initialActions must not be null");
        this.turnActions = Objects.requireNonNull(turnActions, "turnActions must not be null");
        this.mapRenderer = Objects.requireNonNull(mapRenderer, "mapRenderer must not be null");
        this.stepRenderer = Objects.requireNonNull(stepRenderer, "stepRenderer must not be null");
        this.sleeper = Objects.requireNonNull(sleeper, "sleeper must not be null");
    }

    public void startSimulation() {
        executeActions(initialActions); // todo в конструкторе

        while (running) { // todo isRunning = true
            nextTurn();
            sleeper.sleep(TURN_DELAY_MS); // todo
        }
    }

    public void nextTurn() {
        waitWhilePaused(); // todo сделать по тз
        if (!running) {
            return;
        }

        renderCurrentState();

        executeActions(turnActions);
        step++;

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

    private void renderCurrentState() {
        stepRenderer.render(step);
        mapRenderer.render(gameMap);
    }

    private void executeActions(List<Action> actions) {
        for (Action action : actions) {
            action.execute(gameMap);
        }
    }
}
