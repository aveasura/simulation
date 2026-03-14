package org.simulation.entity.creature.movable.herbivore;

import org.simulation.entity.Entity;
import org.simulation.entity.EntityType;
import org.simulation.entity.creature.Creature;
import org.simulation.entity.immovable.Grass;
import org.simulation.game.GameMap;
import org.simulation.game.Position;

public abstract class Herbivore extends Creature {

    protected Herbivore(EntityType type, int speed, int healthPoint) {
        super(type, speed, healthPoint);
    }

    @Override
    public boolean isFood(Entity entity) {
        return entity instanceof Grass;
    }

    @Override
    protected void interactWithTarget(GameMap gameMap, Position finalTarget, Entity entityTarget) {
        if (entityTarget instanceof Grass) {
            gameMap.remove(finalTarget);
        }
    }
}
