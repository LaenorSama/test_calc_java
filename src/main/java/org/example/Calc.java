package org.example;

public class Calc {
    private double result;

    public Calc() {
        // Калькулятор без начальных данных
        this.result = 0;
    }

    public void addition(double a, double b) {
        this.result = a + b;
    }

    public void subtraction(double a, double b) {
        this.result = a - b;
    }

    public void multiplication(double a, double b) {
        this.result = a * b;
    }

    public void division(double a, double b) {
        this.result = a / b;
    }

    public double getResult() {
        return result;
    }

    // Для отладки — аналог __main__
    public static void main(String[] args) {
        Calc localCalc = new Calc();

        System.out.println("Начальное значение: " + localCalc.getResult());
        System.out.println("Объект калькулятора: " + localCalc);

        localCalc.addition(4, 5);
        System.out.println("Результат сложения: " + localCalc.getResult());

        localCalc.multiplication(4, 5);
        System.out.println("Результат умножения: " + localCalc.getResult());
    }
}