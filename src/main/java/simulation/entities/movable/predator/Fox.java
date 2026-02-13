package simulation.entities.movable.predator;

import simulation.entities.EntityType;

public class Fox extends Predator {

    public Fox() {
        super(1, 8, 3);
    }

    @Override
    public EntityType type() {
        return EntityType.FOX;
    }
}
