package simulation;

import simulation.runner.ConsoleCommandListener;
import simulation.runner.SimulationRunner;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Config cfg = new Config();
        cfg.load("config.properties");

        AppContext app = SimulationComposition.createApp(cfg);
        SimulationRunner runner = app.runner();

        Thread simThread = new Thread(() -> runner.run(20, cfg.getDelay()), "sim-thread");
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
