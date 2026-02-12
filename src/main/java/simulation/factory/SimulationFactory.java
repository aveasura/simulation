package simulation.factory;


import simulation.entities.Entity;
import simulation.entities.movable.herbivore.Rabbit;
import simulation.entities.movable.predator.Fox;
import simulation.entities.statics.Grass;
import simulation.entities.statics.Mountain;
import simulation.entities.statics.Tree;

public class SimulationFactory implements Factory<Entity> {

    @Override
    public Entity create(EntityType type) {

        switch (type) {
            case FOX -> {
                return new Fox();
            }
            case RABBIT -> {
                return new Rabbit();
            }
            case GRASS -> {
                return new Grass();
            }
            case TREE -> {
                return new Tree();
            }
            case MOUNTAIN -> {
                return new Mountain();
            }
        }

        throw new IllegalArgumentException("Неизвестный type: " + type);
    }
}
