package simulation;

import simulation.entities.movable.Creature;
import simulation.map.Position;
import simulation.map.SimulationMap;
import simulation.path.NeighborsFinder;
import simulation.path.PathFinder;
import simulation.path.rules.MoveRules;
import simulation.path.rules.MoveRulesProvider;

public final class ReachableTargetCondition implements ContinueCondition {
    private final PathFinder pathFinder;
    private final MoveRulesProvider provider;
    private final NeighborsFinder neighbors;

    public ReachableTargetCondition(PathFinder pathFinder, MoveRulesProvider provider, NeighborsFinder neighbors) {
        this.pathFinder = pathFinder;
        this.provider = provider;
        this.neighbors = neighbors;
    }

    @Override
    public boolean shouldContinue(SimulationMap map) {

        for (Creature c : map.getCreatures()) {
            Position from = map.findPosition(c);
            if (from == null) {
                continue;
            }

            MoveRules rules = provider.forCreature(c);

            if (hasAdjacentTarget(from, c, map, rules)) {
                return true;
            }

            Position next = pathFinder.findNextStep(from, c, map, rules);
            if (!next.equals(from)) {
                return true;
            }
        }

        return false;
    }

    private boolean hasAdjacentTarget(Position from, Creature creature, SimulationMap map, MoveRules rules) {
        for (Position p : neighbors.findNeighbors(from, map)) {
            if (rules.isTarget(creature, p, map)) {
                return true;
            }
        }

        return false;
    }
}
