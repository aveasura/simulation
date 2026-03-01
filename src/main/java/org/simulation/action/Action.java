package org.simulation.action;

import org.simulation.game.GameMap;

public interface Action {
    boolean execute(GameMap gameMap);
}
