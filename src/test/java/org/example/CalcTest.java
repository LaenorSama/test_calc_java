package org.example;

import io.qameta.allure.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Тестирование калькулятора")
@Feature("Арифметические операции")
public class CalcTest {

    private static final Class<?>[] ERROR_TYPES = {
            IndexOutOfBoundsException.class,
            IllegalArgumentException.class,
            ClassCastException.class,
            NullPointerException.class
    };

    private final Random random = new Random();

    @DisplayName("Проверка операции сложения")
    @Description("Этот тест проверяет операцию сложения.")
    @ParameterizedTest(name = "Сложение: {0} + {1} = {2}")
    @CsvSource({
            "1,9,10",
            "2,8,10",
            "3,5,8"
    })
    void testAddition(String aStr, String bStr, double expected) throws IOException {
        attachLogo();
        Calc calc = new Calc();

        try {
            double a = parseOperand(aStr);
            double b = parseOperand(bStr);
            calc.addition(a, b);

            Allure.addAttachment("Результат", String.format("Сложение %s + %s = %s", aStr, bStr, calc.getResult()));
            assertEquals(expected, calc.getResult(), 0.0001);

            maybeThrowRandomError();

        } catch (Exception e) {
            fail("Ошибка во время выполнения: " + e.getMessage(), e);
        }
    }

    @DisplayName("Проверка операции вычитания")
    @Description("Этот тест проверяет операцию вычитания.")
    @ParameterizedTest(name = "Вычитание: {0} - {1} = {2}")
    @CsvSource({
            "9,1,8",
            "8,2,6",
            "7,5,2"
    })
    void testSubtraction(String aStr, String bStr, double expected) throws IOException {
        attachLogo();
        Calc calc = new Calc();

        try {
            double a = parseOperand(aStr);
            double b = parseOperand(bStr);
            calc.subtraction(a, b);

            Allure.addAttachment("Результат", String.format("Вычитание %s - %s = %s", aStr, bStr, calc.getResult()));
            assertEquals(expected, calc.getResult(), 0.0001);

            maybeThrowRandomError();

        } catch (Exception e) {
            fail("Ошибка во время выполнения: " + e.getMessage(), e);
        }
    }

    @DisplayName("Проверка операции умножения")
    @Description("Этот тест проверяет операцию умножения.")
    @ParameterizedTest(name = "Умножение: {0} * {1} = {2}")
    @CsvSource({
            "0,10,0",
            "8,2,16",
            "7,3,21"
    })
    void testMultiplication(String aStr, String bStr, double expected) throws IOException {
        attachLogo();
        Calc calc = new Calc();

        try {
            double a = parseOperand(aStr);
            double b = parseOperand(bStr);
            calc.multiplication(a, b);

            Allure.addAttachment("Результат", String.format("Умножение %s * %s = %s", aStr, bStr, calc.getResult()));
            assertEquals(expected, calc.getResult(), 0.0001);

            maybeThrowRandomError();

        } catch (Exception e) {
            fail("Ошибка во время выполнения: " + e.getMessage(), e);
        }
    }

    @DisplayName("Проверка операции деления")
    @Description("Этот тест проверяет операцию деления.")
    @ParameterizedTest(name = "Деление: {0} / {1} = {2}")
    @CsvSource({
            "10,2,5",
            "8,2,4",
            "14,7,2"
    })
    void testDivision(String aStr, String bStr, double expected) throws IOException {
        attachLogo();
        Calc calc = new Calc();

        try {
            double a = parseOperand(aStr);
            double b = parseOperand(bStr);
            calc.division(a, b);

            Allure.addAttachment("Результат", String.format("Деление %s / %s = %s", aStr, bStr, calc.getResult()));
            assertEquals(expected, calc.getResult(), 0.0001);

            maybeThrowRandomError();

        } catch (Exception e) {
            fail("Ошибка во время выполнения: " + e.getMessage(), e);
        }
    }

    private double parseOperand(String input) {
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Ошибка преобразования операнда: " + input);
        }
    }

    private void maybeThrowRandomError() {
        if (random.nextDouble() < 0.2) {
            Class<?> errorType = ERROR_TYPES[random.nextInt(ERROR_TYPES.length)];
            RuntimeException ex;
            if (errorType.equals(IndexOutOfBoundsException.class)) {
                ex = new IndexOutOfBoundsException("Random error: IndexOutOfBoundsException");
            } else if (errorType.equals(IllegalArgumentException.class)) {
                ex = new IllegalArgumentException("Random error: IllegalArgumentException");
            } else if (errorType.equals(ClassCastException.class)) {
                ex = new ClassCastException("Random error: ClassCastException");
            } else {
                ex = new NullPointerException("Random error: NullPointerException");
            }
            throw ex;
        }
    }

    private void attachLogo() throws IOException {
        Path imagePath = Path.of("src/test/resources/img/logo.jpeg");
        if (Files.exists(imagePath)) {
            byte[] imageBytes = Files.readAllBytes(imagePath);
            Allure.addAttachment("ТестОпс Лого.jpg", "image/jpeg", new String(imageBytes), ".jpg");
        }
    }
}
