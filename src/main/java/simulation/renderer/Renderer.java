package simulation.renderer;

import simulation.map.SimulationMap;

public interface Renderer {

    void renderMap(SimulationMap map, int steps);
}
