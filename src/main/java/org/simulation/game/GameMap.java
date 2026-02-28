package org.simulation.game;

import org.simulation.entity.Entity;

import java.util.HashMap;
import java.util.Map;

public class GameMap {

    private static final int WIDTH = 5;
    private static final int HEIGHT = 5;

    private final Map<Position, Entity> entities = new HashMap<>();

    public void place(Position position, Entity entity) {
        if (entities.containsKey(position)) {
            throw new IllegalArgumentException("Position already busy");
        }
        entities.put(position, entity);
    }

    public void move(Position from, Position to) {
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
        return entities.get(position);
    }

    public void removeEntity(Position target) {
        entities.remove(target);
    }

    public boolean isInside(Position position) {
        return position.x() >= 0
                && position.y() >= 0
                && position.x() < WIDTH
                && position.y() < HEIGHT;
    }

    public boolean isOccupied(Position position) {
        return entities.containsKey(position);
    }

    // todo
    public boolean isFinished() {
        return false;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public int getWidth() {
        return WIDTH;
    }
}
