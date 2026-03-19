package org.simulation.entity.creature.movable.herbivore;

import org.simulation.entity.Entity;
import org.simulation.entity.immovable.Carrot;

import java.util.Set;

public class Rabbit extends Herbivore {

    private static final Set<Class<? extends Entity>> FOOD_TYPES = Set.of(Carrot.class);

    private static final int DEFAULT_SPEED = 1;
    private static final int DEFAULT_HEALTH_POINT = 1;

    public Rabbit() {
        super(DEFAULT_SPEED, DEFAULT_HEALTH_POINT);
    }

    @Override
    protected Set<Class<? extends Entity>> getFoodTypes() {
        return FOOD_TYPES;
    }
}
