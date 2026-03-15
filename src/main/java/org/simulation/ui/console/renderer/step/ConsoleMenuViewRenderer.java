package org.simulation.ui.console.renderer.step;

import org.simulation.ui.console.menu.MenuDefinition;
import org.simulation.ui.console.output.Output;

import java.util.Objects;

public class ConsoleMenuViewRenderer implements MenuViewRenderer {

    private static final int START_MENU_INDEX = 1;

    private final Output output;

    public ConsoleMenuViewRenderer(Output output) {
        this.output = Objects.requireNonNull(output, "output must not be null");
    }

    @Override
    public void render(MenuDefinition definition) {
        output.println(definition.title());

        for (int i = 0; i < definition.items().size(); i++) {
            output.println((START_MENU_INDEX + i) + ". " + definition.items().get(i));
        }

        output.println(definition.selectMessage());
        output.println();
    }
}