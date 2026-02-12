package simulation.path;

import simulation.entities.movable.Creature;
import simulation.map.Position;
import simulation.map.SimulationMap;
import simulation.path.rules.MoveRules;

import java.util.*;

public class BreadthFirstSearch implements PathFinder {

    private final NeighborsFinder nf;

    public BreadthFirstSearch(NeighborsFinder nf) {
        this.nf = nf;
    }

    @Override
    public List<Position> findPath(Position from, Position to, Creature mover, SimulationMap map, MoveRules rules) {
        if (from.equals(to)) return List.of(from);

        Queue<Position> q = new ArrayDeque<>();
        Set<Position> visited = new HashSet<>();
        Map<Position, Position> parent = new HashMap<>();

        q.add(from);
        visited.add(from);

        boolean found = false;

        while (!q.isEmpty()) {
            Position cur = q.poll();

            for (Position next : nf.findNeighbors(cur, map)) {
                if (visited.contains(next)) continue;
                if (!rules.canEnter(mover, next, map)) continue;

                visited.add(next);
                parent.put(next, cur);

                if (next.equals(to)) {
                    found = true;
                    q.clear();
                    break;
                }
                q.add(next);
            }
        }

        if (!found) return List.of();
        return buildPath(from, to, parent);
    }

    @Override
    public Position findNextStep(Position start, Creature mover, SimulationMap map, MoveRules rules) {
        List<Position> path = findPathToNearestGoal(start, mover, map, rules);
        if (path.size() < 2) return start;
        return path.get(1);
    }

    // путь до ближайшей цели по rules.isGoal
    public List<Position> findPathToNearestGoal(Position start, Creature mover, SimulationMap map, MoveRules rules) {
        Queue<Position> q = new ArrayDeque<>();
        Set<Position> visited = new HashSet<>();
        Map<Position, Position> parent = new HashMap<>();

        q.add(start);
        visited.add(start);

        Position foundGoal = null;

        while (!q.isEmpty()) {
            Position curr = q.poll();

            if (!curr.equals(start) && rules.isGoal(mover, curr, map)) {
                foundGoal = curr;
                break;
            }

            for (Position next : nf.findNeighbors(curr, map)) {
                if (visited.contains(next)) continue;
                if (!rules.canEnter(mover, next, map)) continue;

                visited.add(next);
                parent.put(next, curr);
                q.add(next);
            }
        }

        if (foundGoal == null) return List.of();
        return buildPath(start, foundGoal, parent);
    }

    private List<Position> buildPath(Position start, Position goal, Map<Position, Position> parent) {
        List<Position> path = new ArrayList<>();
        Position cur = goal;
        while (cur != null && !cur.equals(start)) {
            path.add(cur);
            cur = parent.get(cur);
        }
        path.add(start);
        Collections.reverse(path);
        return path;
    }
}
