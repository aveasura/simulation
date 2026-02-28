package org.simulation.entity.creature.movable;

import org.simulation.game.GameMap;
import org.simulation.game.Position;

import java.util.List;

public interface Movable {

    boolean makeMove(GameMap gameMap, Position from, List<Position> path);
}
