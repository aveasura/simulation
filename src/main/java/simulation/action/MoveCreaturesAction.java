package simulation.action;

import simulation.entities.Entity;
import simulation.entities.movable.Creature;
import simulation.entities.movable.herbivore.Herbivore;
import simulation.entities.movable.predator.Predator;
import simulation.entities.statics.Grass;
import simulation.map.Position;
import simulation.map.SimulationMap;
import simulation.path.PathFinder;
import simulation.path.rules.MoveRules;
import simulation.path.rules.MoveRulesProvider;

import java.util.List;

public class MoveCreaturesAction implements Action {

    private final PathFinder pathFinder;
    private final MoveRulesProvider provider;

    public MoveCreaturesAction(PathFinder pathFinder, MoveRulesProvider provider) {
        this.pathFinder = pathFinder;
        this.provider = provider;
    }

    @Override
    public void execute(SimulationMap map) {
        List<Creature> creatures = map.getCreatures(); // копия

        for (Creature creature : creatures) {
            Position from = map.findPosition(creature);
            if (from == null) {
                continue; // могли убить раньше в этом же ходу
            }

            MoveRules rules = rulesFor(creature);

            // 1) Если цель рядом — взаимодействуем и НЕ двигаемся (ход потрачен)
            Position goalNeighbor = findAdjacentGoal(from, creature, map, rules);
            if (goalNeighbor != null) {
                Entity target = map.getAt(goalNeighbor); // по идее не null, но пусть будет явно
                if (target != null) {
                    handleGoalInteraction(creature, goalNeighbor, target, map);
                }
                continue;
            }

            // 2) Иначе — только движение (без атаки/еды в этом же ходу)
            for (int step = 0; step < creature.getSpeed(); step++) {
                Position next = pathFinder.findNextStep(from, creature, map, rules);
                if (next.equals(from)) {
                    break; // нет пути / стоим
                }

                // Мы не заходим на занятые клетки (даже если это цель)
                if (map.isOccupied(next)) {
                    break;
                }

                if (!map.move(creature, next)) {
                    break;
                }
                from = next;
            }
        }
    }

    // 4-соседей проверяем на "цель"
    private Position findAdjacentGoal(Position from, Creature creature, SimulationMap map, MoveRules rules) {
        int x = from.x();
        int y = from.y();

        Position[] neighbors = {
                new Position(x - 1, y),
                new Position(x + 1, y),
                new Position(x, y - 1),
                new Position(x, y + 1)
        };

        for (Position p : neighbors) {
            if (!map.isInside(p)) continue;
            if (rules.isGoal(creature, p, map)) {
                return p;
            }
        }
        return null;
    }


    private void handleGoalInteraction(Creature creature, Position targetPos, Entity target, SimulationMap map) {
        // Herbivore: съесть траву
        if (creature instanceof Herbivore && target instanceof Grass) {
            map.removeAt(targetPos);
            return;
        }

        // Predator: атаковать травоядное (снижаем hp, если 0 - исчезает)
        if (creature instanceof Predator predator && target instanceof Herbivore prey) {
            prey.takeDamage(predator.getAttackPower());
            if (prey.isDead()) {
                map.removeAt(targetPos);
            }
        }
    }

    private MoveRules rulesFor(Creature creature) {
        return provider.forCreature(creature);
    }
}
