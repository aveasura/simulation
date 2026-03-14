package org.simulation.console.renderer;

import org.simulation.console.Output;

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

    @Override
    public void renderTerminalStateReached() {
        output.println("Достигнуто конечное состояние, выход в главное меню.");
    }

    @Override
    public void renderInvalidChoice() {
        output.println("Выберите и введите цифру из предложенных");
    }

    @Override
    public void renderInputClosed() {
        output.println("Поток закрыт, симуляция завершается.");
    }
}
