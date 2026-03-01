package org.simulation.console.renderer;

import org.simulation.console.Output;

import java.util.Objects;

public class ConsoleMainMenuRenderer implements MainMenuRenderer {

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
            
            - Вы можете ставить симуляцию на паузу, возобновлять и полностью завершать.

            Симуляция завершается, если:
            1. Исчезает один из ключевых типов сущностей
               (хищники, травоядные или трава).
            2. За полный ход на карте не произошло ни одного изменения
            3. Симуляция завершена пользователем.
            """;

    private final Output output;

    public ConsoleMainMenuRenderer(Output output) {
        this.output = Objects.requireNonNull(output, "output must not be null");
    }

    @Override
    public void renderMenu() {
        output.println(MAIN_MENU);
    }

    @Override
    public void renderRules() {
        output.println(RULES);
    }

    @Override
    public void printExitMessage() {
        output.println("Выход...");
    }

    @Override
    public void printInvalidChoice() {
        output.println("Неправильный ввод: выберите и введите цифру из предложенных в главном меню");
    }

    @Override
    public void printInputClosed() {
        output.print("Поток ввода закрыт. Приложение закрывается.");
    }
}
