package org.simulation.action.spawn;

import org.simulation.game.GameMap;
import org.simulation.game.Position;

import java.util.Objects;
import java.util.Random;

public final class RandomFreePositionGenerator {

    private final Random random;

    public RandomFreePositionGenerator(Random random) {
        this.random = Objects.requireNonNull(random, "random must not be null");
    }

    public Position generate(GameMap gameMap) {
        if (!hasFreeCell(gameMap)) {
            throw new IllegalStateException("No free position available.");
        }

        int width = gameMap.getWidth();
        int height = gameMap.getHeight();

        while (true) {
            int x = getRandomCoordinate(width);
            int y = getRandomCoordinate(height);

            Position position = new Position(x, y);
            if (isPositionAvailable(gameMap, position)) {
                return position;
            }
        }
    }

    private boolean hasFreeCell(GameMap gameMap) {
        int totalCells = gameMap.getMapArea();
        int occupied = gameMap.toMap().size();
        int free = totalCells - occupied;
        return free > 0;
    }

    private int getRandomCoordinate(int bound) {
        return random.nextInt(bound);
    }

    private boolean isPositionAvailable(GameMap gameMap, Position position) {
        return !gameMap.isOccupied(position);
    }
}
