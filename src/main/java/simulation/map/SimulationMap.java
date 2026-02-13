package simulation.map;

import simulation.entities.Entity;
import simulation.entities.movable.Creature;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimulationMap {

    private final Map<Position, Entity> map = new HashMap<>();
    private final Map<Entity, Position> positionByEntity = new HashMap<>(); // O(1) findPosition

    private final int width;
    private final int height;

    public SimulationMap(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isInside(Position p) {
        return p.x() >= 0 && p.x() < width
                && p.y() >= 0 && p.y() < height;
    }

    public Entity getAt(Position position) {
        return map.get(position);
    }

    public boolean isOccupied(Position position) {
        return map.containsKey(position);
    }

    public void removeAt(Position position) {
        Entity removed = map.remove(position);
        if (removed != null) {
            positionByEntity.remove(removed);
        }
    }

    public List<Creature> getCreatures() {
        List<Creature> creatures = new ArrayList<>();
        for (Entity e : map.values()) {
            if (e instanceof Creature c) {
                creatures.add(c);
            }
        }
        return creatures;
    }

    public boolean place(Position position, Entity entity) {
        if (!isInside(position)) {
            return false;
        }
        if (isOccupied(position)) {
            return false;
        }
        map.put(position, entity);
        positionByEntity.put(entity, position);
        return true;
    }

    public boolean move(Creature creature, Position to) {
        if (!isInside(to)) {
            return false;
        }

        if (isOccupied(to)) {
            return false;
        }

        Position from = findPosition(creature);
        if (from == null) {
            return false;
        }

        map.remove(from);
        map.put(to, creature);
        positionByEntity.put(creature, to);
        return true;
    }

    public Position findPosition(Entity entity) {
        return positionByEntity.get(entity);
    }
}
