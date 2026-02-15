package simulation;

import simulation.runner.ConsoleCommandListener;
import simulation.runner.SimulationRunner;

public class Main {
    public static void main(String[] args) {
        SimulationRunner runner = SimulationComposition.createRunner();

        Thread simThread = new Thread(() -> runner.run(20, 500), "sim-thread");
        Thread cmdThread = new Thread(new ConsoleCommandListener(runner), "cmd-thread");
        cmdThread.setDaemon(true);

        simThread.start();
        cmdThread.start();

        try {
            simThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            runner.stop();
        }
    }
}
