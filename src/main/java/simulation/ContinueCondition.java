package simulation;

import simulation.map.SimulationMap;

@FunctionalInterface
public interface ContinueCondition {
    boolean shouldContinue(SimulationMap map);
}
