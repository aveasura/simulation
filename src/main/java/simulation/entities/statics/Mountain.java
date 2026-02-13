package simulation.entities.statics;

import simulation.entities.Entity;
import simulation.entities.EntityType;

public class Mountain extends Entity {
    @Override
    public EntityType type() {
        return EntityType.MOUNTAIN;
    }
}
