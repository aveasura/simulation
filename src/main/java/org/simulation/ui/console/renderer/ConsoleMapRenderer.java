package org.simulation.ui.console.renderer;

import org.simulation.ui.console.output.Output;
import org.simulation.entity.Entity;
import org.simulation.game.GameMap;
import org.simulation.game.Position;

import java.util.Map;
import java.util.Objects;

public class ConsoleMapRenderer implements MapRenderer {

    private static final String EMPTY_CELL = " - ";
    private static final String FORMAT_SYMBOL_PATTERN = " %s ";

    private final Output output;
    private final EntitySymbolProvider symbolProvider;

    public ConsoleMapRenderer(Output output, EntitySymbolProvider symbolProvider) {
        this.output = Objects.requireNonNull(output, "output must not be null");
        this.symbolProvider = Objects.requireNonNull(symbolProvider, "symbolProvider must not be null");
    }

    @Override
    public void render(GameMap gameMap) {
        Map<Position, Entity> entities = gameMap.toMap();
        int mapWidth = gameMap.getWidth();
        int mapHeight = gameMap.getHeight();

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
        String symbol = symbolProvider.getSymbol(entity);
        return formatSymbol(symbol);
    }

    private String formatSymbol(String symbol) {
        return FORMAT_SYMBOL_PATTERN.formatted(symbol);
    }
}
