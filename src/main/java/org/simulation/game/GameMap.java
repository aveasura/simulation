package org.simulation.game;

import org.simulation.entity.Entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameMap {

    private final int width;
    private final int height;

    private final Map<Position, Entity> entities = new HashMap<>();

    public GameMap(int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Invalid map size: width = " + width + ", height = " + height);
        }
        this.width = width;
        this.height = height;
    }

    public <T extends Entity> List<T> getEntitiesByType(Class<T> entityClass) {
        if (entityClass == null) {
            throw new IllegalArgumentException("entityClass must not be null");
        }

        List<T> entitiesOfType = new ArrayList<>();

        for (Entity entity : entities.values()) {
            if (entityClass.isInstance(entity)) {
                entitiesOfType.add(entityClass.cast(entity));
            }
        }

        return entitiesOfType;
    }

    public Entity get(Position position) {
        validatePosition(position);

        Entity entity = entities.get(position);
        if (entity == null) {
            throw new IllegalStateException("No entity at position: " + position);
        }

        return entity;
    }

    public Position getPosition(Entity entity) {
        validateEntity(entity);

        for (Map.Entry<Position, Entity> entry : entities.entrySet()) {
            if (entry.getValue() == entity) {
                return entry.getKey();
            }
        }

        throw new IllegalStateException("Entity is not present on the map: " + entity);
    }

    public void put(Position position, Entity entity) {
        validatePosition(position);
        validateEntity(entity);

        if (entities.containsKey(position)) {
            throw new IllegalStateException("Position already occupied: " + position);
        }

        entities.put(position, entity);
    }

    public void remove(Position position) {
        validatePosition(position);

        if (!entities.containsKey(position)) {
            throw new IllegalStateException("No entity at source position: " + position);
        }

        entities.remove(position);
    }

    public boolean isOccupied(Position position) {
        validatePosition(position);
        return entities.containsKey(position);
    }

    public boolean isOutOfBounds(Position position) {
        if (position == null) {
            throw new IllegalArgumentException("position must not be null");
        }

        return position.x() < 0
                || position.y() < 0
                || position.x() >= width
                || position.y() >= height;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    private void validatePosition(Position position) {
        if (position == null) {
            throw new IllegalArgumentException("position must not be null");
        }

        if (isOutOfBounds(position)) {
            throw new IllegalArgumentException("Position is outside the map: " + position);
        }
    }

    private void validateEntity(Entity entity) {
        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null");
        }
    }
}
