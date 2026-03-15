package org.simulation.ui.console.renderer.map;

import org.simulation.entity.Entity;
import org.simulation.entity.creature.movable.herbivore.Rabbit;
import org.simulation.entity.creature.movable.predator.Fox;
import org.simulation.entity.immovable.Grass;
import org.simulation.entity.immovable.Mountain;
import org.simulation.entity.immovable.Tree;

import java.util.Map;

public class DefaultEntitySymbolProvider implements EntitySymbolProvider {

    private static final Map<Class<? extends Entity>, String> SYMBOLS = Map.of(
            Rabbit.class, "R",
            Fox.class, "F",
            Grass.class, "G",
            Tree.class, "T",
            Mountain.class, "M"
    );

    @Override
    public String getSymbol(Entity entity) {
        Class<? extends Entity> entityClass = entity.getClass();
        String symbol = SYMBOLS.get(entityClass);
        if (symbol == null) {
            throw new IllegalArgumentException("Unknown entity class: " + entityClass);
        }

        return symbol;
    }
}
