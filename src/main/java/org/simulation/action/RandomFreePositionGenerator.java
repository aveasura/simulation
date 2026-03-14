package org.simulation.action;

import org.simulation.game.GameMap;
import org.simulation.game.Position;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

public class RandomFreePositionGenerator {

    private final Random random;

    public RandomFreePositionGenerator(Random random) {
        this.random = Objects.requireNonNull(random, "random must not be null");
    }

    public List<Position> generate(GameMap gameMap, int count) {
        if (count < 0) {
            throw new IllegalArgumentException("count must not be negative");
        }

        if (!hasEnoughFreeCells(gameMap, count)) {
            throw new IllegalStateException("Not enough free cells to spawn all entities.");
        }

        Set<Position> positions = new HashSet<>();
        int width = gameMap.getWidth();
        int height = gameMap.getHeight();

        while (positions.size() < count) {
            int x = getRandomCoordinate(width);
            int y = getRandomCoordinate(height);

            Position position = new Position(x, y);
            if (isPositionAvailable(gameMap, position)) {
                positions.add(position);
            }
        }

        return new ArrayList<>(positions);
    }

    private boolean hasEnoughFreeCells(GameMap gameMap, int count) {
        int totalCells = gameMap.getMapArea();
        int occupied = gameMap.toMap().size();
        int free = totalCells - occupied;
        return free >= count;
    }

    private int getRandomCoordinate(int bound) {
        return random.nextInt(bound);
    }

    private boolean isPositionAvailable(GameMap gameMap, Position position) {
        return !gameMap.isOccupied(position);
    }
}
