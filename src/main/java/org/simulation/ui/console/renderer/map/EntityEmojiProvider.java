package org.simulation.ui.console.renderer.map;

import org.simulation.entity.Entity;
import org.simulation.entity.creature.movable.herbivore.Rabbit;
import org.simulation.entity.creature.movable.predator.Eagle;
import org.simulation.entity.immovable.Carrot;
import org.simulation.entity.immovable.Mountain;
import org.simulation.entity.immovable.Tree;

import java.util.Map;

public class EntityEmojiProvider implements EntitySymbolProvider {

    private static final String EMPTY_SYMBOL = "\u2B1B";

    private static final Map<Class<? extends Entity>, String> SYMBOLS = Map.of(
            Rabbit.class, "\uD83D\uDC07",
            Eagle.class, "\uD83E\uDD85",
            Carrot.class, "\uD83E\uDD55",
            Tree.class, "\uD83C\uDFDD\uFE0F",
            Mountain.class, "\uD83C\uDFDE\uFE0F"
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

    public String getEmptySymbol() {
        return EMPTY_SYMBOL;
    }
}
