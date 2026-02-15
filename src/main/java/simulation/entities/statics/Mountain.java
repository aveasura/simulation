package simulation.entities.statics;

import simulation.entities.Entity;
import simulation.entities.EntityType;

public class Mountain extends Entity implements StaticEntity{
    @Override
    public EntityType type() {
        return EntityType.MOUNTAIN;
    }
}
