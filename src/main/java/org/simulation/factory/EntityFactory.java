package org.simulation.factory;

import org.simulation.entity.EntityType;
import org.simulation.entity.Entity;
import org.simulation.entity.creature.movable.herbivore.Rabbit;
import org.simulation.entity.creature.movable.predator.Fox;
import org.simulation.entity.immovable.Grass;
import org.simulation.entity.immovable.Mountain;
import org.simulation.entity.immovable.Tree;

public class EntityFactory implements Factory<Entity> {

    @Override
    public Entity create(EntityType type) {
        return switch (type) {
            case RABBIT     -> new Rabbit();
            case FOX        -> new Fox();
            case GRASS      -> new Grass();
            case TREE       -> new Tree();
            case MOUNTAIN   -> new Mountain();
        };
    }
}
