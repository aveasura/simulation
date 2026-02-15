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
            if (!sc.hasNextLine()) {
                return;
            }

            String input = sc.nextLine().trim().toLowerCase();

            switch (input) {
                case "1" -> runner.pause();
                case "2" -> runner.resume();
                case "3" -> {
                    runner.stop(FinishReason.STOPPED_BY_USER);
                    return;
                }
                default -> output.println("""
                        Неправильный ввод, используй:
                        1 = пауза
                        2 = продолжить
                        3 = выход
                        """);
            }
        }
    }
}
