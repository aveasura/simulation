package org.simulation.console.renderer;

import org.simulation.console.Output;

public class ConsoleMenuRenderer implements MenuRenderer {

    private static final String MAIN_MENU = """
            Главное меню
                        
            1. Показать правила
            2. Запустить симуляцию
            3. Выход
            """;

    private static final String RULES = """
            Правила симуляции

            - После запуска на карте появляется стартовый набор сущностей.
            - На каждом ходу существа перемещаются и взаимодействуют друг с другом.
            - Если количество некоторых сущностей падает ниже заданного минимума,
              они автоматически появляются снова.

            Симуляция завершается, если:
            1. Исчезает один из ключевых типов сущностей
               (хищники, травоядные или трава).
            2. За полный ход на карте не произошло ни одного изменения.
            """;

    private final Output output;

    public ConsoleMenuRenderer(Output output) {
        this.output = output;
    }

    @Override
    public void renderMainMenu() {
        output.print(MAIN_MENU);
    }

    @Override
    public void renderRules() {
        output.print(RULES);
    }

    @Override
    public void printExitMessage() {
        output.print("Выход...");
    }

    @Override
    public void printInvalidMainMenuChoice() {
        output.print("Выберите цифру из доступных");
    }
}
