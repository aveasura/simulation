package simulation.entities.movable;

import simulation.entities.Entity;

public abstract class Creature extends Entity {

    private static final int MIN_SPEED = 1;
    private static final int MIN_HP = 1;
    private static final int DEAD_HP = 0;

    private final int speed;
    private int hp;

    protected Creature(int speed, int hp) {
        if (speed < MIN_SPEED) {
            throw new IllegalArgumentException("Скорость должна быть >= " + MIN_SPEED);
        }

        if (hp < MIN_HP) {
            throw new IllegalArgumentException("Хп должно быть >= " + MIN_HP);
        }

        this.speed = speed;
        this.hp = hp;
    }

    public void takeDamage(int amount) {
        if (amount <= 0) {
            return;
        }
        hp = Math.max(DEAD_HP, hp - amount);
    }

    public boolean isDead() {
        return hp <= DEAD_HP;
    }

    public int getSpeed() {
        return speed;
    }
}
