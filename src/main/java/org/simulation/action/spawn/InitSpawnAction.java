package org.simulation.action.spawn;

import org.simulation.entity.Entity;
import org.simulation.factory.EntityFactory;
import org.simulation.game.GameMap;

import java.util.Map;

public class InitSpawnAction extends SpawnAction {

    public InitSpawnAction(EntityFactory factory, RandomFreePositionGenerator positionGenerator) {
        super(factory, positionGenerator);
    }

    @Override
    public void execute(GameMap gameMap) {
        for (Map.Entry<Class<? extends Entity>, Integer> entry : ENTITY_MAXIMUM_NUMBERS.entrySet()) {
            Class<? extends Entity> entityClass = entry.getKey();
            int count = entry.getValue();

            spawn(gameMap, entityClass, count);
        }
    }
}
