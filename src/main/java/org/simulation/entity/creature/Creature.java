package org.simulation.entity.creature;

import org.simulation.entity.Entity;
import org.simulation.entity.EntityType;
import org.simulation.entity.creature.movable.Movable;
import org.simulation.game.GameMap;
import org.simulation.game.Position;

import java.util.List;

public abstract class Creature extends Entity implements Movable {

    private final int speed;
    private int healthPoint;

    protected Creature(EntityType type, int speed, int healthPoint) {
        super(type);
        this.speed = speed;
        this.healthPoint = healthPoint;
    }

    @Override
    public boolean makeMove(GameMap gameMap, Position from, List<Position> path) {
        if (!hasStepToMake(path)) {
            return false;
        }

        int stepIndex = Math.min(this.speed, path.size() - 1);
        Position destination = path.get(stepIndex);
        Position finalTarget = path.get(path.size() - 1);

        if (destination.equals(finalTarget) && gameMap.isOccupied(finalTarget)) {
            Entity entityTarget = gameMap.getAt(finalTarget);
            interactWithTarget(gameMap, finalTarget, entityTarget);

            // По правилам симуляции существо взаимодействует с целью,
            // но не занимает освободившуюся клетку в этот же ход.
            return true;
        }

        gameMap.move(from, destination);
        return true;
    }

    public void takeDamage(int attackDamage) {
        if (attackDamage <= 0) {
            throw new IllegalArgumentException("Damage must be > 0.");
        }

        this.healthPoint -= attackDamage;
    }

    public boolean isAlive() {
        return healthPoint > 0;
    }

    public abstract boolean isFood(Entity entity);

    protected abstract void interactWithTarget(GameMap gameMap, Position finalTarget, Entity entityTarget);

    private boolean hasStepToMake(List<Position> path) {
        return path.size() > 1;
    }
}
