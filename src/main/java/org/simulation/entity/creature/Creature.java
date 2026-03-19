package org.simulation.entity.creature;

import org.simulation.entity.Entity;
import org.simulation.entity.creature.movable.Movable;
import org.simulation.game.GameMap;
import org.simulation.game.Position;
import org.simulation.path.PathFinder;

import java.util.List;
import java.util.function.Predicate;

public abstract class Creature extends Entity implements Movable {

    private final int speed;
    private int healthPoint;

    protected Creature(int speed, int healthPoint) {
        this.speed = speed;
        this.healthPoint = healthPoint;
    }

    @Override
    public final void makeMove(GameMap gameMap, PathFinder pathFinder) {
        if (isDead()) {
            return;
        }

        Position currentPosition = gameMap.getPosition(this);

        List<Position> path = findPathToFood(gameMap, currentPosition, pathFinder);
        if (!hasNextStep(path)) {
            return;
        }

        Position nextPosition = getReachablePosition(path);
        Position finalTargetPosition = getFinalTargetPosition(path);

        if (canInteractWithTarget(gameMap, nextPosition, finalTargetPosition)) {
            Entity target = gameMap.get(finalTargetPosition);
            interactWithTarget(gameMap, finalTargetPosition, target);

            // По правилам симуляции существо взаимодействует с целью,
            // но не занимает освободившуюся клетку в этот же ход.
            return;
        }

        moveTo(gameMap, currentPosition, nextPosition);
    }

    public void takeDamage(int attackDamage) {
        if (attackDamage <= 0) {
            throw new IllegalArgumentException("Damage must be > 0.");
        }
        healthPoint = Math.max(0, healthPoint - attackDamage);
    }

    public boolean isDead() {
        return healthPoint <= 0;
    }

    public abstract boolean isFood(Entity entity);

    protected abstract void interactWithTarget(GameMap gameMap, Position targetPosition, Entity targetEntity);

    private List<Position> findPathToFood(GameMap gameMap, Position currentPosition, PathFinder pathFinder) {

        Predicate<Position> isFoodPosition =
                position -> isFoodPosition(gameMap, position);

        Predicate<Position> isReachablePosition =
                position -> isReachablePosition(gameMap, position);

        return pathFinder.find(gameMap, currentPosition, isFoodPosition, isReachablePosition);
    }

    private boolean isFoodPosition(GameMap gameMap, Position position) {
        if (!gameMap.isOccupied(position)) {
            return false;
        }

        Entity entity = gameMap.get(position);
        return isFood(entity);
    }

    private boolean isReachablePosition(GameMap gameMap, Position position) {
        return !gameMap.isOccupied(position) || isFoodPosition(gameMap, position);
    }

    private boolean hasNextStep(List<Position> path) {
        return path.size() > 1;
    }

    private Position getReachablePosition(List<Position> path) {
        int lastPathIndex = getLastPathIndex(path);
        int reachablePathIndex = Math.min(speed, lastPathIndex);
        return path.get(reachablePathIndex);
    }

    private Position getFinalTargetPosition(List<Position> path) {
        int lastPathIndex = getLastPathIndex(path);
        return path.get(lastPathIndex);
    }

    private int getLastPathIndex(List<Position> path) {
        return path.size() - 1;
    }

    private boolean canInteractWithTarget(GameMap gameMap, Position nextPosition, Position finalTargetPosition) {
        return nextPosition.equals(finalTargetPosition) && gameMap.isOccupied(finalTargetPosition);
    }

    private void moveTo(GameMap gameMap, Position currentPosition, Position nextPosition) {
        gameMap.remove(currentPosition);
        gameMap.put(nextPosition, this);
    }
}
