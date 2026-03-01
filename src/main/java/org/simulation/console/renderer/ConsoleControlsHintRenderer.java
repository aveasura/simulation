package org.simulation.console.renderer;

import org.simulation.console.Output;

import java.util.Objects;

public class ConsoleControlsHintRenderer implements HintRenderer {

    private static final String CONTROLS_HINTS = """
            1. Приостановить симуляцию
            2. Продолжить симуляцию
            3. Остановить симуляцию
            """;

    private final Output output;

    public ConsoleControlsHintRenderer(Output output) {
        this.output = Objects.requireNonNull(output, " must not be null");
    }

    @Override
    public void render() {
        output.println(CONTROLS_HINTS);
    }
}
