package org.simulation.path.neighbor;

import org.simulation.game.GameMap;
import org.simulation.game.Position;

import java.util.List;

public interface NeighborFinder {

    List<Position> find(GameMap gameMap, Position current);

}
