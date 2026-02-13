package simulation.entities.movable.predator;

import simulation.entities.movable.Creature;

public abstract class Predator extends Creature {

    private static final int MIN_ATTACK_POWER = 1;

    private final int attackPower;

    protected Predator(int speed, int hp, int attackPower) {
        super(speed, hp);
        if (attackPower < MIN_ATTACK_POWER) {
            throw new IllegalArgumentException("Сила атаки хищника должна быть >= " + MIN_ATTACK_POWER);
        }
        this.attackPower = attackPower;
    }

    public int getAttackPower() {
        return attackPower;
    }
}
