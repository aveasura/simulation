package org.simulation.ui.console.menu;

import org.simulation.ui.console.input.InputProvider;
import org.simulation.ui.console.output.Output;
import org.simulation.ui.console.renderer.step.MenuViewRenderer;

import java.util.List;
import java.util.Objects;

public class ConsoleMenu implements Menu {

    private static final int START_MENU_INDEX = 1;

    private final InputProvider input;
    private final Output output;
    private final MenuDefinition definition;
    private final MenuViewRenderer viewRenderer;
    private final List<Runnable> actions;

    public ConsoleMenu(InputProvider input,
                       Output output,
                       MenuDefinition definition,
                       MenuViewRenderer viewRenderer,
                       List<Runnable> actions) {
        this.input = Objects.requireNonNull(input, "input must not be null");
        this.output = Objects.requireNonNull(output, "output must not be null");
        this.definition = Objects.requireNonNull(definition, "definition must not be null");
        this.viewRenderer = Objects.requireNonNull(viewRenderer, "viewRenderer must not be null");
        this.actions = List.copyOf(Objects.requireNonNull(actions, "actions must not be null"));

        if (definition.items().size() != this.actions.size()) {
            throw new IllegalArgumentException("items count must match actions count");
        }
    }

    @Override
    public void show() {
        viewRenderer.render(definition);
    }

    @Override
    public void select() {
        while (true) {
            String key = input.nextLine();

            if (isValidChoice(key)) {
                int index = Integer.parseInt(key) - START_MENU_INDEX;
                actions.get(index).run();
                return;
            }

            output.println(definition.errorMessage());
            show();
        }
    }

    private boolean isValidChoice(String key) {
        try {
            int choice = Integer.parseInt(key);
            return isChoiceInMenuRange(choice);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isChoiceInMenuRange(int choice) {
        return choice >= START_MENU_INDEX && choice < START_MENU_INDEX + actions.size();
    }
}