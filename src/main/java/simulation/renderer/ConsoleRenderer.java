package simulation.renderer;

import simulation.entities.Entity;
import simulation.map.Position;
import simulation.map.SimulationMap;

public class ConsoleRenderer implements Renderer {
    private static final String EMPTY_CELL = " • ";

    @Override
    public void renderMap(SimulationMap map, int stepCounter) {
        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                Position position = new Position(x, y);
                if (map.isOccupied(position)) {
                    Entity entity = map.getAt(position);
                    System.out.printf(" %s ", entity.type().symbol());
                } else {
                    System.out.print(EMPTY_CELL);
                }
            }
            System.out.println();
        }
        System.out.printf("Количество ходов: %d \n\n", stepCounter);
    }
}
