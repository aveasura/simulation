package simulation.action;

import simulation.entities.Entity;
import simulation.entities.EntityType;
import simulation.factory.Factory;
import simulation.map.Position;
import simulation.map.SimulationMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class InitialSpawnEntitiesAction implements Action {
    private static final List<EntityType> GUARANTEED =
            List.of(EntityType.RABBIT, EntityType.FOX, EntityType.GRASS);

    private final Factory<Entity> factory;
    private final Random random = new Random();
    private final int quantity;

    public InitialSpawnEntitiesAction(Factory<Entity> factory, int quantity) {
        this.factory = factory;
        this.quantity = quantity;
    }

    @Override
    public void execute(SimulationMap map) {
        spawnEntities(map);
    }

    private void spawnEntities(SimulationMap map) {
        if (quantity < GUARANTEED.size()) {
            throw new IllegalArgumentException(
                    "Количество должно быть >= " + GUARANTEED.size());
        }

        List<Position> freePositions = collectFreePositions(map);
        if (freePositions.size() < GUARANTEED.size()) {
            throw new IllegalStateException(
                    "Не хватает клеток для размещения гарантированных сущностей. Свободных клеток: " + freePositions.size()
            );
        }
        if (quantity > freePositions.size()) {
            throw new IllegalArgumentException(
                    "Количество сущностей (" + quantity + ") больше чем количество свободных клеток (" + freePositions.size() + ")"
            );
        }

        Collections.shuffle(freePositions, random);

        // Гарантированные сущности
        for (EntityType type : GUARANTEED) {
            placeOrFail(map, popLast(freePositions), factory.create(type));
        }

        // Остальные сущности (случайный тип из всех EntityType)
        EntityType[] types = EntityType.values();
        int remaining = quantity - GUARANTEED.size();
        for (int i = 0; i < remaining; i++) {
            EntityType type = types[random.nextInt(types.length)];
            placeOrFail(map, popLast(freePositions), factory.create(type));
        }
    }

    private List<Position> collectFreePositions(SimulationMap map) {
        List<Position> positions = new ArrayList<>(map.getWidth() * map.getHeight());
        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                Position p = new Position(x, y);
                if (!map.isOccupied(p)) {
                    positions.add(p);
                }
            }
        }
        return positions;
    }

    private static Position popLast(List<Position> positions) {
        return positions.removeLast();
    }

    private static void placeOrFail(SimulationMap map, Position position, Entity entity) {
        if (!map.place(position, entity)) {
            // по идее сюда не должны попадать
            throw new IllegalStateException("Ошибка размещения сущности на позицию: " + position);
        }
    }
}
