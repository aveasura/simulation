package org.simulation.ui.console.renderer.step;

import org.simulation.ui.console.output.Output;

import java.util.Objects;

public class ConsoleControlStepRenderer implements StepRenderer {

    private final Output output;

    public ConsoleControlStepRenderer(Output output) {
        this.output = Objects.requireNonNull(output, "output must not be null");
    }

    @Override
    public void render(int step) {
        output.println("Ход: " + step);
    }
}
