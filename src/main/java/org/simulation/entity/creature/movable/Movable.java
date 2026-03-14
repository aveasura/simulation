package org.simulation.entity.creature.movable;

import org.simulation.game.GameMap;
import org.simulation.game.Position;
import org.simulation.path.PathFinder;

import java.util.List;

public interface Movable {

    void makeMove(GameMap gameMap, PathFinder pathFinder);
}
