package simulation.map;

import simulation.entities.Entity;
import simulation.entities.movable.Creature;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimulationMap {

    private final Map<Position, Entity> map = new HashMap<>();

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
        map.remove(position);
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
        return true;
    }

    // двигаем только в пустую клетку (атака/еда делается в Action)
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
        return true;
    }

    public Position findPosition(Creature creature) {
        for (Map.Entry<Position, Entity> entry : map.entrySet()) {
            if (entry.getValue() == creature) {
                return entry.getKey();
            }
        }
        return null; // todo убрать null
    }
}
