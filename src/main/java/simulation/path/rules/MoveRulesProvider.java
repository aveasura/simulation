package simulation.path.rules;

import simulation.entities.movable.Creature;

public interface MoveRulesProvider {
    MoveRules forCreature(Creature creature);
}