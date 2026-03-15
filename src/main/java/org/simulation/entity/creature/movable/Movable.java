package org.simulation.entity.creature.movable;

import org.simulation.game.GameMap;
import org.simulation.path.PathFinder;

public interface Movable {

    void makeMove(GameMap gameMap, PathFinder pathFinder);
}
