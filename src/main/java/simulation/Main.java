package simulation;

import simulation.runner.ConsoleCommandListener;
import simulation.runner.SimulationRunner;

public class Main {
    public static void main(String[] args) {
        AppContext app = SimulationComposition.createApp();
        SimulationRunner runner = app.runner();

        Thread simThread = new Thread(() -> runner.run(20, 500), "sim-thread");
        Thread cmdThread = new Thread(new ConsoleCommandListener(runner, app.output()), "cmd-thread");
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
