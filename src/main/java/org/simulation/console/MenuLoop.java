package org.simulation.console;

import java.util.Map;
import java.util.Objects;
import java.util.function.BooleanSupplier;

public class MenuLoop {

    private final InputProvider input;
    private final Map<String, Runnable> commandsByKey;

    private final Runnable hint;
    private final Runnable onInvalid;
    private final Runnable onInputClosed;

    private final BooleanSupplier shouldExit;
    private final Runnable onExit;

    public MenuLoop(InputProvider input,
                    Map<String, Runnable> commandsByKey,
                    Runnable hint,
                    Runnable onInvalid,
                    Runnable onInputClosed,
                    BooleanSupplier shouldExit,
                    Runnable onExit) {

        this.input = Objects.requireNonNull(input, "input must not be null");
        this.commandsByKey = Objects.requireNonNull(commandsByKey, "commandsByKey must not be null");
        this.hint = Objects.requireNonNull(hint, "hint must not be null");
        this.onInvalid = Objects.requireNonNull(onInvalid, "onInvalid must not be null");
        this.onInputClosed = Objects.requireNonNull(onInputClosed, "onInputClosed must not be null");
        this.shouldExit = Objects.requireNonNull(shouldExit, "shouldExit must not be null");
        this.onExit = Objects.requireNonNull(onExit, "onExit must not be null");
    }

    public void start() {
        try {
            runLoop();
        } catch (IllegalStateException e) {
            onInputClosed.run();
            throw e;
        }
    }

    private void runLoop() {
        while (!exitRequested()) {
            hint.run();
            executeChoice(input.nextLine());
        }
        onExit.run();
    }

    private boolean exitRequested() {
        if (shouldExit.getAsBoolean()) {
            onExit.run();
            return true;
        }
        return false;
    }

    private void executeChoice(String choice) {
        Runnable command = commandsByKey.get(choice);
        if (command == null) {
            onInvalid.run();
            return;
        }
        command.run();
    }
}
