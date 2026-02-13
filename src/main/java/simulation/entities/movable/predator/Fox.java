package simulation.entities.movable.predator;

import simulation.entities.EntityType;

public class Fox extends Predator {
    // Дефолтные статы лиса (вынести в конфиг баланса при необходимости)
    private static final int BASE_SPEED = 1;
    private static final int BASE_HP = 5;
    private static final int BASE_ATK = 5;

    public Fox() {
        this(BASE_SPEED, BASE_HP, BASE_ATK);
    }

    public Fox(int speed, int hp, int atk) {
        super(speed, hp, atk);
    }

    @Override
    public EntityType type() {
        return EntityType.FOX;
    }
}
