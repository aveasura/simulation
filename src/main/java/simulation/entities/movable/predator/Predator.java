package simulation.entities.movable.predator;

import simulation.entities.movable.Creature;

public abstract class Predator extends Creature {

    private final int attackPower;

    protected Predator(int speed, int hp, int attackPower) {
        super(speed, hp);
        if (attackPower < 1) {
            throw new IllegalArgumentException("Сила атаки хищника должна быть >= 1");
        }
        this.attackPower = attackPower;
    }

    public int getAttackPower() {
        return attackPower;
    }
}
