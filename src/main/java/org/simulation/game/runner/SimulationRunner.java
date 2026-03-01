package org.simulation.game.runner;

import org.simulation.factory.SimulationFactory;
import org.simulation.game.Simulation;

import java.util.Objects;

public class SimulationRunner implements Runner {

    private final SimulationFactory factory;
    private Simulation simulation;
    private Thread simulationThread;

    public SimulationRunner(SimulationFactory factory) {
        this.factory = Objects.requireNonNull(factory, "factory must not be null");
    }

    @Override
    public void start() {
        if (isRunning()) {
            return;
        }

        simulation = factory.create();
        simulationThread = new Thread(simulation::startSimulation);
        simulationThread.start();
    }

    @Override
    public void pause() {
        if (simulation != null) {
            simulation.pauseSimulation();
        }
    }

    @Override
    public void resume() {
        if (simulation != null) {
            simulation.resumeSimulation();
        }
    }

    @Override
    public void stop() {
        if (simulation != null) {
            simulation.stopSimulation();
        }
    }

    @Override
    public boolean isRunning() {
        return simulationThread != null && simulationThread.isAlive();
    }
}
