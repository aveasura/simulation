package simulation.factory;

import simulation.entities.Entity;
import simulation.entities.EntityType;

public interface Factory<T extends Entity>{

      T create(EntityType type);
}
