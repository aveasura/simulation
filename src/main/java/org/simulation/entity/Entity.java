package org.simulation.entity;

public abstract class Entity {

    private final EntityType type;

    protected Entity(EntityType type) {
        this.type = type;
    }

    public EntityType getType() {
        return type;
    }
}
