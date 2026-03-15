package org.simulation;

import org.simulation.app.Application;
import org.simulation.app.ConsoleApplicationBootstrap;

public class Main {
    public static void main(String[] args) {
        Application application = new ConsoleApplicationBootstrap().create();
        application.start();
    }
}