package org.simulation.ui.console.menu.control;

import org.simulation.ui.console.output.Output;

import java.util.Objects;

public class ConsoleControlMenuRenderer implements ControlMenuRenderer {

    private final Output output;

    public ConsoleControlMenuRenderer(Output output) {
        this.output = Objects.requireNonNull(output, "output must not be null");
    }

    @Override
    public void renderPaused() {
        output.println("Симуляция поставлена на паузу.");
    }

    @Override
    public void renderResumed() {
        output.println("Симуляция продолжается.");
    }

    @Override
    public void renderStoppedByUser() {
        output.println("Симуляция завершена пользователем.");
    }
}
