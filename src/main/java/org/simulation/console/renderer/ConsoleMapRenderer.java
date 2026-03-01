package org.simulation.console.renderer;

import org.simulation.console.Output;
import org.simulation.entity.Entity;
import org.simulation.game.GameMap;
import org.simulation.game.Position;

import java.util.Map;
import java.util.Objects;

public class ConsoleMapRenderer implements MapRenderer {

    private static final String EMPTY_CELL = " - ";

    private final Output output;

    public ConsoleMapRenderer(Output output) {
        this.output = Objects.requireNonNull(output, "output must not be null");
    }

    @Override
    public void render(GameMap gameMap, int step) {
        Map<Position, Entity> entities = gameMap.getEntities();
        int mapWidth = gameMap.getWidth();
        int mapHeight = gameMap.getHeight();

        output.println("Ход: " + step);
        for (int height = 0; height < mapHeight; height++) {
            renderRow(mapWidth, entities, height);
        }
    }

    private void renderRow(int mapWidth, Map<Position, Entity> entities, int height) {
        for (int width = 0; width < mapWidth; width++) {
            Position position = new Position(width, height);
            Entity entity = entities.get(position);
            String cellRepresentation = getCellSymbol(entity);
            output.print(cellRepresentation);
        }
        output.println();
    }

    private String getCellSymbol(Entity entity) {
        return entity != null
                ? getEntitySymbol(entity)
                : EMPTY_CELL;
    }

    private String getEntitySymbol(Entity entity) {
        return switch (entity.getType()) {
            case FOX -> " F ";
            case RABBIT -> " R ";
            case GRASS -> " G ";
            case TREE -> " T ";
            case MOUNTAIN -> " M ";
        };
    }
}
