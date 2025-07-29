package org.example;

import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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

    private record CalcCase(String aStr, String bStr, double expected) {}

    enum Operation {
        ADDITION("сложение"),
        SUBTRACTION("вычитание"),
        MULTIPLICATION("умножение"),
        DIVISION("деление");

        private final String name;
        Operation(String name) { this.name = name; }
        public String getName() { return name; }
    }

    @ParameterizedTest(name = "Проверка операции сложения")
    @ValueSource(strings = {
            "1,9,10",
            "2,8,10",
            "3,two,5" // всегда падает
    })
    @Description("Этот тест проверяет операцию сложения.")
    @DisplayName("Проверка операции сложения")
    void testAddition(String csv) throws IOException {
        runTest(parseCase(csv), Operation.ADDITION);
    }

    @ParameterizedTest(name = "Проверка операции вычитания")
    @ValueSource(strings = {
            "9,1,8",
            "8,2,6",
            "7,two,5"
    })
    @Description("Этот тест проверяет операцию вычитания.")
    @DisplayName("Проверка операции вычитания")
    void testSubtraction(String csv) throws IOException {
        runTest(parseCase(csv), Operation.SUBTRACTION);
    }

    @ParameterizedTest(name = "Проверка операции умножения")
    @ValueSource(strings = {
            "0,10,0",
            "8,2,16",
            "7,two,14"
    })
    @Description("Этот тест проверяет операцию умножения.")
    @DisplayName("Проверка операции умножения")
    void testMultiplication(String csv) throws IOException {
        runTest(parseCase(csv), Operation.MULTIPLICATION);
    }

    @ParameterizedTest(name = "Проверка операции деления")
    @ValueSource(strings = {
            "10,0,0",
            "8,2,4",
            "14,two,2"
    })
    @Description("Этот тест проверяет операцию деления.")
    @DisplayName("Проверка операции деления")
    void testDivision(String csv) throws IOException {
        runTest(parseCase(csv), Operation.DIVISION);
    }

    private CalcCase parseCase(String csv) {
        String[] parts = csv.split(",");
        return new CalcCase(parts[0], parts[1], Double.parseDouble(parts[2]));
    }

    private void runTest(CalcCase testCase, Operation operation) throws IOException {
        attachLogo();

        Allure.parameter("a", testCase.aStr);
        Allure.parameter("b", testCase.bStr);
        Allure.parameter("expected", String.valueOf(testCase.expected));

        Calc calc = new Calc();
        assertEquals(0, calc.getResult(), "Начальное значение должно быть 0");

        try {
            if (random.nextDouble() < 0.95) {
                double a = parseOperand(testCase.aStr);
                double b = parseOperand(testCase.bStr);

                switch (operation) {
                    case ADDITION -> calc.addition(a, b);
                    case SUBTRACTION -> calc.subtraction(a, b);
                    case MULTIPLICATION -> calc.multiplication(a, b);
                    case DIVISION -> calc.division(a, b);
                }
            }

            Allure.step(String.format("Выполнена операция %s: %s и %s, ожидаемый результат: %s",
                    operation.getName(), testCase.aStr, testCase.bStr, testCase.expected));

            Allure.addAttachment("Результат", String.format("%s %s %s = %s",
                    operation.getName(), testCase.aStr, testCase.bStr, calc.getResult()));

            assertEquals(testCase.expected, calc.getResult(), 0.0001);

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
