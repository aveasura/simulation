package simulation.runner;

import simulation.ui.ConsoleOutput;

public class ConsoleSimulationNotifier implements SimulationLifecycleListener {

    private final ConsoleOutput output;

    public ConsoleSimulationNotifier(ConsoleOutput output) {
        this.output = output;
    }

    @Override
    public void onSimulationFinished(FinishReason reason, int ticks) {
        String message = switch (reason) {
            case COMPLETED -> "Симуляция завершена: достигнуто конечное состояние";
            case STOPPED_BY_USER -> "Симуляция остановлена пользователем";
            case INTERRUPTED -> "Симуляция прервана";
        };

        output.println(message);
    }
}