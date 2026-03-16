package org.simulation.entity.creature.movable.predator;

import org.simulation.entity.creature.Creature;
import org.simulation.entity.creature.movable.herbivore.Herbivore;

import java.util.Set;

public class Eagle extends Predator {

    private static final Set<Class<? extends Creature>> PREY_TYPES = Set.of(Herbivore.class);

    private static final int DEFAULT_SPEED = 1;
    private static final int DEFAULT_HEALTH_POINT = 1;
    private static final int DEFAULT_ATTACK_DAMAGE = 1;

    public Eagle() {
        super(DEFAULT_SPEED, DEFAULT_HEALTH_POINT, DEFAULT_ATTACK_DAMAGE);
    }

    @Override
    protected Set<Class<? extends Creature>> preyTypes() {
        return PREY_TYPES;
    }
}
