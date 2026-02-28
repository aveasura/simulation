package org.simulation.entity.creature.movable.predator;

public class Fox extends Predator {

    private static final int DEFAULT_SPEED = 1;
    private static final int DEFAULT_HEALTH_POINT = 1;
    private static final int DEFAULT_ATTACK_DAMAGE = 1;

    public Fox() {
        super(DEFAULT_SPEED, DEFAULT_HEALTH_POINT, DEFAULT_ATTACK_DAMAGE);
    }
}
