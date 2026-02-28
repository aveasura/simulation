package org.simulation.sleeper;

public class SleeperImpl implements Sleeper {
    @Override
    public void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
