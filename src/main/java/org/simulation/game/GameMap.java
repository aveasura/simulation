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
            throw new IllegalArgumentException("Invalid map size: width=" + width + ", height=" + height);
        }
        this.width = width;
        this.height = height;
    }

    public void place(Position position, Entity entity) {
        validateInside(position);
        if (entities.containsKey(position)) {
            throw new IllegalArgumentException("Position already busy: " + position);
        }

        entities.put(position, entity);
    }

    public void move(Position from, Position to) {
        validateInside(from);
        validateInside(to);
        if (!entities.containsKey(from)) {
            throw new IllegalStateException("No entity at source position: " + from);
        }

        if (entities.containsKey(to)) {
            throw new IllegalArgumentException("Position already busy: " + to);
        }

        Entity entity = entities.remove(from);
        entities.put(to, entity);
    }

    public Map<Position, Entity> getEntities() {
        return new HashMap<>(entities);
    }

    public Entity getAt(Position position) {
        validateInside(position);
        return entities.get(position);
    }

    public void removeEntity(Position target) {
        validateInside(target);
        entities.remove(target);
    }

    public boolean isInside(Position position) {
        return position.x() >= 0
                && position.y() >= 0
                && position.x() < width
                && position.y() < height;
    }

    public boolean isOccupied(Position position) {
        validateInside(position);
        return entities.containsKey(position);
    }

    public int getEntitiesCount() {
        return entities.size();
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

    private void validateInside(Position position) {
        if (!isInside(position)) {
            throw new IllegalArgumentException("Position is outside the map: " + position);
        }
    }
}
