package simulation.runner;

import simulation.ui.ConsoleOutput;

import java.util.Scanner;

public class ConsoleCommandListener implements Runnable {
    private final SimulationRunner runner;
    private final ConsoleOutput output;

    public ConsoleCommandListener(SimulationRunner runner, ConsoleOutput output) {
        this.runner = runner;
        this.output = output;
    }

    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            if (!sc.hasNextLine()) return;

            String input = sc.nextLine().trim().toLowerCase();

            switch (input) {
                case "p" -> runner.pause();
                case "c" -> runner.resume();
                case "q" -> {
                    runner.stop(FinishReason.STOPPED_BY_USER);
                    return;
                }
                default -> output.println("""
                        Неправильный ввод, используй:
                        p = пауза
                        c = продолжить
                        q = выход
                        """);
            }
        }
    }
}
