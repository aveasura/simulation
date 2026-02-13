package simulation.entities.movable.herbivore;

import simulation.entities.EntityType;

public class Rabbit extends Herbivore {

    public Rabbit() {
        super(1, 5);
    }

    @Override
    public EntityType type() {
        return EntityType.RABBIT;
    }
}
