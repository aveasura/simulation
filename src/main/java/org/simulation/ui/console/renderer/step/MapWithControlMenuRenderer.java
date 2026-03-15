package org.simulation.ui.console.renderer.step;

import org.simulation.game.GameMap;
import org.simulation.ui.console.renderer.map.MapRenderer;

import java.util.Objects;

public class MapWithControlMenuRenderer implements MapRenderer {

    private final MapRenderer delegate;
    private final Runnable renderControlMenu;

    public MapWithControlMenuRenderer(MapRenderer delegate, Runnable renderControlMenu) {
        this.delegate = Objects.requireNonNull(delegate, "delegate must not be null");
        this.renderControlMenu = Objects.requireNonNull(renderControlMenu, "renderControlMenu must not be null");
    }

    @Override
    public void render(GameMap gameMap) {
        delegate.render(gameMap);
        renderControlMenu.run();
    }
}