package org.simulation.console.renderer;

import org.simulation.console.Output;

public class ConsoleControlMenuRenderer implements ControlMenuRenderer {

    private final Output output;

    public ConsoleControlMenuRenderer(Output output) {
        this.output = output;
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
        output.println("Симуляция остановлена пользователем.");
    }

    @Override
    public void printInvalidChoice() {
        output.println("Выберите цифру из предложенных");
    }
}
