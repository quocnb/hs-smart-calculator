package calculator;

import java.math.BigInteger;
import java.util.Arrays;

enum Operator {
    Plus, Minus, Product, Divide, Pow;

    @Override
    public String toString() {
        return switch (this) {
            case Plus -> "+";
            case Minus -> "-";
            case Product -> "*";
            case Divide -> "/";
            case Pow -> "^";
        };
    }

    public BigInteger execute(BigInteger operand1, BigInteger operand2) {
        return switch (this) {
            case Plus -> operand1.add(operand2);
            case Minus -> operand1.subtract(operand2);
            case Pow -> operand1.pow(operand2.intValue());
            case Product -> operand1.multiply(operand2);
            case Divide -> operand1.divide(operand2);
        };
    }

    public static boolean isOperator(String s) {
        return Arrays.stream(Operator.values())
                .map(Operator::toString)
                .toList()
                .contains(s);
    }

    public static Operator operator(String s) {
        return switch (s) {
            case "+" -> Plus;
            case "-" -> Minus;
            case "*" -> Product;
            case "/" -> Divide;
            case "^" -> Pow;
            default -> throw new IllegalArgumentException();
        };
    }
}
