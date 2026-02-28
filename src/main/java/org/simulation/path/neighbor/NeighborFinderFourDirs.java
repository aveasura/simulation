package org.simulation.path.neighbor;

import org.simulation.game.GameMap;
import org.simulation.game.Position;

import java.util.ArrayList;
import java.util.List;

public class NeighborFinderFourDirs implements NeighborFinder {

    private static final List<Position> DIRECTIONS = List.of(
            new Position(-1, 0),
            new Position(0, -1),
            new Position(1, 0),
            new Position(0, 1)
    );

    @Override
    public List<Position> find(GameMap gameMap, Position current) {
        List<Position> neighbors = new ArrayList<>();

        for (Position direction : DIRECTIONS) {
            Position neighbor = new Position(
                    current.x() + direction.x(),
                    current.y() + direction.y());

            if (!gameMap.isInside(neighbor)) {
                continue;
            }

            neighbors.add(neighbor);
        }

        return neighbors;
    }
}