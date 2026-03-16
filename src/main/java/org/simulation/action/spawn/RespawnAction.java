package org.simulation.action.spawn;

import org.simulation.entity.Entity;
import org.simulation.entity.creature.movable.herbivore.Rabbit;
import org.simulation.entity.creature.movable.predator.Eagle;
import org.simulation.entity.immovable.Carrot;
import org.simulation.factory.EntityFactory;
import org.simulation.game.GameMap;

import java.util.Collection;
import java.util.Map;

public class RespawnAction extends SpawnAction {

    private static final Map<Class<? extends Entity>, Integer> ENTITY_MINIMUM_NUMBERS = Map.of(
            Rabbit.class, 3,
            Eagle.class, 1,
            Carrot.class, 4
    );

    public RespawnAction(EntityFactory factory, RandomFreePositionGenerator positionGenerator) {
        super(factory, positionGenerator);
    }

    @Override
    public void execute(GameMap gameMap) {
        for (Map.Entry<Class<? extends Entity>, Integer> entry : ENTITY_MINIMUM_NUMBERS.entrySet()) {

            Class<? extends Entity> entityClass = entry.getKey();

            int minimumCount = entry.getValue();
            int currentCount = calculateEntityCount(gameMap, entityClass);

            if (minimumCount > currentCount) {
                int maximumCount = ENTITY_MAXIMUM_NUMBERS.get(entityClass);
                int toSpawnCount = maximumCount - currentCount;

                spawn(gameMap, entityClass, toSpawnCount);
            }
        }
    }

    private static int calculateEntityCount(GameMap gameMap, Class<? extends Entity> entityClass) {
        Collection<Entity> entities = gameMap.toMap().values();
        int count = 0;

        for (Entity entity : entities) {
            if (entityClass.isInstance(entity)) {
                count++;
            }
        }

        return count;
    }
}
