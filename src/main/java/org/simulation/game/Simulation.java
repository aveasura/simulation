package org.simulation.game;

import org.simulation.action.Actions;
import org.simulation.renderer.Renderer;

public class Simulation {

    private int step = 0;

    private final GameMap gameMap;
    private final Actions initial;
    private final Actions turn;
    private final Renderer renderer;

    public Simulation(GameMap gameMap, Actions initial, Actions turn, Renderer renderer) {
        this.gameMap = gameMap;
        this.initial = initial;
        this.turn = turn;
        this.renderer = renderer;
    }

    // Запустить бесконечный цикл симуляции и рендеринга
    // todo while !game.isFinished()
    public void startSimulation() {
        initial.execute(gameMap);

        renderer.render(gameMap);

        while (true) {
            nextTurn();
            sleep(600);
        }
    }

    // Просимулировать и отрендерить один ход
    public void nextTurn() {
        turn.execute(gameMap);
        renderer.render(gameMap);
    }

    // todo добавить поток
    public void pauseSimulation() {

    }

    private void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
