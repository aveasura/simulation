package simulation.runner;

import simulation.Simulation;

public class SimulationRunner {

    private volatile boolean running = false;
    private volatile State state = State.STOPPED;
    private final Object lock = new Object();

    private final Simulation simulation;

    public SimulationRunner(Simulation simulation) {
        this.simulation = simulation;
    }

    public void run(int maxSteps, long delay) {
        simulation.initSimulation();
        running = true;
        state = State.RUNNING;

        while (running && simulation.getStep() < maxSteps) {
            waitIfPaused();
            if (!running || state == State.STOPPED) {
                break;
            }

            simulation.step();

            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                stop();
            }
        }

        running = false;
        state = State.STOPPED;
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

    public void stop() {
        synchronized (lock) {
            running = false;
            state = State.STOPPED;
            lock.notifyAll();
        }
    }

    public boolean isRunning() {
        return running;
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
                    return;
                }
            }
        }
    }
}
