package simulation.path.rules;

import simulation.entities.movable.Creature;
import simulation.entities.movable.herbivore.Herbivore;
import simulation.entities.movable.predator.Predator;

public class DefaultMoveRulesProvider implements MoveRulesProvider {
    private static final MoveRules PREDATOR = new PredatorRules();
    private static final MoveRules HERBIVORE = new HerbivoreRules();

    @Override
    public MoveRules forCreature(Creature c) {
        if (c instanceof Predator) {
            return PREDATOR;
        }

        if (c instanceof Herbivore) {
            return HERBIVORE;
        }

        throw new IllegalStateException("Неизвестное существо: " + c.getClass());
    }
}
