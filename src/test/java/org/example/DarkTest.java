package org.example;

import io.qameta.allure.Allure;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.qameta.allure.Allure.step;
import io.qameta.allure.Feature;
import io.qameta.allure.Features;
import io.qameta.allure.Owner;
import io.qameta.allure.Stories;
import io.qameta.allure.Story;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;


public class DarkTest {

    @Test
    @AllureId("2127")
    @DisplayName("Проверка переключения на темную тему")
    @Owner("Alex")
    @Features({
            @Feature("Интерфейс"),
    })
    @Stories({
            @Story("Смена темы"),
    })
    @Tags({
            @Tag("интерфейс"),
    })
    public void testMethod() {
        Allure.label("tag", "интерфейс");
        Allure.label("Jira_Alex", "SCRUM-67");
        step("Зайти на портал");
        step("Перейти в меню пользователя");
        step("Открыть настройки оформления");
        step("Переключить тему оформления на темную");
        step("Убедиться что интерфейс переключился на темную тему");
    }

}