package simulation.path.rules;

import simulation.entities.Entity;
import simulation.entities.movable.Creature;
import simulation.entities.statics.Grass;
import simulation.map.Position;
import simulation.map.SimulationMap;

public class HerbivoreRules implements MoveRules {

    @Override
    public boolean isTarget(Creature mover, Position p, SimulationMap map) {
        Entity e = map.getAt(p);
        return e instanceof Grass;
    }

    @Override
    public boolean canEnter(Creature mover, Position p, SimulationMap map) {
        return map.getAt(p) == null;
    }
}
