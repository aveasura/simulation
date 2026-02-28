package org.simulation.path;

import org.simulation.game.GameMap;
import org.simulation.game.Position;
import org.simulation.path.neighbor.NeighborFinder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.function.Predicate;

public class BfsPathFinder implements PathFinder {

    private final NeighborFinder neighborFinder;

    public BfsPathFinder(NeighborFinder neighborFinder) {
        this.neighborFinder = neighborFinder;
    }

    @Override
    public List<Position> find(GameMap gameMap,
                               Position start,
                               Predicate<Position> isTarget,
                               Predicate<Position> canStep) {

        Queue<Position> queue = new LinkedList<>();
        Set<Position> visited = new HashSet<>();
        Map<Position, Position> parents = new HashMap<>();

        parents.put(start, null);
        queue.add(start);
        visited.add(start);

        Position target = null;

        while (!queue.isEmpty()) {
            Position current = queue.poll();

            if (isTarget.test(current)) {
                target = current;
                break;
            }

            List<Position> neighbors = neighborFinder.find(gameMap, current);
            for (Position neighbor : neighbors) {

                if (!gameMap.isInside(neighbor)) {
                    throw new IllegalStateException("BFS should not find a position that is outside the map.");
                }

                if (visited.contains(neighbor)) {
                    continue;
                }

                if (!canStep.test(neighbor)) {
                    continue;
                }

                queue.add(neighbor);
                visited.add(neighbor);
                parents.put(neighbor, current);
            }
        }

        if (target == null) {
            return Collections.emptyList();
        }

        List<Position> path = buildPath(target, parents);
        return path;
    }

    private List<Position> buildPath(Position target, Map<Position, Position> parents) {
        List<Position> path = new ArrayList<>();
        Position current = target;

        while (current != null) {
            path.add(current);
            current = parents.get(current);
        }

        Collections.reverse(path);
        return path;
    }
}
