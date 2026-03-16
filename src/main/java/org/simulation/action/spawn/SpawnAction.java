package org.simulation.action.spawn;

import org.simulation.action.Action;
import org.simulation.entity.Entity;
import org.simulation.entity.creature.movable.herbivore.Rabbit;
import org.simulation.entity.creature.movable.predator.Fox;
import org.simulation.entity.immovable.Grass;
import org.simulation.entity.immovable.Mountain;
import org.simulation.entity.immovable.Tree;
import org.simulation.factory.EntityFactory;
import org.simulation.game.GameMap;
import org.simulation.game.Position;

import java.util.Map;

public abstract class SpawnAction implements Action {

    protected final static Map<Class<? extends Entity>, Integer> ENTITY_MAXIMUM_NUMBERS = Map.of(
            Rabbit.class, 7,
            Fox.class, 4,
            Grass.class, 10,
            Tree.class, 4,
            Mountain.class, 4
    );

    private final EntityFactory factory;
    private final RandomFreePositionGenerator positionGenerator;

    protected SpawnAction(EntityFactory factory, RandomFreePositionGenerator positionGenerator) {
        this.factory = factory;
        this.positionGenerator = positionGenerator;
    }

    protected void spawn(GameMap gameMap, Class<? extends Entity> entityClass, int count) {
        for (int i = 0; i < count; i++) {
            Position position = createRandomFreePosition(gameMap);
            Entity entity = createEntity(entityClass);

            gameMap.put(position, entity);
        }
    }

    private Position createRandomFreePosition(GameMap gameMap) {
        return positionGenerator.generate(gameMap);
    }

    private Entity createEntity(Class<? extends Entity> entity) {
        return factory.create(entity);
    }
}
