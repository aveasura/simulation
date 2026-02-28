package org.simulation.action;

import org.simulation.entity.Entity;
import org.simulation.entity.EntityType;
import org.simulation.factory.Factory;
import org.simulation.game.GameMap;
import org.simulation.game.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SpawnEntitiesAction implements Actions {

    private final Factory<Entity> factory;
    private final Random random;

    public SpawnEntitiesAction(Factory<Entity> factory, Random random) {
        this.factory = factory;
        this.random = random;
    }

    @Override
    public void execute(GameMap gameMap) {
        List<Entity> entities = createEntities();
        List<Position> randomPositions = createRandomFreePositions(gameMap, entities.size());
        spawn(gameMap, randomPositions, entities);
    }

    private List<Entity> createEntities() {
        List<Entity> entities = new ArrayList<>();
        for (EntityType type : EntityType.values()) {
            Entity entity = factory.create(type);
            entities.add(entity);
        }

        return entities;
    }

    private List<Position> createRandomFreePositions(GameMap gameMap, int quantity) {
        List<Position> positions = new ArrayList<>();

        for (int i = 0; i < quantity; i++) {
            if (excessOfEntities(positions.size(), gameMap)) {
                System.out.println("Все ячейки карты заполнены"); // todo render
                return positions;
            }

            int x = random.nextInt(gameMap.getWidth());
            int y = random.nextInt(gameMap.getHeight());

            Position position = new Position(x, y);
            if (positions.contains(position)) {
                continue;
            }

            positions.add(position);
        }

        return positions;
    }

    private void spawn(GameMap gameMap, List<Position> positions, List<Entity> entities) {
        for (int i = 0; i < positions.size(); i++) {
            Position position = positions.get(i);
            Entity entity = entities.get(i);
            gameMap.place(position, entity);
        }
    }

    private boolean excessOfEntities(int entities, GameMap gameMap) {
        return entities >= gameMap.getHeight() * gameMap.getWidth();
    }
}
