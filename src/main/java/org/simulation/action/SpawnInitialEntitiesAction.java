package org.simulation.action;

import org.simulation.entity.Entity;
import org.simulation.entity.EntityType;
import org.simulation.factory.Factory;
import org.simulation.game.GameMap;
import org.simulation.game.Position;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class SpawnInitialEntitiesAction implements Actions {

    private final Factory<Entity> factory;
    private final Random random;

    public SpawnInitialEntitiesAction(Factory<Entity> factory, Random random) {
        this.factory = factory;
        this.random = random;
    }

    @Override
    public boolean execute(GameMap gameMap) {
        List<Entity> entities = createInitialEntities();
        int entitiesCountToSpawn = entities.size();
        List<Position> randomPositions = createRandomFreePositions(gameMap, entitiesCountToSpawn);
        spawn(gameMap, randomPositions, entities);
        return true;
    }

    private List<Entity> createInitialEntities() {
        List<Entity> entities = new ArrayList<>();
        for (EntityType type : EntityType.values()) {
            Entity entity = factory.create(type);
            entities.add(entity);
        }

        return entities;
    }

    private List<Position> createRandomFreePositions(GameMap gameMap, int entitiesToSpawn) {
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

    private void spawn(GameMap gameMap, List<Position> positions, List<Entity> entities) {
        if (positions.size() != entities.size()) {
            throw new IllegalStateException("Position count must match entities count.");
        }

        for (int i = 0; i < positions.size(); i++) {
            Position position = positions.get(i);
            Entity entity = entities.get(i);
            gameMap.place(position, entity);
        }
    }

    private boolean hasEnoughFreeCells(GameMap gameMap, int entitiesToSpawn) {
        int totalCells = gameMap.getMapArea();
        int occupied = gameMap.getEntitiesCount();
        int free = totalCells - occupied;

        return free >= entitiesToSpawn;
    }
}
