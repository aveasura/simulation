package org.simulation.factory;

import org.simulation.entity.EntityType;

public interface Factory<T> {
    T create(EntityType type);
}
