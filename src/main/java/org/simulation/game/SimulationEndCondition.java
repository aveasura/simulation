package org.simulation.game;

import org.simulation.entity.Entity;
import org.simulation.entity.creature.movable.herbivore.Herbivore;
import org.simulation.entity.creature.movable.predator.Predator;
import org.simulation.entity.immovable.Grass;

import java.util.Collection;

public final class SimulationEndCondition {

    public boolean isFinished(GameMap gameMap, boolean turnChanged) {
        boolean hasPredators = false;
        boolean hasHerbivores = false;
        boolean hasGrass = false;

        Collection<Entity> entities = gameMap.toMap().values();
        for (Entity entity : entities) {

            if (entity instanceof Predator) {
                hasPredators = true;
            } else if (entity instanceof Herbivore) {
                hasHerbivores = true;
            } else if (entity instanceof Grass) {
                hasGrass = true;
            }
        }

        boolean missingRequiredEntities = !hasPredators || !hasHerbivores || !hasGrass;
        boolean noChangesThisTurn = !turnChanged;

        return missingRequiredEntities || noChangesThisTurn;
    }
}
