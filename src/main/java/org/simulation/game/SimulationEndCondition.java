package org.simulation.game;

import org.simulation.entity.Entity;
import org.simulation.entity.creature.movable.herbivore.Herbivore;
import org.simulation.entity.creature.movable.predator.Predator;
import org.simulation.entity.immovable.Grass;

/**
 * Симуляция завершается если на карте отсутствуют:
 *  Хищники, Травоядные, Трава.
 *  Либо если после завершения хода нет изменений на карте.
 */
public class SimulationEndCondition {

    public boolean isFinished(GameMap gameMap, boolean turnHadChanged) {

        boolean hasPredators = false;
        boolean hasHerbivores = false;
        boolean hasGrass = false;

        for (Entity entity : gameMap.getEntities().values()) {

            if (entity instanceof Predator) {
                hasPredators = true;
            } else if (entity instanceof Herbivore) {
                hasHerbivores = true;
            } else if (entity instanceof Grass) {
                hasGrass = true;
            }
        }

        if (!hasPredators || !hasHerbivores || !hasGrass) {
            return true;
        }

        return !turnHadChanged;
    }
}
