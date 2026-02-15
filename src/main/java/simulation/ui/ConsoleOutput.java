package simulation.ui;

public interface ConsoleOutput {
    void print(String text);
    void println(String text);
    void printf(String format, Object... args);
}
