package org.simulation.factory;

import org.simulation.entity.Entity;

public interface EntityFactory {
    Entity create(Class<? extends Entity> entityClass);
}
