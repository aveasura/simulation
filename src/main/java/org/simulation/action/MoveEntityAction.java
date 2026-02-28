package org.simulation.action;

import org.simulation.path.PathFinder;
import org.simulation.entity.Entity;
import org.simulation.entity.creature.Creature;
import org.simulation.game.GameMap;
import org.simulation.game.Position;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class MoveEntityAction implements Actions {

    private final PathFinder pathFinder;

    public MoveEntityAction(PathFinder pathFinder) {
        this.pathFinder = pathFinder;
    }

    @Override
    public boolean execute(GameMap gameMap) {
        boolean turnHadChanges = false;

        Map<Position, Entity> snapshot = new HashMap<>(gameMap.getEntities());

        for (Map.Entry<Position, Entity> entry : snapshot.entrySet()) {
            Position currentPosition = entry.getKey();
            Entity entity = entry.getValue();

            Entity actualEntity = gameMap.getAt(currentPosition);
            if (actualEntity != entity) {
                continue;
            }

            if (entity instanceof Creature creature) {
                Predicate<Position> isTarget = position -> {
                    if (!gameMap.isOccupied(position)) {
                        return false;
                    }

                    Entity target = gameMap.getAt(position);
                    return creature.isFood(target);
                };

                Predicate<Position> canStep = position -> !gameMap.isOccupied(position) || isTarget.test(position);

                List<Position> path = pathFinder.find(gameMap, currentPosition, isTarget, canStep);

                boolean changed = creature.makeMove(gameMap, currentPosition, path);
                if (changed) {
                    turnHadChanges = true;
                }
            }
        }

        return turnHadChanges;
    }
}
