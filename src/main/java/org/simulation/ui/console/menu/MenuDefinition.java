package org.simulation.ui.console.menu;

import java.util.List;
import java.util.Objects;

public record MenuDefinition(
        String title,
        String selectMessage,
        String errorMessage,
        List<String> items
) {
    public MenuDefinition {
        Objects.requireNonNull(title, "title must not be null");
        Objects.requireNonNull(selectMessage, "selectMessage must not be null");
        Objects.requireNonNull(errorMessage, "errorMessage must not be null");
        Objects.requireNonNull(items, "items must not be null");

        items = List.copyOf(items);

        if (items.isEmpty()) {
            throw new IllegalArgumentException("items must not be empty");
        }
    }
}