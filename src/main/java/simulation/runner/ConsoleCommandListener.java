package simulation.runner;

import java.util.Scanner;

public class ConsoleCommandListener implements Runnable {
    private final SimulationRunner runner;

    public ConsoleCommandListener(SimulationRunner runner) {
        this.runner = runner;
    }

    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Команды: p = пауза, c = продолжить, q = выход");

        while (true) {
            if (!sc.hasNextLine()) return;

            String input = sc.nextLine().trim().toLowerCase();

            switch (input) {
                case "p" -> runner.pause();
                case "c" -> runner.resume();
                case "q" -> {
                    runner.stop();
                    return;
                }
                default -> System.out.println("""
            Неправильный ввод, используй:
            p = пауза
            c = продолжить
            q = выход
            """);
            }
        }
    }
}
