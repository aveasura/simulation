package org.simulation.action;

import org.simulation.entity.Entity;
import org.simulation.entity.creature.movable.herbivore.Herbivore;
import org.simulation.entity.creature.movable.herbivore.Rabbit;
import org.simulation.entity.creature.movable.predator.Fox;
import org.simulation.entity.creature.movable.predator.Predator;
import org.simulation.entity.immovable.Grass;
import org.simulation.factory.EntityFactory;
import org.simulation.game.GameMap;
import org.simulation.game.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// TODO: refactor
public class RespawnEntitiesAction implements Action {

    private static final int MAX_PREDATOR_COUNT = 3;
    private static final int MIN_HERBIVORES_COUNT = 3;
    private static final int MIN_GRASS_COUNT = 6;

    private final RandomFreePositionGenerator positionGenerator;
    private final EntityFactory entityFactory;

    public RespawnEntitiesAction(RandomFreePositionGenerator positionGenerator, EntityFactory entityFactory) {
        this.positionGenerator = Objects.requireNonNull(positionGenerator, "positionGenerator must not be null");
        this.entityFactory = Objects.requireNonNull(entityFactory, "entityFactory must not be null");
    }

    @Override
    public void execute(GameMap gameMap) {
        EntityCounts counts = countEntities(gameMap);

        int missingHerbivores = Math.max(0, MIN_HERBIVORES_COUNT - counts.herbivores());
        int missingGrass = Math.max(0, MIN_GRASS_COUNT - counts.grass());

        int futureHerbivoreCount = counts.herbivores() + missingHerbivores;
        int desiredPredators = Math.min(MAX_PREDATOR_COUNT, futureHerbivoreCount);
        int missingPredators = Math.max(0, desiredPredators - counts.predators());

        List<Entity> entitiesToSpawn = new ArrayList<>();

        addEntities(entitiesToSpawn, Rabbit.class, missingHerbivores);
        addEntities(entitiesToSpawn, Grass.class, missingGrass);
        addEntities(entitiesToSpawn, Fox.class, missingPredators);

        if (entitiesToSpawn.isEmpty()) {
            return;
        }

        List<Position> positions = positionGenerator.generate(gameMap, entitiesToSpawn.size());
        placeEntities(gameMap, positions, entitiesToSpawn);
    }

    private EntityCounts countEntities(GameMap gameMap) {
        int predatorCount = 0;
        int herbivoreCount = 0;
        int grassCount = 0;

        for (Entity entity : gameMap.toMap().values()) {
            if (entity instanceof Predator) {
                predatorCount++;
            } else if (entity instanceof Herbivore) {
                herbivoreCount++;
            } else if (entity instanceof Grass) {
                grassCount++;
            }
        }

        return new EntityCounts(predatorCount, herbivoreCount, grassCount);
    }

    private void addEntities(List<Entity> target, Class<? extends Entity> entityClass, int count) {
        for (int i = 0; i < count; i++) {
            Entity entity = entityFactory.create(entityClass);
            target.add(entity);
        }
    }

    private void placeEntities(GameMap gameMap, List<Position> positions, List<Entity> entities) {
        if (positions.size() != entities.size()) {
            throw new IllegalStateException("Positions count must match entities count.");
        }

        for (int i = 0; i < positions.size(); i++) {
            gameMap.put(positions.get(i), entities.get(i));
        }
    }

    private record EntityCounts(int predators, int herbivores, int grass) {
    }
}