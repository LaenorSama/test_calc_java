package org.example;

import io.qameta.allure.Allure;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.qameta.allure.Allure.step;

public class DarkTest {

    @Test
    @AllureId("661")
    @DisplayName("Проверка темной темы.")
    public void testMethod() {
        Allure.label("Jira_Alex", "SCRUM-28");
        Allure.label("suite", "test_calc_classes");
        step("Зайти на портал");
        step("Перейти в меню пользователя");
        step("Открыть настройки оформления");
        step("Переключить тему оформления на темную");
        step("Убедиться что интерфейс переключился на темную тему");
    }

}