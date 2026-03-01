package org.simulation.console.renderer;

import org.simulation.console.Output;

import java.util.Objects;

public class ConsoleControlMenuRenderer implements ControlMenuRenderer {

    private final Output output;

    public ConsoleControlMenuRenderer(Output output) {
        this.output = Objects.requireNonNull(output, "output must not be null");
    }

    @Override
    public void printPaused() {
        output.println("Симуляция поставлена на паузу.");
    }

    @Override
    public void printResumed() {
        output.println("Симуляция продолжается.");
    }

    @Override
    public void printStopped() {
        output.println("Симуляция завершена пользователем.");
    }

    @Override
    public void printInvalidChoice() {
        output.println("Выберите и введите цифру из предложенных");
    }
}
