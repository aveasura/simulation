package org.simulation.game;

import org.simulation.entity.Entity;

import java.util.HashMap;
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

    public Map<Position, Entity> toMap() {
        return new HashMap<>(entities);
    }

    public Entity get(Position position) {
        validateInside(position);
        return entities.get(position);
    }

    public Position getPosition(Entity entity) {
        for (Map.Entry<Position, Entity> entry : entities.entrySet()) {
            if (entry.getValue() == entity) {
                return entry.getKey();
            }
        }

        throw new IllegalStateException("Entity is not present on the map: " + entity);
    }

    public void put(Position position, Entity entity) {
        validateInside(position);

        if (entities.containsKey(position)) {
            throw new IllegalArgumentException("Position already busy: " + position);
        }

        entities.put(position, entity);
    }

    public void remove(Position position) {
        validateInside(position);

        if (!entities.containsKey(position)) {
            throw new IllegalStateException("No entity at source position: " + position);
        }

        entities.remove(position);
    }

    public boolean containsEntity(Entity entity) {
        return entities.containsValue(entity);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getMapArea() {
        return width * height;
    }

    public boolean isOccupied(Position position) {
        validateInside(position);
        return entities.containsKey(position);
    }

    public boolean isInside(Position position) {
        return position.x() >= 0
                && position.y() >= 0
                && position.x() < width
                && position.y() < height;
    }

    private void validateInside(Position position) {
        if (!isInside(position)) {
            throw new IllegalArgumentException("Position is outside the map: " + position);
        }
    }
}
