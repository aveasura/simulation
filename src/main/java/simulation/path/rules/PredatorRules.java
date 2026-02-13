package simulation.path.rules;

import simulation.entities.Entity;
import simulation.entities.movable.Creature;
import simulation.entities.movable.herbivore.Herbivore;
import simulation.map.Position;
import simulation.map.SimulationMap;

public class PredatorRules implements MoveRules {

    @Override
    public boolean isTarget(Creature mover, Position p, SimulationMap map) {
        Entity e = map.getAt(p);
        return e instanceof Herbivore;
    }

    @Override
    public boolean canEnter(Creature mover, Position p, SimulationMap map) {
        return map.getAt(p) == null;
    }
}
