package simulation.path;

import simulation.entities.movable.Creature;
import simulation.map.Position;
import simulation.map.SimulationMap;
import simulation.path.rules.MoveRules;

import java.util.List;

public interface PathFinder {

    // под A*
    List<Position> findPath(Position from, Position to, Creature mover, SimulationMap map, MoveRules rules);

    // следующий шаг к ближайшей цели (grass/prey)
    Position findNextStep(Position from, Creature mover, SimulationMap map, MoveRules rules);
}
