package org.simulation.console;

import java.util.Objects;
import java.util.Scanner;

public class ConsoleInputProvider implements InputProvider {

    private final Scanner scanner;

    public ConsoleInputProvider(Scanner scanner) {
        this.scanner = Objects.requireNonNull(scanner, "scanner must not be null");
    }

    @Override
    public String nextLine() {
        if (!scanner.hasNextLine()) {
            throw new IllegalStateException("Input not available: stdin thread closed");
        }

        return scanner.nextLine().trim();
    }

    @Override
    public void close() {
        scanner.close();
    }
}
