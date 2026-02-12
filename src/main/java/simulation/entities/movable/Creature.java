package simulation.entities.movable;

import simulation.entities.Entity;

public abstract class Creature extends Entity {

    private final int speed;
    private int hp;

    protected Creature(int speed, int hp) {
        if (speed < 1) {
            throw new IllegalArgumentException("Скорость должна быть >= 1");
        }

        if (hp < 1) {
            throw new IllegalArgumentException("Хп должно быть >= 1");
        }

        this.speed = speed;
        this.hp = hp;
    }

    public void takeDamage(int amount) {
        if (amount <= 0) {
            return;
        }
        hp -= amount;
    }

    public boolean isDead() {
        return hp <= 0;
    }

    public int getSpeed() {
        return speed;
    }
}
