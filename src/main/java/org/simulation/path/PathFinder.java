package org.simulation.path;

import org.simulation.game.GameMap;
import org.simulation.game.Position;

import java.util.List;
import java.util.function.Predicate;

public interface PathFinder {

    List<Position> find(GameMap gameMap, Position currentPosition, Predicate<Position> isTarget, Predicate<Position> canStep);
}
