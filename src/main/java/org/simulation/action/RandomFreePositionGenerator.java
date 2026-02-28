package org.simulation.action;

import org.simulation.game.GameMap;
import org.simulation.game.Position;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class RandomFreePositionGenerator {

    private final Random random;

    public RandomFreePositionGenerator(Random random) {
        this.random = random;
    }

    public List<Position> generate(GameMap gameMap, int entitiesToSpawn) {
        Set<Position> positions = new HashSet<>();

        if (!hasEnoughFreeCells(gameMap, entitiesToSpawn)) {
            throw new IllegalStateException("Not enough free cells to spawn all entities.");
        }

        while (positions.size() < entitiesToSpawn) {
            int x = random.nextInt(gameMap.getWidth());
            int y = random.nextInt(gameMap.getHeight());

            Position position = new Position(x, y);
            if (gameMap.isOccupied(position)) {
                continue;
            }

            positions.add(position);
        }

        return new ArrayList<>(positions);
    }

    private boolean hasEnoughFreeCells(GameMap gameMap, int entitiesToSpawn) {
        int totalCells = gameMap.getMapArea();
        int occupied = gameMap.getEntitiesCount();
        int free = totalCells - occupied;

        return free >= entitiesToSpawn;
    }
}
