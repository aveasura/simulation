package org.simulation.ui.console.renderer.map;

import org.simulation.entity.Entity;

public interface EntitySymbolProvider {

    String getSymbol(Entity entity);

    String getSymbol(Class<? extends Entity> entityClass);

    String getEmptySymbol();
}
