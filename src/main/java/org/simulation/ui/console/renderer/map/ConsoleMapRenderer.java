package org.simulation.ui.console.renderer.map;

import org.simulation.entity.Entity;
import org.simulation.game.GameMap;
import org.simulation.game.Position;
import org.simulation.ui.console.output.Output;

import java.util.Objects;

public class ConsoleMapRenderer implements MapRenderer {

    private static final String FORMAT_SYMBOL_TEMPLATE = " %s ";

    private final Output output;
    private final EntitySymbolProvider symbolProvider;

    public ConsoleMapRenderer(Output output, EntitySymbolProvider symbolProvider) {
        this.output = Objects.requireNonNull(output, "output must not be null");
        this.symbolProvider = Objects.requireNonNull(symbolProvider, "symbolProvider must not be null");
    }

    @Override
    public void render(GameMap gameMap) {
        int height = gameMap.getHeight();
        int width = gameMap.getWidth();

        for (int y = 0; y < height; y++) {
            renderRow(y, width, gameMap);
        }
    }

    private void renderRow(int y, int width, GameMap gameMap) {
        for (int x = 0; x < width; x++) {
            Position position = new Position(x, y);
            if (gameMap.isOccupied(position)) {
                Entity entity = gameMap.get(position);
                String sprite = getEntitySymbol(entity);
                output.print(sprite);
            } else {
                String emptySymbol = getEmptySymbol();
                output.print(emptySymbol);
            }
        }

        output.println();
    }

    private String getEntitySymbol(Entity entity) {
        String symbol = symbolProvider.getSymbol(entity);
        return formatSymbol(symbol);
    }

    private String getEmptySymbol() {
        String symbol = symbolProvider.getEmptySymbol();
        return formatSymbol(symbol);
    }

    private String formatSymbol(String symbol) {
        return FORMAT_SYMBOL_TEMPLATE.formatted(symbol);
    }
}
