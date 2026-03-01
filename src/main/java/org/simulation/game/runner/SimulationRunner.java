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
    public synchronized void start() {
        if (isRunning()) {
            return;
        }

        simulation = factory.create();
        simulationThread = new Thread(simulation::startSimulation);
        simulationThread.setDaemon(true);
        simulationThread.start();

    }

    @Override
    public synchronized void pause() {
        if (simulation != null) {
            simulation.pauseSimulation();
        }
    }

    @Override
    public synchronized void resume() {
        if (simulation != null) {
            simulation.resumeSimulation();
        }
    }

    @Override
    public synchronized void stop() {
        Simulation currentSimulation = simulation;
        Thread currentThread = simulationThread;

        if (currentSimulation != null) {
            currentSimulation.stopSimulation();
        }

        if (currentThread != null) {
            currentThread.interrupt();
            try {
                currentThread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }

        simulation = null;
        simulationThread = null;
    }

    @Override
    public synchronized boolean isRunning() {
        return simulationThread != null && simulationThread.isAlive();
    }
}
