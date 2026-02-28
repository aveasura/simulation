package org.simulation.console.renderer;

import org.simulation.console.ConsoleOutput;
import org.simulation.console.Output;
import org.simulation.entity.Entity;
import org.simulation.game.GameMap;
import org.simulation.game.Position;

import java.util.Map;

public class ConsoleMapRenderer implements MapRenderer {

    private static final String EMPTY_POSITION = " - ";

    private final Output output;

    public ConsoleMapRenderer(Output output) {
        this.output = output;
    }

    @Override
    public void render(GameMap gameMap, int step) {
        Map<Position, Entity> entities = gameMap.getEntities();
        output.println("Ход: " + step);
        for (int height = 0; height < gameMap.getHeight(); height++) {
            for (int width = 0; width < gameMap.getWidth(); width++) {
                Position position = new Position(width, height);
                if (entities.containsKey(position)) {
                    Entity entity = entities.get(position);
                    output.print(" " + entity.getClass().getSimpleName().charAt(0) + " ");
                    continue;
                }
                output.print(EMPTY_POSITION);
            }
            output.println("");
        }
        output.println("");
    }
}
