package simulation.factory;

import simulation.entities.Entity;

public interface Factory<T extends Entity>{

      T create(EntityType type);
}
