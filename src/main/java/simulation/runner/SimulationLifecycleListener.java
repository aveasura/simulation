package simulation.runner;

public interface SimulationLifecycleListener {
    default void onSimulationStarted() {
    }

    default void onSimulationFinished(FinishReason reason, int ticks) {
    }
}