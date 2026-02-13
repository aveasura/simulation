package simulation.action;

import simulation.entities.Entity;
import simulation.entities.EntityType;
import simulation.factory.Factory;
import simulation.map.Position;
import simulation.map.SimulationMap;

import java.util.Random;

public class InitialSpawnEntitiesAction implements Action {
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
        final int maxTries = calculateMaxPlaceTries(map);
        EntityType[] types = EntityType.values();
        for (int i = 0; i < quantity; i++) {
            // берем рандомный тип сущности, создаем
            EntityType type = types[nextIndex(types.length)];
            Entity entity = factory.create(type);

            // если позиция на карте уже занята - выбираем другие координаты + защита от зацикливания.
            boolean isPlaced;
            int tries = 0;
            do {
                int x = nextIndex(map.getWidth());
                int y = nextIndex(map.getHeight());
                Position position = new Position(x, y);

                isPlaced = map.place(position, entity);
                tries++;
            } while (!isPlaced && tries < maxTries);
        }
    }

    // на случай если формула поменяется
    private int calculateMaxPlaceTries(SimulationMap map) {
        return map.getHeight() * map.getWidth();
    }

    private int nextIndex(int bound) {
        return random.nextInt(bound);
    }
}
