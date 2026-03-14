package org.simulation.action;

import org.simulation.entity.Entity;
import org.simulation.entity.creature.movable.herbivore.Rabbit;
import org.simulation.entity.creature.movable.predator.Fox;
import org.simulation.entity.immovable.Grass;
import org.simulation.entity.immovable.Mountain;
import org.simulation.entity.immovable.Tree;
import org.simulation.factory.EntityFactory;
import org.simulation.game.GameMap;
import org.simulation.game.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// TODO: refactor
public class SpawnInitialEntitiesAction implements Action {

    private static final int EXTRA_TREES_COUNT = 10;
    private static final int EXTRA_MOUNTAINS_COUNT = 8;

    private final EntityFactory entityFactory;
    private final RandomFreePositionGenerator positionGenerator;

    public SpawnInitialEntitiesAction(EntityFactory entityFactory, RandomFreePositionGenerator positionGenerator) {
        this.entityFactory = Objects.requireNonNull(entityFactory, "entityFactory must not be null");
        this.positionGenerator = Objects.requireNonNull(positionGenerator, "positionGenerator must not be null");
    }

    @Override
    public void execute(GameMap gameMap) {
        List<Entity> entities = createInitialEntities();
        int entitiesCountToSpawn = entities.size();
        List<Position> randomPositions = positionGenerator.generate(gameMap, entitiesCountToSpawn);
        spawn(gameMap, randomPositions, entities);
    }

    public void spawn(GameMap gameMap, List<Position> positions, List<Entity> entities) {
        if (positions.size() != entities.size()) {
            throw new IllegalStateException("Position count must match entities count.");
        }

        for (int i = 0; i < positions.size(); i++) {
            Position position = positions.get(i);
            Entity entity = entities.get(i);
            gameMap.put(position, entity);
        }
    }

    private List<Entity> createInitialEntities() {
        List<Entity> entities = new ArrayList<>();

        entities.add(entityFactory.create(Rabbit.class));

        entities.add(entityFactory.create(Fox.class));

        entities.add(entityFactory.create(Grass.class));


        entities.add(entityFactory.create(Tree.class));
        entities.add(entityFactory.create(Mountain.class));

        addEntities(entities, Tree.class, EXTRA_TREES_COUNT);
        addEntities(entities, Mountain.class, EXTRA_MOUNTAINS_COUNT);

        return entities;
    }

    private void addEntities(List<Entity> target, Class<? extends Entity> entityClass, int count) {
        for (int i = 0; i < count; i++) {
            target.add(entityFactory.create(entityClass));
        }
    }
}
