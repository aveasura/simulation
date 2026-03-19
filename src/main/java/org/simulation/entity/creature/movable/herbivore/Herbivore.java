package org.simulation.entity.creature.movable.herbivore;

import org.simulation.entity.Entity;
import org.simulation.entity.creature.Creature;
import org.simulation.game.GameMap;
import org.simulation.game.Position;

import java.util.Set;

public abstract class Herbivore extends Creature {

    protected Herbivore(int speed, int healthPoint) {
        super(speed, healthPoint);
    }

    @Override
    public final boolean isFood(Entity entity) {
        return supportsFood(entity);
    }

    @Override
    protected final void interactWithTarget(GameMap gameMap, Position targetPosition, Entity targetEntity) {
        if (!supportsFood(targetEntity)) {
            throw new IllegalStateException(
                    getClass().getSimpleName() + " cannot interact with non-food target: "
                            + targetEntity.getClass().getSimpleName());
        }

        gameMap.remove(targetPosition);
    }

    protected abstract Set<Class<? extends Entity>> getFoodTypes();

    private boolean supportsFood(Entity entity) {
        return getFoodTypes().stream()
                .anyMatch(foodType -> foodType.isInstance(entity));
    }
}
