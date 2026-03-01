package org.simulation.entity.creature.movable.predator;

import org.simulation.entity.EntityType;

public class Fox extends Predator {

    // дефолтные статы лисы, можно вынести в конфиг
    private static final int DEFAULT_SPEED = 1;
    private static final int DEFAULT_HEALTH_POINT = 1;
    private static final int DEFAULT_ATTACK_DAMAGE = 1;

    public Fox() {
        super(EntityType.FOX, DEFAULT_SPEED, DEFAULT_HEALTH_POINT, DEFAULT_ATTACK_DAMAGE);
    }
}
