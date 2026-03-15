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
                F - Fox (лиса, хищник)
                R - Rabbit (кролик, пища для лисы)
                G - Grass (трава, пища для кролика)
                T - Tree (дерево, препятствие)
                M - Mountain (гора, препятствие)
                - - пустая клетка

            - Во время симуляции можно:
                1 - поставить симуляцию на паузу
                2 - продолжить симуляцию
                3 - завершить симуляцию

            Симуляция завершается, если:
            1. Исчезает один из ключевых типов сущностей
               (хищники, травоядные или трава).
            2. За полный ход на карте не произошло ни одного изменения.
            3. Симуляция завершена пользователем.
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
