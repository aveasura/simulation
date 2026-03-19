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
        if (entity == null) {
            throw new IllegalArgumentException("Entity must not be null");
        }

        return validateAndGetSymbol(entity.getClass());
    }

    @Override
    public String getSymbol(Class<? extends Entity> entityClass) {
        return validateAndGetSymbol(entityClass);
    }

    @Override
    public String getEmptySymbol() {
        return EMPTY_SYMBOL;
    }

    private static String validateAndGetSymbol(Class<? extends Entity> entityClass) {
        String symbol = SYMBOLS.get(entityClass);
        if (symbol == null) {
            throw new IllegalArgumentException("Unknown entity class: " + entityClass);
        }
        return symbol;
    }
}
