package org.example;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

    @ParameterizedTest
    @CsvSource({
            "1,9,10",
            "2,8,10",
            "3,two,5" // всегда падает
    })
    @Description("Этот тест проверяет операцию сложения.")
    @DisplayName("Проверка операции сложения")
    void testAddition(String aStr, String bStr, double expected) throws IOException {
        attachLogo();

        Calc calc = new Calc();
        assertEquals(0, calc.getResult(), "Начальное значение должно быть 0");

        try {
            if (random.nextDouble() < 0.95) {
                double a = parseOperand(aStr);
                double b = parseOperand(bStr);
                calc.addition(a, b);
            }

            Allure.addAttachment("Результат", String.format("Сложение %s + %s = %s", aStr, bStr, calc.getResult()));
            assertEquals(expected, calc.getResult(), 0.0001);

            maybeThrowRandomError();

        } catch (Exception e) {
            fail("Ошибка во время выполнения: " + e.getMessage(), e);
        }
    }

    @ParameterizedTest
    @CsvSource({
            "9,1,8",
            "8,2,6",
            "7,two,5"
    })
    @Description("Этот тест проверяет операцию вычитания.")
    @DisplayName("Проверка операции вычитания")
    void testSubtraction(String aStr, String bStr, double expected) throws IOException {
        attachLogo();

        Calc calc = new Calc();
        assertEquals(0, calc.getResult());

        try {
            if (random.nextDouble() < 0.95) {
                double a = parseOperand(aStr);
                double b = parseOperand(bStr);
                calc.subtraction(a, b);
            }

            Allure.addAttachment("Результат", String.format("Вычитание %s - %s = %s", aStr, bStr, calc.getResult()));
            assertEquals(expected, calc.getResult(), 0.0001);

            maybeThrowRandomError();

        } catch (Exception e) {
            fail("Ошибка во время выполнения: " + e.getMessage(), e);
        }
    }

    @ParameterizedTest
    @CsvSource({
            "0,10,0",
            "8,2,16",
            "7,two,14"
    })
    @Description("Этот тест проверяет операцию умножения.")
    @DisplayName("Проверка операции умножения")
    void testMultiplication(String aStr, String bStr, double expected) throws IOException {
        attachLogo();

        Calc calc = new Calc();
        assertEquals(0, calc.getResult());

        try {
            if (random.nextDouble() < 0.95) {
                double a = parseOperand(aStr);
                double b = parseOperand(bStr);
                calc.multiplication(a, b);
            }

            Allure.addAttachment("Результат", String.format("Умножение %s * %s = %s", aStr, bStr, calc.getResult()));
            assertEquals(expected, calc.getResult(), 0.0001);

            maybeThrowRandomError();

        } catch (Exception e) {
            fail("Ошибка во время выполнения: " + e.getMessage(), e);
        }
    }

    @ParameterizedTest
    @CsvSource({
            "10,0,0",
            "8,2,4",
            "14,two,2"
    })
    @Description("Этот тест проверяет операцию деления.")
    @DisplayName("Проверка операции деления")
    void testDivision(String aStr, String bStr, double expected) throws IOException {
        attachLogo();

        Calc calc = new Calc();
        assertEquals(0, calc.getResult());

        try {
            if (random.nextDouble() < 0.95) {
                double a = parseOperand(aStr);
                double b = parseOperand(bStr);
                calc.division(a, b);
            }

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
