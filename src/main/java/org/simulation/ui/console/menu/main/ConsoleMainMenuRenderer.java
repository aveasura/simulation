package org.simulation.ui.console.menu.main;

import org.simulation.ui.console.output.Output;

import java.util.Objects;

public class ConsoleMainMenuRenderer implements MainMenuRenderer {

    private static final String RULES = """
            Правила симуляции

            - После запуска на карте появляется стартовый набор сущностей.
            - На каждом ходу существа перемещаются и взаимодействуют друг с другом.
            - Если количество некоторых сущностей падает ниже заданного минимума,
              они автоматически добавляются снова.

            - Обозначения на карте:
                E - Eagle (Орел, хищник)
                R - Rabbit (Кролик, пища для хищников)
                C - Carrot (Морковка, пища для кролика)
                T - Tree (дерево, препятствие)
                M - Mountain (гора, препятствие)

            - Во время симуляции можно:
                1 - поставить симуляцию на паузу
                2 - продолжить симуляцию
                3 - завершить симуляцию
            """;

    private final Output output;

    public ConsoleMainMenuRenderer(Output output) {
        this.output = Objects.requireNonNull(output, "output must not be null");
    }

    @Override
    public void renderRules() {
        output.println(RULES);
    }
}
