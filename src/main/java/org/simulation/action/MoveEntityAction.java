package org.simulation.action;

import org.simulation.entity.Entity;
import org.simulation.entity.creature.Creature;
import org.simulation.game.GameMap;
import org.simulation.game.Position;
import org.simulation.path.PathFinder;

import java.util.Map;
import java.util.Objects;

public class MoveEntityAction implements Action {

    private final PathFinder pathFinder;

    public MoveEntityAction(PathFinder pathFinder) {
        this.pathFinder = Objects.requireNonNull(pathFinder, "pathFinder must not be null");
    }

    @Override
    public void execute(GameMap gameMap) {
        Map<Position, Entity> entities = gameMap.toMap();

        for (Entity entity : entities.values()) {
            if (!(entity instanceof Creature creature)) {
                continue;
            }

            if (!gameMap.containsEntity(creature)) {
                continue;
            }

            creature.makeMove(gameMap, pathFinder);
        }
    }
}
