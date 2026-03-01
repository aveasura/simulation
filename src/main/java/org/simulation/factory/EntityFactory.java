package org.simulation.factory;

import org.simulation.entity.Entity;
import org.simulation.entity.EntityType;

public interface EntityFactory {
    Entity create(EntityType type);
}
