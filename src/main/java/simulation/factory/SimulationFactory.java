package simulation.factory;


import simulation.entities.Entity;
import simulation.entities.EntityType;
import simulation.entities.movable.herbivore.Rabbit;
import simulation.entities.movable.predator.Fox;
import simulation.entities.statics.Grass;
import simulation.entities.statics.Mountain;
import simulation.entities.statics.Tree;

public class SimulationFactory implements Factory<Entity> {

    @Override
    public Entity create(EntityType type) {
        return switch (type) {
            case FOX -> new Fox();
            case RABBIT -> new Rabbit();
            case GRASS -> new Grass();
            case TREE -> new Tree();
            case MOUNTAIN -> new Mountain();
        };
    }
}
