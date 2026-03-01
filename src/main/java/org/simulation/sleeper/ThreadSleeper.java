package org.simulation.sleeper;

public class ThreadSleeper implements Sleeper {
    @Override
    public void sleep(int millis) {
        if (millis < 0) {
            throw new IllegalArgumentException("millis must be >= 0");
        }

        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
