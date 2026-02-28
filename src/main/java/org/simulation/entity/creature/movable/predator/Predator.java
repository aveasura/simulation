package org.simulation.entity.creature.movable.predator;

import org.simulation.entity.Entity;
import org.simulation.entity.creature.Creature;
import org.simulation.entity.creature.movable.herbivore.Herbivore;
import org.simulation.game.GameMap;
import org.simulation.game.Position;

public abstract class Predator extends Creature {
    protected final int attackDamage;

    protected Predator(int speed, int healthPoint, int attackDamage) {
        super(speed, healthPoint);
        this.attackDamage = attackDamage;
    }

    @Override
    public boolean isFood(Entity entity) {
        return entity instanceof Herbivore;
    }

    @Override
    protected void interactWithTarget(GameMap gameMap, Position finalTarget, Entity entityTarget) {
        if (entityTarget instanceof Herbivore herbivore) {
            this.attack(herbivore);

            if (!herbivore.isAlive()) {
                gameMap.removeEntity(finalTarget);
            }
        }
    }

    public void attack(Creature target) {
        target.takeDamage(this.attackDamage);
    }
}
