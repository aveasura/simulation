package simulation;

import simulation.runner.SimulationRunner;
import simulation.ui.ConsoleOutput;

public record AppContext(SimulationRunner runner, ConsoleOutput output) {}