package simulation.runner;

import simulation.Simulation;

public class SimulationRunner {

    private volatile boolean running = false;
    private volatile State state = State.STOPPED;
    private volatile FinishReason finishReason = FinishReason.COMPLETED;
    private final Object lock = new Object();

    private final Simulation simulation;
    private final SimulationLifecycleListener lifecycleListener;

    public SimulationRunner(Simulation simulation, SimulationLifecycleListener lifecycleListener) {
        this.simulation = simulation;
        this.lifecycleListener = lifecycleListener;
    }

    public void run(long delay) {
        simulation.initSimulation();
        running = true;
        state = State.RUNNING;
        finishReason = FinishReason.COMPLETED;

        lifecycleListener.onSimulationStarted();

        while (running && simulation.shouldContinue()) {
            waitIfPaused();
            if (!running || state == State.STOPPED) {
                break;
            }

            simulation.step();

            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                stop(FinishReason.INTERRUPTED);
            }
        }

        running = false;
        state = State.STOPPED;

        lifecycleListener.onSimulationFinished(finishReason, simulation.getTick());
    }

    public void pause() {
        if (running) {
            state = State.PAUSED;
        }
    }

    public void resume() {
        synchronized (lock) {
            if (state == State.PAUSED) {
                state = State.RUNNING;
                lock.notifyAll();
            }
        }
    }

    public void stop(FinishReason reason) {
        synchronized (lock) {
            running = false;
            state = State.STOPPED;
            finishReason = reason;
            lock.notifyAll();
        }
    }

    private void waitIfPaused() {
        synchronized (lock) {
            while (state == State.PAUSED) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    running = false;
                    state = State.STOPPED;
                    finishReason = FinishReason.INTERRUPTED;
                    return;
                }
            }
        }
    }
}
