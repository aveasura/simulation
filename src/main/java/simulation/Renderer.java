package simulation;

import simulation.entities.Entity;
import simulation.map.Position;
import simulation.map.SimulationMap;

public class Renderer {
    private static final String EMPTY_CELL = " • ";
    private static final char RABBIT_REPRESENTATION = 'R';
    private static final char FOX_REPRESENTATION = 'F';
    private static final char GRASS_REPRESENTATION = 'G';
    private static final char TREE_REPRESENTATION = 'T';
    private static final char MOUNTAIN_REPRESENTATION = 'M';

    public void renderMap(SimulationMap map, int stepCounter) {
        for (int i = 0; i < map.getHeight(); i++) {
            for (int j = 0; j < map.getWidth(); j++) {
                Position position = new Position(j, i);
                if (map.isOccupied(position)) {
                    Entity entity = map.getAt(position);
                    System.out.print(" " + getRepresentation(entity) + " ");
                } else {
                    System.out.print(EMPTY_CELL);
                }
            }
            System.out.println();
        }
        System.out.println("Количество ходов: " + stepCounter + "\n");
    }

    private char getRepresentation(Entity entity) {
        return switch (entity.getClass().getSimpleName()) {
            case "Rabbit" -> RABBIT_REPRESENTATION;
            case "Fox" -> FOX_REPRESENTATION;
            case "Grass" -> GRASS_REPRESENTATION;
            case "Tree" -> TREE_REPRESENTATION;
            case "Mountain" -> MOUNTAIN_REPRESENTATION;
            default -> throw new IllegalStateException("Неизвестное значение: " + entity.getClass().getSimpleName());
        };
    }
}
