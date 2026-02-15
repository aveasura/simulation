package simulation.ui;

public class StdConsoleOutput implements ConsoleOutput {
    @Override
    public synchronized void print(String text) {
        System.out.print(text);
    }

    @Override
    public synchronized void println(String text) {
        System.out.println(text);
    }

    @Override
    public synchronized void printf(String format, Object... args) {
        System.out.printf(format, args);
    }
}
