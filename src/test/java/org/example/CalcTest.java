package org.example;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Тестирование калькулятора")
@Feature("Арифметические операции")
public class CalcTest {

    @DisplayName("Проверка операции сложения")
    @ParameterizedTest(name = "Проверка операции сложения")
    @ValueSource(strings = {"1,9,10", "2,8,10", "3,two,5"})
    public void testAddition(String input) {
        Allure.step("Парсим входные данные: " + input, () -> {
            String[] parts = input.split(",");
            double expected = Double.parseDouble(parts[2]);
            Calc calc = new Calc();

            Allure.step("Выполняем сложение", () -> {
                try {
                    double a = parseOperand(parts[0]);
                    double b = parseOperand(parts[1]);
                    calc.addition(a, b);
                    Allure.step("Результат: " + calc.getResult());
                    assertEquals(expected, calc.getResult(), 0.0001);
                } catch (Exception e) {
                    fail("Ошибка при сложении: " + e.getMessage());
                }
            });
        });
    }

    @DisplayName("Проверка операции вычитания")
    @ParameterizedTest(name = "Проверка операции вычитания")
    @ValueSource(strings = {"9,1,8", "8,2,6", "7,two,5"})
    public void testSubtraction(String input) {
        Allure.step("Парсим входные данные: " + input, () -> {
            String[] parts = input.split(",");
            double expected = Double.parseDouble(parts[2]);
            Calc calc = new Calc();

            Allure.step("Выполняем вычитание", () -> {
                try {
                    double a = parseOperand(parts[0]);
                    double b = parseOperand(parts[1]);
                    calc.subtraction(a, b);
                    Allure.step("Результат: " + calc.getResult());
                    assertEquals(expected, calc.getResult(), 0.0001);
                } catch (Exception e) {
                    fail("Ошибка при вычитании: " + e.getMessage());
                }
            });
        });
    }

    @DisplayName("Проверка операции умножения")
    @ParameterizedTest(name = "Проверка операции умножения")
    @ValueSource(strings = {"0,10,0", "8,2,16", "7,two,14"})
    public void testMultiplication(String input) {
        Allure.step("Парсим входные данные: " + input, () -> {
            String[] parts = input.split(",");
            double expected = Double.parseDouble(parts[2]);
            Calc calc = new Calc();

            Allure.step("Выполняем умножение", () -> {
                try {
                    double a = parseOperand(parts[0]);
                    double b = parseOperand(parts[1]);
                    calc.multiplication(a, b);
                    Allure.step("Результат: " + calc.getResult());
                    assertEquals(expected, calc.getResult(), 0.0001);
                } catch (Exception e) {
                    fail("Ошибка при умножении: " + e.getMessage());
                }
            });
        });
    }

    @DisplayName("Проверка операции деления")
    @ParameterizedTest(name = "Проверка операции деления")
    @ValueSource(strings = {"10,0,0", "8,2,4", "14,two,2"})
    public void testDivision(String input) {
        Allure.step("Парсим входные данные: " + input, () -> {
            String[] parts = input.split(",");
            double expected = Double.parseDouble(parts[2]);
            Calc calc = new Calc();

            Allure.step("Выполняем деление", () -> {
                try {
                    double a = parseOperand(parts[0]);
                    double b = parseOperand(parts[1]);
                    calc.division(a, b);
                    Allure.step("Результат: " + calc.getResult());
                    assertEquals(expected, calc.getResult(), 0.0001);
                } catch (Exception e) {
                    fail("Ошибка при делении: " + e.getMessage());
                }
            });
        });
    }

    private double parseOperand(String s) {
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Неверный операнд: " + s);
        }
    }
}