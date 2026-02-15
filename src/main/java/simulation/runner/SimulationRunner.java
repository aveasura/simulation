package simulation.runner;

import simulation.Simulation;

public class SimulationRunner {
    private boolean running = false;

    private final Simulation simulation;

    public SimulationRunner(Simulation simulation) {
        this.simulation = simulation;
    }

    public void run(int maxSteps, long delayMs) {
        simulation.initSimulation();
        running = true;

        while (running && simulation.getStep() <maxSteps) {
            simulation.step();

            try {
                Thread.sleep(delayMs);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                running = false;
            }
        }
    }

    public void stop() {
        running = false;
    }
}
