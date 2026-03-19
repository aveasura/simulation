package org.simulation.action.move;

import org.simulation.action.Action;
import org.simulation.entity.creature.Creature;
import org.simulation.game.GameMap;
import org.simulation.path.PathFinder;

import java.util.List;
import java.util.Objects;

public class MoveAction implements Action {

    private final PathFinder pathFinder;

    public MoveAction(PathFinder pathFinder) {
        this.pathFinder = Objects.requireNonNull(pathFinder, "pathFinder must not be null");
    }

    @Override
    public void execute(GameMap gameMap) {
        List<Creature> creatures = getCreatures(gameMap);
        for (Creature creature : creatures) {
            creature.makeMove(gameMap, pathFinder);
        }
    }

    private List<Creature> getCreatures(GameMap gameMap) {
        return gameMap.getEntitiesByType(Creature.class);
    }
}
