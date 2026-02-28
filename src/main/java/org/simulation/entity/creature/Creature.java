package org.simulation.entity.creature;

import org.simulation.entity.Entity;
import org.simulation.entity.creature.movable.Movable;
import org.simulation.game.GameMap;
import org.simulation.game.Position;

import java.util.List;

public abstract class Creature extends Entity implements Movable {
    protected final int speed;
    protected int healthPoint;

    protected Creature(int speed, int healthPoint) {
        this.speed = speed;
        this.healthPoint = healthPoint;
    }

    @Override
    public void makeMove(GameMap gameMap, Position from, List<Position> path) {
        if (path.isEmpty()) {
            return;
        }

        int stepIndex = Math.min(this.speed, path.size() - 1);
        Position destination = path.get(stepIndex);
        Position finalTarget = path.get(path.size() - 1);

        if (destination.equals(finalTarget) && gameMap.isOccupied(finalTarget)) {
            Entity entityTarget = gameMap.getAt(finalTarget);
            interactWithTarget(gameMap, finalTarget, entityTarget);
            return;
        }

        gameMap.move(from, destination);
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
}
