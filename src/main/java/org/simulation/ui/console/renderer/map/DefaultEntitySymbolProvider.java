package org.simulation.ui.console.renderer.map;

import org.simulation.entity.Entity;
import org.simulation.entity.creature.movable.herbivore.Rabbit;
import org.simulation.entity.creature.movable.predator.Eagle;
import org.simulation.entity.immovable.Carrot;
import org.simulation.entity.immovable.Mountain;
import org.simulation.entity.immovable.Tree;

import java.util.Map;

public class DefaultEntitySymbolProvider implements EntitySymbolProvider {

    private static final String EMPTY_SYMBOL = "-";

    private static final Map<Class<? extends Entity>, String> SYMBOLS = Map.of(
            Rabbit.class, "R",
            Eagle.class, "E",
            Carrot.class, "C",
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

    @Override
    public String getEmptySymbol() {
        return EMPTY_SYMBOL;
    }
}
