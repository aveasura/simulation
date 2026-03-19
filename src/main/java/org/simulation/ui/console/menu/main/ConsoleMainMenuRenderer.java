package org.simulation.ui.console.menu.main;

import org.simulation.entity.creature.movable.herbivore.Rabbit;
import org.simulation.entity.creature.movable.predator.Eagle;
import org.simulation.entity.immovable.Carrot;
import org.simulation.entity.immovable.Mountain;
import org.simulation.entity.immovable.Tree;
import org.simulation.ui.console.output.Output;
import org.simulation.ui.console.renderer.map.EntitySymbolProvider;

import java.util.Objects;

public class ConsoleMainMenuRenderer implements MainMenuRenderer {

    private final EntitySymbolProvider symbolProvider;
    private final Output output;

    public ConsoleMainMenuRenderer(EntitySymbolProvider symbolProvider, Output output) {
        this.symbolProvider = Objects.requireNonNull(symbolProvider, "symbolProvider must not be null");
        this.output = Objects.requireNonNull(output, "output must not be null");
    }

    @Override
    public void renderRules() {
        String rules = getRules();
        output.println(rules);
    }

    private String getRules() {
        return """
                Правила симуляции

                - После запуска на карте появляется стартовый набор сущностей.
                - На каждом ходу существа перемещаются и взаимодействуют друг с другом.
                - Если количество некоторых сущностей падает ниже заданного минимума,
                  они автоматически добавляются снова.

                - Обозначения на карте:
                    %s - %s (Хищник, охотится на %s)
                    %s - %s (Пища для %s)
                    %s - %s (Пища для %s)
                    %s - %s (Непроходимое препятствие)
                    %s - %s (Непроходимое препятствие)
                   \s"""
                .formatted(symbolProvider.getSymbol(Eagle.class), Eagle.class.getSimpleName(),
                        symbolProvider.getSymbol(Rabbit.class),
                        symbolProvider.getSymbol(Rabbit.class), Rabbit.class.getSimpleName(),
                        symbolProvider.getSymbol(Eagle.class),
                        symbolProvider.getSymbol(Carrot.class), Carrot.class.getSimpleName(),
                        symbolProvider.getSymbol(Rabbit.class),
                        symbolProvider.getSymbol(Tree.class), Tree.class.getSimpleName(),
                        symbolProvider.getSymbol(Mountain.class), Mountain.class.getSimpleName()
                );
    }
}
