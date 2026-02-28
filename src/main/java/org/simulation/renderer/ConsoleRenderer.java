package org.simulation.renderer;

import org.simulation.entity.Entity;
import org.simulation.game.GameMap;
import org.simulation.game.Position;

import java.util.Map;

public class ConsoleRenderer implements Renderer {

    @Override
    public void render(GameMap gameMap) {
        Map<Position, Entity> entities = gameMap.getEntities();
        for (int height = 0; height < gameMap.getHeight(); height++) {
            for (int width = 0; width < gameMap.getWidth(); width++) {
                Position position = new Position(width, height);
                if (entities.containsKey(position)) {
                    Entity entity = entities.get(position);
                    System.out.print(" " + entity.getClass().getSimpleName().charAt(0) + " ");
                    continue;
                }
                System.out.print(" - ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
