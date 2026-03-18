package org.simulation.factory;

import org.simulation.entity.Entity;
import org.simulation.entity.creature.movable.herbivore.Rabbit;
import org.simulation.entity.creature.movable.predator.Eagle;
import org.simulation.entity.immovable.Carrot;
import org.simulation.entity.immovable.Mountain;
import org.simulation.entity.immovable.Tree;

import java.util.Map;
import java.util.function.Supplier;

public class RegistryEntityFactory implements EntityFactory {

    private final Map<Class<? extends Entity>, Supplier<? extends Entity>> creators = Map.of(
            Rabbit.class, Rabbit::new,
            Eagle.class, Eagle::new,
            Carrot.class, Carrot::new,
            Tree.class, Tree::new,
            Mountain.class, Mountain::new
    );

    @Override
    public Entity create(Class<? extends Entity> entityClass) {
        Supplier<? extends Entity> creator = creators.get(entityClass);
        if (creator == null) {
            throw new IllegalArgumentException("Unknown entity class: " + entityClass);
        }

        return creator.get();
    }
}
