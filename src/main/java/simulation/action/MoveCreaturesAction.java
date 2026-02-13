package simulation.action;

import simulation.entities.Entity;
import simulation.entities.movable.Creature;
import simulation.entities.movable.herbivore.Herbivore;
import simulation.entities.movable.predator.Predator;
import simulation.entities.statics.Grass;
import simulation.map.Position;
import simulation.map.SimulationMap;
import simulation.path.NeighborsFinder;
import simulation.path.PathFinder;
import simulation.path.rules.MoveRules;
import simulation.path.rules.MoveRulesProvider;

import java.util.List;

public class MoveCreaturesAction implements Action {

    private final PathFinder pathFinder;
    private final MoveRulesProvider provider;
    private final NeighborsFinder neighbors;

    public MoveCreaturesAction(PathFinder pathFinder, MoveRulesProvider provider, NeighborsFinder neighbors) {
        this.pathFinder = pathFinder;
        this.provider = provider;
        this.neighbors = neighbors;
    }

    @Override
    public void execute(SimulationMap map) {
        List<Creature> creatures = map.getCreatures(); // копия

        for (Creature creature : creatures) {
            Position from = map.findPosition(creature);
            if (from == null) {
                continue; // могли убить раньше в этом же ходу
            }

            MoveRules rules = provider.forCreature(creature);

            Position targetNeighbor = findAdjacentTarget(from, creature, map, rules);
            if (targetNeighbor != null) {
                Entity target = map.getAt(targetNeighbor);
                if (target != null) {
                    handleTargetInteraction(creature, targetNeighbor, target, map);
                }
                continue;
            }

            for (int step = 0; step < creature.getSpeed(); step++) {
                Position next = pathFinder.findNextStep(from, creature, map, rules);
                if (next.equals(from)) {
                    break; // нет пути
                }

                if (!map.move(creature, next)) {
                    break; // кто то занял / вышли за границы и т д
                }
                from = next;

                // когда дошли до цели, стоп. След ход будет взаимодействием
                if (findAdjacentTarget(from, creature, map, rules) != null) {
                    break;
                }
            }
        }
    }

    private Position findAdjacentTarget(Position from, Creature creature, SimulationMap map, MoveRules rules) {
        for (Position p : neighbors.findNeighbors(from, map)) {
            if (rules.isTarget(creature, p, map)) {
                return p;
            }
        }
        return null;
    }

    private void handleTargetInteraction(Creature creature, Position targetPos, Entity target, SimulationMap map) {
        if (creature instanceof Herbivore && target instanceof Grass) {
            map.removeAt(targetPos);
            return;
        }

        if (creature instanceof Predator predator && target instanceof Herbivore prey) {
            prey.takeDamage(predator.getAttackPower());
            if (prey.isDead()) {
                map.removeAt(targetPos);
            }
        }
    }
}
