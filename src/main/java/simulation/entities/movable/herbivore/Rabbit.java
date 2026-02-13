package simulation.entities.movable.herbivore;

import simulation.entities.EntityType;

public class Rabbit extends Herbivore {
    // Дефолтные статы кролика (вынести в конфиг баланса при необходимости)
    private static final int BASE_SPEED = 1;
    private static final int BASE_HP = 5;

    public Rabbit() {
        this(BASE_SPEED, BASE_HP);
    }

    public Rabbit(int speed, int hp) {
        super(speed, hp);
    }

    @Override
    public EntityType type() {
        return EntityType.RABBIT;
    }
}
