package simulation.renderer;

import simulation.entities.Entity;
import simulation.map.Position;
import simulation.map.SimulationMap;
import simulation.ui.ConsoleOutput;

public class ConsoleRenderer implements Renderer {
    private static final String EMPTY_CELL = " • ";
    private final ConsoleOutput output;

    public ConsoleRenderer(ConsoleOutput output) {
        this.output = output;
    }

    @Override
    public void renderMap(SimulationMap map, int step) {
        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                Position position = new Position(x, y);
                if (map.isOccupied(position)) {
                    Entity entity = map.getAt(position);
                    output.printf(" %s ", entity.type().symbol());
                } else {
                    output.print(EMPTY_CELL);
                }
            }
            output.println("");
        }
        output.printf("Ход: %d%n", step);
        output.println("p = пауза, c = продолжить, q = выход");
        output.println("");
    }
}
