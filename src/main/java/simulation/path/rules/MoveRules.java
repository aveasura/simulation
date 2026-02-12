package simulation.path.rules;

import simulation.entities.movable.Creature;
import simulation.map.Position;
import simulation.map.SimulationMap;

public interface MoveRules {

    boolean isGoal(Creature mover, Position p, SimulationMap map);

    boolean canEnter(Creature mover, Position p, SimulationMap map);
}
