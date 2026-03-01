package org.simulation.entity.creature.movable.herbivore;

import org.simulation.entity.EntityType;

public class Rabbit extends Herbivore {

    // дефолтные статы кролика, можно вынести в конфиг
    private static final int DEFAULT_SPEED = 1;
    private static final int DEFAULT_HEALTH_POINT = 1;

    public Rabbit() {
        super(EntityType.RABBIT, DEFAULT_SPEED, DEFAULT_HEALTH_POINT);
    }
}
