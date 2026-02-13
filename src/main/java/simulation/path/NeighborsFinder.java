package simulation.path;

import simulation.map.Position;
import simulation.map.SimulationMap;

import java.util.ArrayList;
import java.util.List;

public class NeighborsFinder {

    private static final int[][] DIRS_4 = {
            {-1, 0}, {1, 0}, {0, -1}, {0, 1}
    };

    public List<Position> findNeighbors(Position from, SimulationMap map) {
        List<Position> res = new ArrayList<>();
        int x = from.x();
        int y = from.y();

        for (int[] d : DIRS_4) {
            Position p = new Position(x + d[0], y + d[1]);
            if (map.isInside(p)) {
                res.add(p);
            }
        }
        return res;
    }
}
