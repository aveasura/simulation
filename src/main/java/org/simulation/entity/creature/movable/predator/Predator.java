package org.simulation.entity.creature.movable.predator;

import org.simulation.entity.Entity;
import org.simulation.entity.creature.Creature;
import org.simulation.game.GameMap;
import org.simulation.game.Position;

import java.util.Set;

public abstract class Predator extends Creature {

    private final int attackDamage;

    protected Predator(int speed, int healthPoint, int attackDamage) {
        super(speed, healthPoint);
        this.attackDamage = attackDamage;
    }

    @Override
    public final boolean isFood(Entity entity) {
        return supportsPrey(entity);
    }

    @Override
    protected final void interactWithTarget(GameMap gameMap, Position targetPosition, Entity targetEntity) {
        if (!supportsPrey(targetEntity)) {
            throw new IllegalStateException(
                    getClass().getSimpleName() + " cannot interact with non-prey target: "
                            + targetEntity.getClass().getSimpleName());
        }

        Creature prey = (Creature) targetEntity;
        attack(prey);

        if (!prey.isAlive()) {
            gameMap.remove(targetPosition);
        }
    }

    protected abstract Set<Class<? extends Creature>> preyTypes();

    private boolean supportsPrey(Entity entity) {
        return preyTypes().stream()
                .anyMatch(preyType -> preyType.isInstance(entity));
    }

    private void attack(Creature target) {
        target.takeDamage(this.attackDamage);
    }
}
