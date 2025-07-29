package org.example;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Тестирование калькулятора")
@Feature("Арифметические операции")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CalcTest {

    private static final Class<?>[] ERROR_TYPES = {
            IndexOutOfBoundsException.class,
            IllegalArgumentException.class,
            ClassCastException.class,
            NullPointerException.class
    };

    private final Random random = new Random();

    @Attachment(value = "Логотип", type = "image/jpeg")
    public byte[] attachLogo() {
        try {
            Path imagePath = Path.of("src/test/resources/img/logo.jpeg");
            return Files.readAllBytes(imagePath);
        } catch (IOException e) {
            return new byte[0]; // Не прикрепляем, если файл не найден
        }
    }

    private double parseOperand(String input) {
        return Double.parseDouble(input);
    }

    @Step("Может выбросить случайную ошибку")
    private void maybeThrowRandomError() {
        if (random.nextDouble() < 0.2) {
            Class<?> errorType = ERROR_TYPES[random.nextInt(ERROR_TYPES.length)];
            RuntimeException ex;
            if (errorType.equals(IndexOutOfBoundsException.class)) {
                ex = new IndexOutOfBoundsException("Случайная ошибка: IndexOutOfBoundsException");
            } else if (errorType.equals(IllegalArgumentException.class)) {
                ex = new IllegalArgumentException("Случайная ошибка: IllegalArgumentException");
            } else if (errorType.equals(ClassCastException.class)) {
                ex = new ClassCastException("Случайная ошибка: ClassCastException");
            } else {
                ex = new NullPointerException("Случайная ошибка: NullPointerException");
            }
            throw ex;
        }
    }

    @Severity(SeverityLevel.CRITICAL)
    @Description("Сложение двух чисел")
    @DisplayName("Сложение")
    @ParameterizedTest(name = "Сложение: {0} + {1} = {2}")
    @CsvSource({
            "1,9,10",
            "2,8,10",
            "3,5,8"
    })
    void testAddition(String aStr, String bStr, double expected) {
        attachLogo();

        Calc calc = new Calc();
        double a = parseOperand(aStr);
        double b = parseOperand(bStr);

        calc.addition(a, b);
        maybeThrowRandomError();

        Allure.addAttachment("Результат", String.format("%s + %s = %s", a, b, calc.getResult()));
        assertEquals(expected, calc.getResult(), 0.0001);
    }

    @Severity(SeverityLevel.CRITICAL)
    @Description("Вычитание двух чисел")
    @DisplayName("Вычитание")
    @ParameterizedTest(name = "Вычитание: {0} - {1} = {2}")
    @CsvSource({
            "9,1,8",
            "8,2,6",
            "10,4,6"
    })
    void testSubtraction(String aStr, String bStr, double expected) {
        attachLogo();

        Calc calc = new Calc();
        double a = parseOperand(aStr);
        double b = parseOperand(bStr);

        calc.subtraction(a, b);
        maybeThrowRandomError();

        Allure.addAttachment("Результат", String.format("%s - %s = %s", a, b, calc.getResult()));
        assertEquals(expected, calc.getResult(), 0.0001);
    }

    @Severity(SeverityLevel.CRITICAL)
    @Description("Умножение двух чисел")
    @DisplayName("Умножение")
    @ParameterizedTest(name = "Умножение: {0} * {1} = {2}")
    @CsvSource({
            "2,5,10",
            "3,3,9",
            "7,0,0"
    })
    void testMultiplication(String aStr, String bStr, double expected) {
        attachLogo();

        Calc calc = new Calc();
        double a = parseOperand(aStr);
        double b = parseOperand(bStr);

        calc.multiplication(a, b);
        maybeThrowRandomError();

        Allure.addAttachment("Результат", String.format("%s * %s = %s", a, b, calc.getResult()));
        assertEquals(expected, calc.getResult(), 0.0001);
    }

    @Severity(SeverityLevel.CRITICAL)
    @Description("Деление двух чисел")
    @DisplayName("Деление")
    @ParameterizedTest(name = "Деление: {0} / {1} = {2}")
    @CsvSource({
            "10,2,5",
            "8,4,2",
            "6,3,2"
    })
    void testDivision(String aStr, String bStr, double expected) {
        attachLogo();

        Calc calc = new Calc();
        double a = parseOperand(aStr);
        double b = parseOperand(bStr);

        calc.division(a, b);
        maybeThrowRandomError();

        Allure.addAttachment("Результат", String.format("%s / %s = %s", a, b, calc.getResult()));
        assertEquals(expected, calc.getResult(), 0.0001);
    }

    @Severity(SeverityLevel.NORMAL)
    @Description("Негативные кейсы: некорректные входные данные")
    @DisplayName("Некорректный ввод (ошибка парсинга)")
    @ParameterizedTest(name = "Ошибка при вводе: {0}, {1}")
    @CsvSource({
            "three,2",
            "1,two",
            "x,y"
    })
    void testInvalidInput(String aStr, String bStr) {
        assertThrows(NumberFormatException.class, () -> {
            double a = parseOperand(aStr);
            double b = parseOperand(bStr);
            new Calc().addition(a, b); // Любая операция
        });
    }
}
