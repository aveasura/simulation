package org.simulation.todoMenu;

import org.simulation.ui.console.input.InputProvider;
import org.simulation.ui.console.output.Output;

import java.util.ArrayList;
import java.util.List;

public class ConsoleMenu implements Menu {

    private static final int START_MENU_INDEX = 1;

    private final List<Item> items = new ArrayList<>();

    private int id = START_MENU_INDEX;

    private final InputProvider input;
    private final Output output;

    private final String title;
    private final String selectMessage;
    private final String errorMessage;

    public ConsoleMenu(InputProvider input, Output output, String title, String selectMessage, String errorMessage) {
        this.input = input;
        this.output = output;
        this.title = title;
        this.selectMessage = selectMessage;
        this.errorMessage = errorMessage;
    }

    @Override
    public void add(String text, Runnable action) {
        Item item = new Item(id++, text, action);
        items.add(item);
    }

    @Override
    public void show() {
        output.println(title);
        for (Item item : items) {
            output.println(item.id() + ". " + item.text());
        }
    }

    @Override
    public void select() {
        while (true) {
            output.println(selectMessage);
            String key = input.nextLine();
            if (isInteger(key)) {
                int num = Integer.parseInt(key);
                if (num >= START_MENU_INDEX && num < id) {
                    Item item = items.get(num - START_MENU_INDEX);
                    item.action.run();
                    return;
                }
            }
            output.println(errorMessage);
        }
    }

    private static boolean isInteger(String key) {
        try {
            Integer.parseInt(key);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private record Item(int id, String text, Runnable action) {
    }
}
