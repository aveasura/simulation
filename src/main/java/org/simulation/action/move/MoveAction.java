package org.simulation.action.move;

import org.simulation.action.Action;
import org.simulation.entity.Entity;
import org.simulation.entity.creature.Creature;
import org.simulation.game.GameMap;
import org.simulation.path.PathFinder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class MoveAction implements Action {

    private final PathFinder pathFinder;

    public MoveAction(PathFinder pathFinder) {
        this.pathFinder = Objects.requireNonNull(pathFinder, "pathFinder must not be null");
    }

    @Override
    public void execute(GameMap gameMap) {
        List<Creature> creatures = getCreaturesSnapshot(gameMap);
        for (Creature creature : creatures) {
            if (!gameMap.containsEntity(creature)) {
                continue;
            }

            creature.makeMove(gameMap, pathFinder);
        }
    }

    private List<Creature> getCreaturesSnapshot(GameMap gameMap) {
        List<Creature> creatures = new ArrayList<>();
        Collection<Entity> entities = gameMap.toMap().values();

        for (Entity entity : entities) {
            if (!(entity instanceof Creature creature)) {
                continue;
            }

            creatures.add(creature);
        }

        return creatures;
    }
}
